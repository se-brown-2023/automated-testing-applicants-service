package com.sebrown2023.dto;

import com.sebrown2023.model.db.Exam;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

public record TaskDto (
    Long id,
    Long examId,
    String name,
    String description,
    String authorSourceCode
) {
}
