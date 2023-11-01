package com.sebrown2023.model.dto;

import com.sebrown2023.model.db.Task;


public record TestDto(
        Task task,
        String name,
        String inputData,
        String expectedOutputData
) {
}
