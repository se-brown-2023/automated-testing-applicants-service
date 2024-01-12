package com.sebrown2023.exceptions;

import com.sebrown2023.model.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NoElementException.class)
    public ResponseEntity<ApiError> handleNoElementException(NoElementException e, WebRequest request) {
        ApiError responseError = new ApiError();

        var method = ((ServletWebRequest) request).getHttpMethod();
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();
        var requestInfo = method + " " + url;

        responseError.setMessage(e.getMessage());

        log.error("Message: {}, requset: {}, time: {}",
                responseError.getMessage(),
                requestInfo,
                LocalDateTime.now());

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApiException(Exception handledEx, WebRequest request) {
        ApiError responseError = new ApiError();

        var method = ((ServletWebRequest) request).getHttpMethod();
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();
        var requestInfo = method + " " + url;

        responseError.setMessage(handledEx.getMessage());

        log.error("Message: {}, requset: {}, time: {}",
                responseError.getMessage(),
                requestInfo,
                LocalDateTime.now());

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
