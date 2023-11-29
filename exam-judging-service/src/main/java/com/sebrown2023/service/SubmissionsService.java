package com.sebrown2023.service;

import com.sebrown2023.exception.JudgeServiceException;
import com.sebrown2023.model.db.Submission;
import com.sebrown2023.model.dto.SubmissionDto;
import com.sebrown2023.repository.ExamSessionRepository;
import com.sebrown2023.repository.SubmissionRepository;
import com.sebrown2023.repository.TaskRepository;
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

import java.util.concurrent.atomic.AtomicLong;

@Service
public class SubmissionsService {
    private final Logger logger = LoggerFactory.getLogger(SubmissionsService.class);
    private final SubmissionRepository submissionRepository;

    private final AtomicLong t = new AtomicLong();

    private final TaskRepository taskRepository;

    private final ExamSessionRepository examSessionRepository;

    public SubmissionsService(SubmissionRepository submissionRepository, TaskRepository taskRepository, ExamSessionRepository examSessionRepository) {
        this.submissionRepository = submissionRepository;
        this.taskRepository = taskRepository;
        this.examSessionRepository = examSessionRepository;
    }

    @KafkaListener(topics = "submissionsTopic")
    @RetryableTopic(
            backoff = @Backoff(delayExpression = "#{'${kafka.backoff.delay}'}"),
            attempts = "${kafka.backoff.retry}"
    )
    public void submissionsListen(@Header("retry_topic-attempts") Integer attempts, @Payload SubmissionDto submissionDto) {
        logger.info(STR. "Received new submission: \{ submissionDto }. Attempt \{ attempts == null ? 1 : attempts }" );
        var task = taskRepository.findById(submissionDto.taskId()).orElseThrow(() ->
                new JudgeServiceException.TaskNotFoundException(submissionDto.taskId())
        );
        var session = examSessionRepository.findById(submissionDto.examSessionId()).orElseThrow(() ->
                new JudgeServiceException.ExamSessionNotFoundException(submissionDto.taskId())
        );
        var newSubmission = submissionRepository.save(Submission.createFromDto(task, session, submissionDto));
        logger.info(STR. "Submission \{ newSubmission.getId() } was saved successfully" );
        // TODO run process in bg
    }

    @DltHandler
    public void dlt(SubmissionDto data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.error("Event from topic " + topic + " is dead lettered - event:" + data);
    }
}
