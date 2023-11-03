package com.sebrown2023.model.exceptions;

public class ExamSessionNotStartedException extends ExamSessionException {

    public ExamSessionNotStartedException() {
        super("Exam session not started. ");
    }
}
