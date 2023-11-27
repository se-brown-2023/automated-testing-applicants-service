package com.sebrown2023.model.exceptions;

public class ExamSessionNotExpiredException extends ExamSessionException {
    public ExamSessionNotExpiredException() {
        super("Exam session has not expired yet");
    }
}
