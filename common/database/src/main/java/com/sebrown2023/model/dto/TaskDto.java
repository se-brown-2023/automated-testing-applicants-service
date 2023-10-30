package com.sebrown2023.model.dto;

import com.sebrown2023.model.db.Exam;


public record TaskDto(
        Exam exam,
        String name,
        String description,
        String authorSourceCode
) {
}
