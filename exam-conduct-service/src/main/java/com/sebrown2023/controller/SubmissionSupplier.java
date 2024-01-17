package com.sebrown2023.controller;

import com.sebrown2023.model.dto.Submission;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Controller
public class SubmissionSupplier {

    EmitterProcessor<Submission> processor = EmitterProcessor.create();

    @RequestMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delegateToSupplier(@RequestBody Submission submission) {
        processor.onNext(submission);
    }

    @Bean
    public Supplier<Flux<Submission>> submissionsTopic() {
        return () -> this.processor;
    }
}
