package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamNotFoundException extends ExamException {
    public ExamNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Exam not found");
    }
}
