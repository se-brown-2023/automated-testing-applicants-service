package com.sebrown2023.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "java-compiler")
public record JavaCompilerProperties(
        String libDir
) {
}
