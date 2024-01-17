package com.sebrown2023.controller;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.dto.ExamSessionComponent;
import com.sebrown2023.model.dto.FinishExamSessionResponse;
import com.sebrown2023.model.dto.SendTaskSolutionRequest;
import com.sebrown2023.model.dto.Submission;
import com.sebrown2023.service.ExamSessionService;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ExamSessionApiImpl implements ExamSessionApi {
    @Autowired
    private ExamSessionService sessionService;

    @Override
    public ResponseEntity<FinishExamSessionResponse> apiExamSessionExamSessionIdFinishGet(UUID examSessionId) {
        ExamSession examSession = sessionService.finishSession(examSessionId);

        ModelMapper modelMapper = new ModelMapper();
        FinishExamSessionResponse response = modelMapper.map(examSession, FinishExamSessionResponse.class);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ExamSessionComponent> apiExamSessionExamSessionIdGet(UUID examSessionId) {
        ExamSession examSession = sessionService.getByUUID(examSessionId);
        ModelMapper modelMapper = new ModelMapper();
        ExamSessionComponent body = modelMapper.map(examSession, ExamSessionComponent.class);
        return ResponseEntity.ok(body);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<String> apiExamSessionExamSessionIdSendSolutionPut(UUID examSessionId, SendTaskSolutionRequest sendTaskSolutionRequest) {
        Submission submission = sendTaskSolutionRequest.getSubmission();
        sessionService.sendTask(examSessionId, submission);

        return ResponseEntity.ok("Ok");
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ExamSessionComponent> apiExamSessionExamSessionIdStartGet(UUID examSessionId) {
        ExamSession examSession = sessionService.startSession(examSessionId);

        ModelMapper modelMapper = new ModelMapper();
        ExamSessionComponent response = modelMapper.map(examSession, ExamSessionComponent.class);

        return ResponseEntity.ok(response);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Long> apiExamSessionExamSessionIdTimeGet(UUID examSessionId) {
        Long minutes = sessionService.getAvailableTimeMinutes(examSessionId);
        return ResponseEntity.ok(minutes);
    }
}
