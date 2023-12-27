package com.sebrown2023.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "submission-service")
public record SubmissionServiceProperties(
        int executionThreads
) {
}
