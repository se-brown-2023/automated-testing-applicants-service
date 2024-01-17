package com.sebrown2023.configuration;

import com.sebrown2023.compiler.BaseCompiler;
import com.sebrown2023.compiler.JavaCompiler;
import com.sebrown2023.configuration.properties.SubmissionServiceProperties;
import com.sebrown2023.model.db.ProgrammingLanguage;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Configuration
@EnableKafkaRetryTopic
public class ExamJudgingConfiguration {
    @Bean(name = "submissionExecutorService")
    ExecutorService submissionExecutorService(SubmissionServiceProperties compilersProperties) {
        return Executors.newFixedThreadPool(compilersProperties.executionThreads(), Thread.ofVirtual().factory());
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
    public ConsumerFactory<String, SubmissionDto> consumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    TaskScheduler scheduler() {
        var newScheduledThreadPool = Executors.newScheduledThreadPool(10, Thread.ofVirtual().factory());
        return new ConcurrentTaskScheduler(newScheduledThreadPool);
    }

    @Bean
    public NewTopic submissionsTopic() {
        return new NewTopic("submissionsTopic-out-0", 1, (short) 3);
    }

    @Bean
    public Map<ProgrammingLanguage, BaseCompiler> compilers(List<BaseCompiler> compilers) {
        return compilers.stream().collect(Collectors.toMap(baseCompiler -> switch (baseCompiler) {
            case JavaCompiler _ -> ProgrammingLanguage.JAVA;
            case null, default -> throw new UnsupportedOperationException();
        }, baseCompiler -> baseCompiler));
    }
}
