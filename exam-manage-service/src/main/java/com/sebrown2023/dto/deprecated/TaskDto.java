package com.sebrown2023.dto.deprecated;

@Deprecated
public record TaskDto (
    Long id,
    Long examId,
    String name,
    String description,
    String authorSourceCode
) {
}
