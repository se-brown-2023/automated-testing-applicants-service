package com.sebrown2023.exceptions;

import com.sebrown2023.model.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApiException(Exception handledEx, WebRequest request) {
        ApiError responseError = new ApiError();

        var method = ((ServletWebRequest) request).getHttpMethod();
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();
        var requestInfo = method + " " + url;

        responseError.setTitle("Error");
        responseError.setMessage(handledEx.getMessage());
        responseError.setRequest(requestInfo);
        responseError.setTime(LocalDateTime.now().toString());

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
