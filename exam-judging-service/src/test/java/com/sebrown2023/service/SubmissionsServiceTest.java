package com.sebrown2023.service;

import com.sebrown2023.WithPostgresTest;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Examinee;
import com.sebrown2023.model.db.ProgrammingLanguage;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.dto.SubmissionDto;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.ExamSessionRepository;
import com.sebrown2023.repository.ExamineeRepository;
import com.sebrown2023.repository.SubmissionRepository;
import com.sebrown2023.repository.TaskRepository;
import com.sebrown2023.repository.TestRepository;
import com.sebrown2023.repository.TestResultRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"},
        topics = {"submissionsTopic", "submissionsTopic-dlt", "submissionsTopic-retry-0", "submissionsTopic-retry-1",}
)
@DirtiesContext
@ActiveProfiles("test")
class SubmissionsServiceTest extends WithPostgresTest {
    @Autowired
    private ExamineeRepository examineeRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ExamSessionRepository examSessionRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private KafkaTemplate<String, SubmissionDto> kafkaTemplate;

    @Autowired
    private ProducerFactory<String, SubmissionDto> producerFactory;

    @BeforeEach
    void cleanUpDataBase() {
        examineeRepository.deleteAll();
        examRepository.deleteAll();
        taskRepository.deleteAll();
        testRepository.deleteAll();
        examSessionRepository.deleteAll();
        submissionRepository.deleteAll();
        testResultRepository.deleteAll();
    }

    @Test
    void javaCompileTest() throws InterruptedException, IOException, ExecutionException {
        var examinee = examineeRepository.save(
                new Examinee(null, "Oleg", "Mongol", "oleg@mongol.com", "78889997766")
        );
        var exam = examRepository.save(new Exam(
                null,
                1,
                "test_exam",
                "descr",
                ProgrammingLanguage.JAVA,
                Duration.ofHours(1),
                Duration.ofHours(2),
                LocalDateTime.now().truncatedTo(ChronoUnit.MICROS),
                Collections.emptyList()
        ));

        var task =
                taskRepository.save(new Task(null, exam, "fib", "fib_desk", "class Solution { }"));

        var test = testRepository.save(
                new com.sebrown2023.model.db.Test(null, task, "test_test", "52", "42")
        );

        var examSession = examSessionRepository.save(
                new ExamSession(
                        null,
                        exam,
                        examinee,
                        Status.FINISHED,
                        LocalDateTime.now().truncatedTo(ChronoUnit.MICROS),
                        LocalDateTime.now().plusMinutes(10).truncatedTo(ChronoUnit.MICROS)
                )
        );

        var sourceCode =
                Files.readString(Path.of(this.getClass().getResource("/submissions/submission1SourceCode.txt").getPath()));

        var newSubmission = new SubmissionDto(
                1,
                examSession.getId(),
                sourceCode,
                Date.from(Instant.now())
        );


        var sendResult = kafkaTemplate.send("submissionsTopic", newSubmission).get();

        Assertions.assertEquals(newSubmission, sendResult.getProducerRecord().value());

        Thread.sleep(10000); // wait for processing

        var submissions = StreamSupport.stream(submissionRepository.findAll().spliterator(), false).toList();
        Assertions.assertEquals(1, submissions.size());
        var submission = submissions.get(0);
        Assertions.assertEquals(task, submission.getTask());
        Assertions.assertEquals(sourceCode, submission.getUserSourceCode());

        var testResults = StreamSupport.stream(testResultRepository.findAll().spliterator(), false).toList();
        Assertions.assertEquals(1, testResults.size());
        var testResult = testResults.get(0);
        Assertions.assertEquals(test, testResult.getTest());
        Assertions.assertTrue(testResult.getPassed());
    }
}