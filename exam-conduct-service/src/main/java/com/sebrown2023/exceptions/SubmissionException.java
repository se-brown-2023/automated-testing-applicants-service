package com.sebrown2023.exceptions;

import org.springframework.http.HttpStatus;

public class SubmissionException extends RuntimeException {

    public SubmissionException(Throwable e) {
        super(e);
    }

    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
