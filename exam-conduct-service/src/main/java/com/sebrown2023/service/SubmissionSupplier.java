package com.sebrown2023.service;

import com.sebrown2023.exceptions.SubmissionException;
import com.sebrown2023.model.dto.Submission;
import com.sebrown2023.model.dto.SubmissionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Service
public class SubmissionSupplier {
    private final Logger logger = LoggerFactory.getLogger(SubmissionSupplier.class);
    private static final String TOPIC_NAME = "submissionsTopic";
    private final KafkaTemplate<String, SubmissionDto> kafkaTemplate;

    public SubmissionSupplier(KafkaTemplate<String, SubmissionDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void delegateToSupplier(Submission submission) {
        var newSubmission = new SubmissionDto(
                submission.getTaskId(),
                submission.getExamSessionId(),
                submission.getUserSourceCode(),
                Date.from(Instant.now())
        );
        var uuid = UUID.randomUUID().toString();
        logger.info("[%s] Sending new submission to kafka%s".formatted(uuid, newSubmission));
        SendResult<String, SubmissionDto> result = null;
        try {
            result = kafkaTemplate.send(TOPIC_NAME, newSubmission).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new SubmissionException(e);
        }
        logger.info("[%s] Sending to kafka result: %s".formatted(uuid, result));
    }

}
