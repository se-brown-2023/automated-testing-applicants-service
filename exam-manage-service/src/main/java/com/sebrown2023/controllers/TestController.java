package com.sebrown2023.controllers;

import com.sebrown2023.controller.TestApi;
import com.sebrown2023.model.dto.TestComponent;
import com.sebrown2023.services.TestService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController implements TestApi {
    private final TestService testService;

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<TestComponent> getTest(Long testId) {
        var testDto = testService.getTestComponentById(testId);
        return ResponseEntity.ok(testDto);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<TestComponent>> getAllTests() {
        var testComponents = testService.getAllTestsComponents();
        return ResponseEntity.ok(testComponents);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<TestComponent>> getTestsForTask(Long testId) {
        var testComponents = testService.getAllTestComponentsByTaskId(testId);
        return ResponseEntity.ok(testComponents);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<TestComponent> createTest(TestComponent testComponent) {
        var createdTest = testService.createTest(testComponent);
        return ResponseEntity.ok().body(createdTest);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteTest(Long testId) {
        testService.deleteTest(testId);
        return ResponseEntity.ok().build();
    }
}
