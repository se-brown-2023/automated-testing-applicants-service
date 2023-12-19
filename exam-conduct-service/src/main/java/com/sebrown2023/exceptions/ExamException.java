package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamException extends Exception {
    private final HttpStatus status;

    public ExamException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
