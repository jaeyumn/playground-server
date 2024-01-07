package com.playground.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorResponse errorResponse;

    public CustomApiException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.errorResponse = new ErrorResponse(errorCode);
    }
}
