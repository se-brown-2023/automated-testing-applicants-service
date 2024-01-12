package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionNotStartedException extends ExamSessionException {

    public ExamSessionNotStartedException() {
        super(HttpStatus.BAD_REQUEST, "Exam session not started. ");
    }
}
