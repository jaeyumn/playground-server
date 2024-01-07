package com.playground.global.exception;

public record ErrorResponse (String errorCode, String errorDescription) {

    public ErrorResponse(ErrorCode errorCode) {
        this(errorCode.getErrorCode(), errorCode.getErrorDescription());
    }
}
