package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionAlreadyFinishedException extends ExamSessionException{
    public ExamSessionAlreadyFinishedException() {
        super(HttpStatus.BAD_REQUEST, "Exam session has been already finished");
    }
}
