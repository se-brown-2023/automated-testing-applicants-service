package com.sebrown2023.model.exceptions;

public class ExamSessionAlreadyFinishedException extends ExamSessionException{
    public ExamSessionAlreadyFinishedException() {
        super("Exam session has been already finished");
    }
}
