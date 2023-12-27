package com.sebrown2023.dto.deprecated;

import java.util.List;

@Deprecated
public record PostTaskDto(
        Long examId,
        String name,
        String description,
        String authorSourceCode,
        List<PostTestDto> tests
) { }
