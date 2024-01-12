package com.sebrown2023.model;

import java.nio.file.Path;
import java.util.List;

public record WrappedCode(
        String mainMethod,
        List<Path> sourceCode,
        Path sourceDir
) {
}
