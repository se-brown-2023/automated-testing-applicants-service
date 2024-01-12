package com.sebrown2023.exceptions;

public class ExamSessionNotFoundException extends NoElementException {
    public ExamSessionNotFoundException(String sessionId) {
        super("Exam session with id" + sessionId);
    }
}
