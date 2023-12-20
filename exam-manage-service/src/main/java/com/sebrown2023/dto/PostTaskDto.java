package com.sebrown2023.dto;

import java.util.List;

public record PostTaskDto(
        Long examId,
        String name,
        String description,
        String authorSourceCode,
        List<PostTestDto> tests
) { }
