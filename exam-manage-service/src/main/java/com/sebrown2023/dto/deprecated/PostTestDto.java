package com.sebrown2023.dto.deprecated;

@Deprecated
public record PostTestDto (
        Long taskId,
        String name,
        String inputData,
        String expectedOutputData
){}
