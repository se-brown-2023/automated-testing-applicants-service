package com.sebrown2023.dto.deprecated;

@Deprecated
public record TestDto (
    Long id,
    String name,
    String inputData,
    String expectedOutputData
) {}
