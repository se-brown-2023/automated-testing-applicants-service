package com.sebrown2023.model.dto;

import java.time.Duration;
import java.util.Date;

public record ExamDto(
        Integer examinerId,
        String name,
        String description,
        String programmingLanguage,
        Duration maxDuration,
        Duration ttl,
        Date creationDate
) {
}
