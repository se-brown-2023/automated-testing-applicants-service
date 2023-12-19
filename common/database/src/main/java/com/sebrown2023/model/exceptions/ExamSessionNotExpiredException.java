package com.sebrown2023.model.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionNotExpiredException extends ExamSessionException {
    public ExamSessionNotExpiredException() {
        super(HttpStatus.BAD_REQUEST, "Exam session has not expired yet");
    }
}
