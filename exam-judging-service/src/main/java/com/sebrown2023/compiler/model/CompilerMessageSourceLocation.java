package com.sebrown2023.compiler.model;

public sealed interface CompilerMessageSourceLocation permits CompilerMessageLocation {
    String path();

    int line();

    int column();

    int lineEnd();

    int columnEnd();

    String lineContent();

}
