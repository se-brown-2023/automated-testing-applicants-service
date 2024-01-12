package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionAlreadyStartedException extends ExamSessionException {
    public ExamSessionAlreadyStartedException() {
        super(HttpStatus.BAD_REQUEST, "Exam has been already started");
    }
}
