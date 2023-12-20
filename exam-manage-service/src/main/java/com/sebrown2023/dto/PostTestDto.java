package com.sebrown2023.dto;

public record PostTestDto (
        Long taskId,
        String name,
        String inputData,
        String expectedOutputData
){}
