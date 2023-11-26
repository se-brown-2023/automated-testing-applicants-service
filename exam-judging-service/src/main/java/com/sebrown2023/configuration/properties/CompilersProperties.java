package com.sebrown2023.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "compilers")
public record CompilersProperties(
        Map<CompilerName, CompilerSettings> compilerSettingsMap
) {
    public enum CompilerName {
        JAVA
    }

    public record CompilerSettings(
            int executionThreads
    ) {

    }
}
