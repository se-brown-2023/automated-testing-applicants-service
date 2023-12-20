package com.sebrown2023.controller;

import com.sebrown2023.exceptions.ExamSessionException;
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
}
