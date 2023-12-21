package com.sebrown2023.mappers;

import com.sebrown2023.dto.deprecated.TestResultDto;
import com.sebrown2023.model.db.TestResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestResultMapper {
    TestResultDto testResultToTestResultDto (TestResult testResult);
}
