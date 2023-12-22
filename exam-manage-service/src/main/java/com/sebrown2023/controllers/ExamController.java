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
        var taskDto = examService.getExamDtoById(examId);
        return ResponseEntity.ok(taskDto);
    }

    @Override
    public ResponseEntity<List<ExamComponent>> getAllExams() {
        var examsDto = examService.getAllExamWithTasksAndTests();
        return ResponseEntity.ok(examsDto);
    }

    @Override
    public ResponseEntity<List<ExamComponent>> getExamsForExaminer(Long examinerId) {
        var examsDto = examService.getAllExamWithTasksAndTestsByExaminerId(examinerId);
        return ResponseEntity.ok(examsDto);
    }

    @Override
    public ResponseEntity<ExamComponent> createExam(ExamComponent examComponent) {
        examService.createExam(examComponent);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteExam(Long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.ok().build();
    }
}
