package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class ExamSessionFinishedException extends ExamSessionException {

    public ExamSessionFinishedException() {
        super(HttpStatus.BAD_REQUEST, "Exam session has been finished");
    }
}
