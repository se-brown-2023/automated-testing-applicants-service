package com.sebrown2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class ExamJudgingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamJudgingApplication.class, args);
    }
}
