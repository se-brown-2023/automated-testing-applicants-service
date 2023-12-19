package com.sebrown2023.model.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionException extends Exception {

    private final HttpStatus status;

    public ExamSessionException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
