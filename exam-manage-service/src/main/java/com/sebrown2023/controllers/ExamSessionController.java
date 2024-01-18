package com.sebrown2023.controllers;

import com.sebrown2023.controller.ExamSessionApi;
import com.sebrown2023.model.dto.ExamSessionComponent;
import com.sebrown2023.services.ExamSessionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamSessionController implements ExamSessionApi {
    private final ExamSessionService examSessionService;

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ExamSessionComponent> getExamSession(String examSessionId) {
        var examSessionComponent = examSessionService.getExamSessionComponentById(examSessionId);
        return ResponseEntity.ok().body(examSessionComponent);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<ExamSessionComponent>> getAllExamSessions() {
        var examSessionsComponent = examSessionService.getAllExamSessions();
        return ResponseEntity.ok().body(examSessionsComponent);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<ExamSessionComponent>> getExamSessionsByExam(Long examId) {
        var examSessionsComponent = examSessionService.getExamSessionComponentsByExamId(examId);
        return ResponseEntity.ok().body(examSessionsComponent);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ExamSessionComponent> createExamSession(ExamSessionComponent examSessionComponent) {
        var examSessionsComponent = examSessionService.createNewExamSession(examSessionComponent);
        return ResponseEntity.ok().body(examSessionsComponent);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteExamSession(String examSessionId) {
        examSessionService.deleteExamSession(examSessionId);
        return ResponseEntity.ok().build();
    }
}
