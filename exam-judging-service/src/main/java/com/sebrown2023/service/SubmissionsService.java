package com.sebrown2023.service;

import com.sebrown2023.compiler.BaseCompiler;
import com.sebrown2023.compiler.JavaCompiler;
import com.sebrown2023.compiler.model.InvokeStatus;
import com.sebrown2023.compiler.model.Stream;
import com.sebrown2023.configuration.properties.JavaCompilerProperties;
import com.sebrown2023.exception.JudgeServiceException;
import com.sebrown2023.model.WrappedCode;
import com.sebrown2023.model.db.ProgrammingLanguage;
import com.sebrown2023.model.db.Submission;
import com.sebrown2023.model.db.Test;
import com.sebrown2023.model.db.TestResult;
import com.sebrown2023.model.dto.SubmissionDto;
import com.sebrown2023.repository.ExamSessionRepository;
import com.sebrown2023.repository.SubmissionRepository;
import com.sebrown2023.repository.TaskRepository;
import com.sebrown2023.repository.TestRepository;
import com.sebrown2023.repository.TestResultRepository;
import com.sebrown2023.util.StringUtils;
import com.sebrown2023.util.TestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Service
public class SubmissionsService {
    private final Logger logger = LoggerFactory.getLogger(SubmissionsService.class);
    private final SubmissionRepository submissionRepository;
    private final TaskRepository taskRepository;
    private final ExamSessionRepository examSessionRepository;
    private final ExecutorService submissionExecutorService;

    private final TestResultRepository testResultRepository;

    private final TestRepository testRepository;

    private final Map<ProgrammingLanguage, BaseCompiler> compilers;

    private final TransactionTemplate transactionTemplate;

    private final JavaCompilerProperties javaCompilerProperties;

    public SubmissionsService(
            SubmissionRepository submissionRepository,
            TaskRepository taskRepository,
            ExamSessionRepository examSessionRepository,
            ExecutorService submissionExecutorService,
            TestResultRepository testResultRepository,
            TestRepository testRepository,
            Map<ProgrammingLanguage, BaseCompiler> compilers,
            TransactionTemplate transactionTemplate,
            JavaCompilerProperties javaCompilerProperties) {
        this.submissionRepository = submissionRepository;
        this.taskRepository = taskRepository;
        this.examSessionRepository = examSessionRepository;
        this.submissionExecutorService = submissionExecutorService;
        this.testResultRepository = testResultRepository;
        this.testRepository = testRepository;
        this.compilers = compilers;
        this.transactionTemplate = transactionTemplate;
        this.javaCompilerProperties = javaCompilerProperties;
    }

    @KafkaListener(topics = "submissionsTopic")
    @RetryableTopic(
            backoff = @Backoff(delayExpression = "#{'${kafka.backoff.delay}'}"),
            attempts = "${kafka.backoff.retry}"
    )
    public void submissionsListen(@Header(value = "retry_topic-attempts", required = false) Integer attempts, @Payload SubmissionDto submissionDto) {
        logger.info(STR. "Received new submission: \{ submissionDto }. Attempt \{ attempts == null ? 1 : attempts }" );
        var task = taskRepository.findById(submissionDto.taskId()).orElseThrow(() ->
                new JudgeServiceException.TaskNotFoundException(submissionDto.taskId())
        );
        var session = examSessionRepository.findById(submissionDto.examSessionId()).orElseThrow(() ->
                new JudgeServiceException.ExamSessionNotFoundException(submissionDto.taskId())
        );
        var newSubmission = submissionRepository.save(Submission.createFromDto(task, session, submissionDto));
        logger.info(STR. "Submission#\{ newSubmission.getId() } was saved successfully" );
        submissionExecutorService.submit(() -> processSubmissionInBG(newSubmission));
    }

    private void processSubmissionInBG(Submission submission) {
        logger.info(STR. "Start processing submission#\{ submission.getId() }" );
        switch (submission.getTask().getExam().getProgrammingLanguage()) {
            case JAVA -> processJavaSubmission((JavaCompiler) compilers.get(ProgrammingLanguage.JAVA), submission);
            case JAVA_SCRIPT -> {
                // TODO support java_script
            }
        }
    }

    private void processJavaSubmission(JavaCompiler javac, Submission submission) {
        try {
            var wrappedCode = TestUtil.addMainFunction(submission.getUserSourceCode(), ProgrammingLanguage.JAVA);
            TestUtil.copyJavaLibs(javaCompilerProperties.libDir(), wrappedCode.sourceDir().resolve("compiled"));
            var invokeStatus =
                    javac.invokeCompiler(wrappedCode.sourceCode(), wrappedCode.sourceDir().resolve("compiled").toString());
            if (invokeStatus.hasCompilationError()) {
                logger.info(STR. "Submission#\{ submission.getId() } has compilation error: \{ invokeStatus.combinedOutput() }" );
                transactionTemplate.executeWithoutResult((_) ->
                        saveCompilationErrorResult("Compilation Error:", submission, invokeStatus)
                );
            } else if (invokeStatus.hasTimeout()) {
                logger.info(STR. "Submission#\{ submission.getId() } has timeout error: \{ invokeStatus.combinedOutput() }" );
                transactionTemplate.executeWithoutResult((_) ->
                        saveCompilationErrorResult("Timeout Exception:", submission, invokeStatus)
                );
            } else if (invokeStatus.isCompileSuccess()) {
                logger.info(STR. "Submission#\{ submission.getId() } was compiled succsessful" );
                var testResults = runTests(testRepository.findAllByTaskId(submission.getTask().getId()), javac, wrappedCode, submission);
                logger.info(STR. "Tests results for submission \{ submission }: \{ StringUtils.joinToString(testResults, ", ") }" );
                transactionTemplate.executeWithoutResult((_) ->
                        testResultRepository.saveAll(testResults)
                );
            } else {
                throw new JudgeServiceException.SubmissionProcessingException(null);
            }
        } catch (IOException e) {
            throw new JudgeServiceException.SubmissionProcessingException(e);
        } catch (ExecutionException | TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TestResult> runTests(List<Test> tests, JavaCompiler javac, WrappedCode wrappedCode, Submission submission) {
        return tests.stream().parallel().map(test -> {
            var executionsStatus =
                    javac.executeCompiled(TestUtil.prepareJavaPathToCompiled(wrappedCode), Stream.BOTH, List.of(wrappedCode.mainMethod(), test.getInputData()), 5L);
                if (!executionsStatus.errorStream().isEmpty()) {
                logger.info(STR. "Test \{ test } failed: \{ executionsStatus.errorStream() }" );
                return new TestResult(
                        submission,
                        test,
                        "Execution error: " + executionsStatus.errorStream(),
                        false,
                        Duration.of(executionsStatus.executionTimeMs(), ChronoUnit.MILLIS)
                );
            }

            var actualOutput = executionsStatus.outputStream().replace("USER_SOLUTION_RESULT: ", "");

            return new TestResult(
                    submission,
                    test,
                    actualOutput,
                    actualOutput.equals(test.getExpectedOutputData()),
                    Duration.of(executionsStatus.executionTimeMs(), ChronoUnit.MILLIS)
            );

        }).toList();
    }

    private void saveCompilationErrorResult(String message, Submission submission, InvokeStatus invokeStatus) {
        var testResults = testRepository.findAllByTaskId(submission.getTask().getId()).stream().map(test ->
                new TestResult(
                        submission,
                        test,
                        message + invokeStatus.combinedOutput(),
                        false,
                        Duration.of(invokeStatus.compilerExecTimeMillis(), ChronoUnit.MILLIS)
                )
        ).toList();
        testResultRepository.saveAll(testResults);
    }


    @DltHandler
    public void dlt(SubmissionDto data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.error("Event from topic " + topic + " is dead lettered - event:" + data);
    }
}
