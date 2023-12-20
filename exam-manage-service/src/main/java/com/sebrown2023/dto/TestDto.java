package com.sebrown2023.dto;

public record TestDto (
    Long id,
    String name,
    String inputData,
    String expectedOutputData
) {}
