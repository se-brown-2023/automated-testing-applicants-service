package com.sebrown2023.services;

import com.sebrown2023.dto.PostTestDto;
import com.sebrown2023.dto.TestDto;
import com.sebrown2023.mappers.TestMapper;
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

    public TestDto getTestDtoById(long testId) {
        var test = testRepository.findTestById(testId);
        return testMapper.testToTestDto(test);
    }

    public List<TestDto> getAllTestsDto() {
        var tests = testRepository.findAll();
        return tests.stream()
                .map(testMapper::testToTestDto)
                .collect(Collectors.toList());
    }

    public void createTest(PostTestDto postTestDto) {
        testRepository.save(testMapper.postTestDtoToTest(postTestDto));
    }

    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);
    }

}
