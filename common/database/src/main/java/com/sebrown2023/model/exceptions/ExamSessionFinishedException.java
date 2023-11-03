package com.sebrown2023.model.exceptions;

public class ExamSessionFinishedException extends ExamSessionException {

    public ExamSessionFinishedException() {
        super("Exam session has been finished");
    }
}
