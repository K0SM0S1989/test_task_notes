package com.krd.TestTaskNotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleContentNotFoundNews(BadRequestException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
