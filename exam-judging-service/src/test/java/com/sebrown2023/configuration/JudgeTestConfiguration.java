package com.sebrown2023.configuration;

import com.sebrown2023.model.dto.SubmissionDto;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

@TestConfiguration
public class JudgeTestConfiguration {
    @Bean
    public ProducerFactory<String, SubmissionDto> consumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildConsumerProperties());
    }
}
