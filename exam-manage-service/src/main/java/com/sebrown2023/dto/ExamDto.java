package com.sebrown2023.dto;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public record ExamDto(
        Long id,
        Integer examinerId,
        String name,
        String description,
        String programmingLanguage,
        Duration maxDuration,
        Duration ttl,
        Date creationDate,
        List<TaskDto> tasks
) {
}
