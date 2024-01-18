package com.sebrown2023.controller;

import com.sebrown2023.exceptions.ExamException;
import com.sebrown2023.exceptions.ExamSessionException;
import com.sebrown2023.exceptions.SubmissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExamSessionException.class)
    public ResponseEntity<ApiErrorDto> handleApiError(ExamSessionException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ApiErrorDto(exception.getStatus().getReasonPhrase(), exception.getMessage()));
    }

    @ExceptionHandler(ExamException.class)
    public ResponseEntity<ApiErrorDto> handleApiError(ExamException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ApiErrorDto(exception.getStatus().getReasonPhrase(), exception.getMessage()));
    }

    @ExceptionHandler(SubmissionException.class)
    public ResponseEntity<ApiErrorDto> handleApiError(SubmissionException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ApiErrorDto(exception.getStatus().getReasonPhrase(), exception.getMessage()));
    }
}
