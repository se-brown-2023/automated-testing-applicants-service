package com.sebrown2023.controllers;

import com.sebrown2023.dto.deprecated.PostTestDto;
import com.sebrown2023.dto.deprecated.TestDto;
import com.sebrown2023.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @GetMapping(value = "/{testId}")
    public ResponseEntity<TestDto> getTest(@PathVariable long testId) {
        var testDto = testService.getTestDtoById(testId);
        return ResponseEntity.ok(testDto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<TestDto>> getAllTest() {
        var testsDto = testService.getAllTestsDto();
        return ResponseEntity.ok(testsDto);
    }

    @PostMapping()
    public ResponseEntity<?> createNewTest(PostTestDto postTestDto) {
        testService.createTest(postTestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable long testId) {
        testService.deleteTest(testId);
        return ResponseEntity.ok().build();
    }

    //TODO Возможно нужен ендпойнт для update
}
