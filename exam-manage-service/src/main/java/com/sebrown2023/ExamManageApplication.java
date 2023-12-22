package com.sebrown2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class ExamManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamManageApplication.class, args);
    }

    //TODO Реализовать ExceptionHandler
    //TODO добавить логирование
    //TODO подумать что делать со связанными testResult
    ///TODO пока живем без апдейтов сущностей(только создание и удаления)

}
