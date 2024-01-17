package com.sebrown2023.controllers;

import com.sebrown2023.controller.ExamineeApi;
import com.sebrown2023.model.dto.ExamineeComponent;
import com.sebrown2023.services.ExamineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamineeController implements ExamineeApi {
    private final ExamineeService examineeService;

    @Override
    public ResponseEntity<ExamineeComponent> createExaminee(ExamineeComponent examineeComponent) {
        return ResponseEntity.ok(examineeService.createExaminee(examineeComponent));
    }

    @Override
    public ResponseEntity<ExamineeComponent> getExamineeById(Long examineeId) {
        return ResponseEntity.ok(examineeService.getExamineeById(examineeId));
    }

    @Override
    public ResponseEntity<List<ExamineeComponent>> getExaminees() {
        return ResponseEntity.ok(examineeService.getExaminees());
    }
}
