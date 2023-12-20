package com.sebrown2023.dto;


import java.util.Date;
import java.util.LinkedHashMap;

public record GetSubmissionDto(
        TaskDto taskDto,
        LinkedHashMap<TestDto, TestResultDto> testResults,
        ExamSessionDto examSessionDto,
        String userSourceCode,
        Date submitTime
) {
}