package com.sebrown2023.controllers;

import com.sebrown2023.dto.deprecated.ExamSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam_session")
public class ExamSessionController {

    //Todo получение всех сессий по экзамену
    //Todo Submission тоже сюда тянем


    @GetMapping(value = "/{examSessionId}")
    public ResponseEntity<ExamSessionDto> getExamSession(@PathVariable long examSessionId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ExamSessionDto>> getAllExamSessions() {
        return ResponseEntity.ok().build();
    }
}
