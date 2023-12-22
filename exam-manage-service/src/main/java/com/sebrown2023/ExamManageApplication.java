package com.sebrown2023;

import com.sebrown2023.mappers.ExamComponentToExamMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class ExamManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamManageApplication.class, args);
    }

    //TODO Добавить кастомные Exceptions и проверки на null (сделать заглушки)

    //TODO Реализовать ExceptionHandler

    //TODO Реализовать транзакции там, где они необходимы

    //TODO прикрутить openApi и заменить Dto классы на нормальные модели из спецификации

    //TODO добавить логирование

    //TODO подумать что делать со связанными testResult

    ////TODO проверить работу методов репозитория
    ////TODO корректно настроить мапперы

    ///TODO пока живем без апдейтов сущностей(только создание и удаления)
    @Bean
    public ExamComponentToExamMapperImpl examComponentToExamMapper() {
        // instantiate and configure MyBean obj
        return new ExamComponentToExamMapperImpl();
    }

}
