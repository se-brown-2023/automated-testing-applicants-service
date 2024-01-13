package com.sebrown2023.controller;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.dto.FinishExamSessionResponse;
import com.sebrown2023.model.dto.SendTaskSolutionRequest;
import com.sebrown2023.model.dto.StartExamSessionResponse;
import com.sebrown2023.model.dto.Submission;
import com.sebrown2023.service.ExamSessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
    public ResponseEntity<String> apiExamSessionExamSessionIdSendSolutionPut(UUID examSessionId, SendTaskSolutionRequest sendTaskSolutionRequest) {
        Submission submission = sendTaskSolutionRequest.getSubmission();
        sessionService.sendTask(examSessionId, submission);

        return ResponseEntity.ok("Ok");
    }

    @Override
    public ResponseEntity<StartExamSessionResponse> apiExamSessionExamSessionIdStartGet(UUID examSessionId) {
        ExamSession examSession = sessionService.startSession(examSessionId);

        ModelMapper modelMapper = new ModelMapper();
        StartExamSessionResponse response = modelMapper.map(examSession, StartExamSessionResponse.class);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Long> apiExamSessionExamSessionIdTimeGet(UUID examSessionId) {
         Long minutes = sessionService.getAvailableTimeMinutes(examSessionId);
         return ResponseEntity.ok(minutes);
    }
}
