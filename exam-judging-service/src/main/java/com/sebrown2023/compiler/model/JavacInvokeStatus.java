package com.sebrown2023.compiler.model;

import java.util.List;

public record JavacInvokeStatus(
        String combinedOutput,
        boolean isCompileSuccess,
        boolean hasException,
        boolean hasTimeout,
        long compilerExecTimeMillis,
        List<? extends CompilerMessageSourceLocation> locations
) implements InvokeStatus {
}
