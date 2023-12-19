package com.sebrown2023.model.exceptions;

public class ExamSessionAlreadyStartedException extends ExamSessionException {
    public ExamSessionAlreadyStartedException() {
        super("Exam has been already started");
    }
}
