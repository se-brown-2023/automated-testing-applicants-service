package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionNotFoundException extends ExamSessionException {

    public ExamSessionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Exam session not found");
    }
}
