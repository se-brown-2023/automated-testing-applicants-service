package com.sebrown2023.configuration;

import com.sebrown2023.configuration.properties.CompilersProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sebrown2023.configuration.properties.CompilersProperties.CompilerName;

@Configuration
public class ExamJudgingConfiguration {
    @Bean(name = "javaCompilerExecutorService")
    ExecutorService javaCompilerExecutorService(CompilersProperties compilersProperties) {
        var javaCompilerSettings = compilersProperties.compilerSettingsMap().get(CompilerName.JAVA);
        assert javaCompilerSettings != null;
        return Executors.newFixedThreadPool(javaCompilerSettings.executionThreads(), Thread.ofVirtual().factory());
    }
}
