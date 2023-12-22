package com.sebrown2023.controllers;

import com.sebrown2023.controller.SubmissionApi;
import com.sebrown2023.model.dto.SubmissionComponent;
import com.sebrown2023.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubmissionController implements SubmissionApi {

    private final SubmissionService submissionService;

    @Override
    public ResponseEntity<SubmissionComponent> getSubmission(Long submissionId) {
        return ResponseEntity.ok().body(submissionService.getSubmissionComponentById(submissionId));
    }

    @Override
    public ResponseEntity<List<SubmissionComponent>> getSubmissionsByExamSessionId(String examSessionId) {
        return ResponseEntity.ok().body(submissionService.getSubmissionsByExamSessionId(examSessionId));
    }
}
