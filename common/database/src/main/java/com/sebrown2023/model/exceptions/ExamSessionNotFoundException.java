package com.sebrown2023.model.exceptions;

public class ExamSessionNotFoundException extends ExamSessionException {

    public ExamSessionNotFoundException() {
        super("Exam session not found");
    }
}
