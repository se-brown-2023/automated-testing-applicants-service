package com.sebrown2023.model.dto;

import com.sebrown2023.model.db.Submission;
import com.sebrown2023.model.db.Test;

import java.time.Duration;


public record TestResultDto(
        Submission submission,
        Test test,
        String actualOutputData,
        Boolean passed,
        Duration elapsedTime
) {
}
