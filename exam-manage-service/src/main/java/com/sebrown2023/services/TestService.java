package com.sebrown2023.services;

import com.sebrown2023.exceptions.NoElementException;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.model.dto.TestComponent;
import com.sebrown2023.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    private final TestMapper testMapper;

    public TestComponent getTestComponentById(long testId) {
        var test = testRepository.findTestById(testId);

        if (test.isPresent()) {
            return testMapper.testToTestComponent(test.get());
        } else throw new NoElementException();
    }

    public List<TestComponent> getAllTestsComponents() {
        var tests = testRepository.findAll();
        return tests.stream()
                .map(testMapper::testToTestComponent)
                .collect(Collectors.toList());
    }

    public List<TestComponent> getAllTestComponentsByTaskId(long taskId) {
        var tests = testRepository.findTestsByTaskId(taskId);
        return tests.stream().map(testMapper::testToTestComponent)
                .collect(Collectors.toList());
    }

    public TestComponent createTest(TestComponent testComponent) {
        var createdTest = testRepository.save(testMapper.testComponentToTest(testComponent));
        return testMapper.testToTestComponent(createdTest);
    }

    public void deleteTest(Long testId) {
        testRepository.deleteTestById(testId);
    }
}
