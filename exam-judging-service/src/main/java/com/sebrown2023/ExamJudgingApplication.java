package com.sebrown2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class ExamJudgingApplication {

    public static class Dev {
        public static void main(String[] args) {
            System.setProperty("spring.profiles.active", "dev,local,local-s3");
            System.setProperty("log.dir", "exam-judging-service/logs");
            ExamJudgingApplication.main(args);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamJudgingApplication.class, args);
    }
}
