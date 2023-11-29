package com.sebrown2023.configuration;

import com.sebrown2023.configuration.properties.CompilersProperties;
import com.sebrown2023.model.dto.SubmissionDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sebrown2023.configuration.properties.CompilersProperties.CompilerName;

@Configuration
@EnableKafkaRetryTopic
public class ExamJudgingConfiguration {
    @Bean(name = "javaCompilerExecutorService")
    ExecutorService javaCompilerExecutorService(CompilersProperties compilersProperties) {
        var javaCompilerSettings = compilersProperties.compilerSettingsMap().get(CompilerName.JAVA);
        assert javaCompilerSettings != null;
        return Executors.newFixedThreadPool(javaCompilerSettings.executionThreads(), Thread.ofVirtual().factory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SubmissionDto> kafkaListenerContainerFactory(
            ConsumerFactory<String, SubmissionDto> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, SubmissionDto> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public ConsumerFactory<String, SubmissionDto> consumerFactory(
            KafkaProperties kafkaProperties
    ) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    TaskScheduler scheduler() {
        var newScheduledThreadPool = Executors.newScheduledThreadPool(
                10, Thread.ofVirtual().factory()
        );
        return new ConcurrentTaskScheduler(newScheduledThreadPool);
    }

    @Bean
    public NewTopic submissionsTopic() {
        return new NewTopic("submissionsTopic", 1, (short) 3);
    }
}
