package com.sebrown2023.controllers;

import com.sebrown2023.controller.ExamApi;
import com.sebrown2023.model.dto.ExamComponent;
import com.sebrown2023.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamController implements ExamApi {
    private final ExamService examService;

    @Override
    public ResponseEntity<ExamComponent> getExam(Long examId) {
        var examComponent = examService.getExamComponentById(examId);
        return ResponseEntity.ok(examComponent);
    }

    @Override
    public ResponseEntity<List<ExamComponent>> getAllExams() {
        var examComponents = examService.getAllExamWithTasksAndTests();
        return ResponseEntity.ok(examComponents);
    }

    @Override
    public ResponseEntity<List<ExamComponent>> getExamsForExaminer(Long examinerId) {
        var examComponents = examService.getAllExamWithTasksAndTestsByExaminerId(examinerId);
        return ResponseEntity.ok(examComponents);
    }

    @Override
    public ResponseEntity<ExamComponent> createExam(ExamComponent examComponent) {
        var createdExamComponent = examService.createExam(examComponent);
        return ResponseEntity.ok().body(createdExamComponent);
    }

    @Override
    public ResponseEntity<Void> deleteExam(Long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.ok().build();
    }
}
