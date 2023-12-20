package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionExpiredException extends ExamSessionException {

    public ExamSessionExpiredException() {
        super(HttpStatus.BAD_REQUEST, "Exam session has been expired");
    }
}
