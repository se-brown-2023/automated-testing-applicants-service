package com.sebrown2023.dto.deprecated;

import java.time.Duration;

@Deprecated
public record TestResultDto (
    Long id,
    String actualOutputData,
    Boolean passed,
    Duration elapsedTime
) {}
