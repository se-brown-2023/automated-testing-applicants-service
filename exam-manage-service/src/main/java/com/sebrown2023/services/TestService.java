package com.sebrown2023.services;

import com.sebrown2023.exceptions.TestNotFoundException;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.model.dto.TestComponent;
import com.sebrown2023.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    private final TestMapper testMapper;

    public TestComponent getTestComponentById(long testId) {
        var test = testRepository.findTestById(testId)
                .orElseThrow(() -> new TestNotFoundException(testId));
        return testMapper.testToTestComponent(test);
    }

    public List<TestComponent> getAllTestsComponents() {
        var tests = testRepository.findAll();
        return tests.stream()
                .map(testMapper::testToTestComponent)
                .toList();
    }

    public List<TestComponent> getAllTestComponentsByTaskId(long taskId) {
        var tests = testRepository.findTestsByTaskId(taskId);
        return tests.stream().map(testMapper::testToTestComponent)
                .toList();
    }

    public TestComponent createTest(TestComponent testComponent) {
        var createdTest = testRepository.save(testMapper.testComponentToTest(testComponent));
        return testMapper.testToTestComponent(createdTest);
    }

    public void deleteTest(Long testId) {
        testRepository.deleteTestById(testId);
    }
}
