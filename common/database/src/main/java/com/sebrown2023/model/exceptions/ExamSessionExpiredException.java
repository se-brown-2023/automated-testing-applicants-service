package com.sebrown2023.model.exceptions;

public class ExamSessionExpiredException extends ExamSessionException {

    public ExamSessionExpiredException() {
        super("Exam session has been expired");
    }
}
