package com.playground.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customExceptionHandle(CustomApiException e) {
        log.error("Error occurs {}", e.getErrorResponse());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getErrorResponse());
    }
}
