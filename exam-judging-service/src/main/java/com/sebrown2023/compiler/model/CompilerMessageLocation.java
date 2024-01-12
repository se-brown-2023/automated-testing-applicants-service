package com.sebrown2023.compiler.model;

public record CompilerMessageLocation(
        String path,
        int line,
        int column,
        String lineContent
) implements CompilerMessageSourceLocation {
    @Override
    public int lineEnd() {
        return -1;
    }

    @Override
    public int columnEnd() {
        return -1;
    }

    public static CompilerMessageLocation create(String path, int line, int column, String lineContent) {
        return path == null ? null : new CompilerMessageLocation(path, line, column, lineContent);
    }
}
