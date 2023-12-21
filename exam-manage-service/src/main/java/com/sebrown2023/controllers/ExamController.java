package com.sebrown2023.controllers;

import com.sebrown2023.controller.ExamApi;
import com.sebrown2023.dto.deprecated.ExamDto;
import com.sebrown2023.dto.deprecated.PostExamDto;
import com.sebrown2023.model.dto.ExamComponent;
import com.sebrown2023.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
public class ExamController implements ExamApi {
    private final ExamService examService;

    @GetMapping(value = "/{examId}")
    public ResponseEntity<ExamDto> getExam(@PathVariable long examId) {
        var taskDto = examService.getExamDtoById(examId);
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping(value = "/examinee/{examineeId}")
    public ResponseEntity<List<ExamDto>> getAllExams(@PathVariable int examineeId) {
        var examsDto = examService.getAllExamDtoByExamineeId(examineeId);
        return ResponseEntity.ok(examsDto);
    }

    //Todo еще нужен get по экзаменуемому

    @GetMapping(value = "")
    public ResponseEntity<List<ExamDto>> getAllExams() {
        var examsDto = examService.getAllExamDto();
        return ResponseEntity.ok(examsDto);
    }

    @PostMapping()
    public ResponseEntity<?> createNewExam(PostExamDto postExamDto) {
        examService.createExam(postExamDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{examId}")
    public ResponseEntity<?> deleteTask(@PathVariable long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ExamComponent> createExam(ExamComponent examComponent) {
        return null;
    }
}
