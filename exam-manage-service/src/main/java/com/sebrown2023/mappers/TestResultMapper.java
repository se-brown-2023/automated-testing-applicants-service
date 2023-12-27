package com.sebrown2023.mappers;

import com.sebrown2023.model.db.TestResult;
import com.sebrown2023.model.dto.TestResultComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestResultMapper {
    @Mapping(target = "testId", expression = "java(testResult.getTest().getId())")
    @Mapping(target = "submissionId", expression = "java(testResult.getSubmission().getId())")
    @Mapping(target = "elapsedTime", expression = "java(Long.valueOf(testResult.getElapsedTime().toSeconds()).intValue())")
    TestResultComponent testResultToTestResultComponent(TestResult testResult);
}
