package com.sebrown2023.controllers;

import com.sebrown2023.controller.ExamSessionApi;
import com.sebrown2023.model.dto.ExamSessionComponent;
import com.sebrown2023.services.ExamSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamSessionController implements ExamSessionApi {
    private final ExamSessionService examSessionService;

    @Override
    public ResponseEntity<ExamSessionComponent> getExamSession(String examSessionId) {
        var examSessionComponent = examSessionService.getExamSessionComponentById(examSessionId);
        return ResponseEntity.ok().body(examSessionComponent);
    }

    @Override
    public ResponseEntity<List<ExamSessionComponent>> getAllExamSessions() {
        var examSessionsComponent = examSessionService.getAllExamSessions();
        return ResponseEntity.ok().body(examSessionsComponent);
    }

    @Override
    public ResponseEntity<List<ExamSessionComponent>> getExamSessionsByExam(Long examId) {
        var examSessionsComponent = examSessionService.getExamSessionComponentsByExamId(examId);
        return ResponseEntity.ok().body(examSessionsComponent);
    }

    @Override
    public ResponseEntity<Void> deleteExamSession(String examSessionId) {
        examSessionService.deleteExamSession(examSessionId);
        return ResponseEntity.ok().build();
    }
}
