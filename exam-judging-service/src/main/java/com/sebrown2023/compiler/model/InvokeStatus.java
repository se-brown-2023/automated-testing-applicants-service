package com.sebrown2023.compiler.model;

import java.util.List;

public sealed interface InvokeStatus permits JavacInvokeStatus {
    String combinedOutput();

    boolean isCompileSuccess();

    boolean hasException();

    boolean hasTimeout();

    long compilerExecTimeMillis();

    List<? extends CompilerMessageSourceLocation> locations();

    default boolean hasCompilationError() {
        return !isCompileSuccess();
    }
}
