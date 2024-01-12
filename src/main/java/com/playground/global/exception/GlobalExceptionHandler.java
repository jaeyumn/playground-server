package com.playground.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customExceptionHandle(CustomApiException e) {
        log.error("Error occurs: {}", e.getErrorResponse());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.error("Error occurs: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();

        // 필드 오류 메시지 하나로 묶기
        String errorDescription = bindingResult.getFieldErrors().stream()
                .map(this::parseFieldErrorMessage)
                .collect(Collectors.joining(","));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDescription);
    }

    private String parseFieldErrorMessage(FieldError fieldError) {
        return fieldError.getField() + ":" + fieldError.getDefaultMessage();
    }
}
