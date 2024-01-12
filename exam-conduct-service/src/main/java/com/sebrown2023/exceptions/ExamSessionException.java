package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionException extends RuntimeException {

    private final HttpStatus status;

    public ExamSessionException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
