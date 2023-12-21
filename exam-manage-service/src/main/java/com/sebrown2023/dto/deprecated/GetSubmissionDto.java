package com.sebrown2023.dto.deprecated;


import java.util.Date;
import java.util.LinkedHashMap;

@Deprecated
public record GetSubmissionDto(
        TaskDto taskDto,
        LinkedHashMap<TestDto, TestResultDto> testResults,
        ExamSessionDto examSessionDto,
        String userSourceCode,
        Date submitTime
) {
}