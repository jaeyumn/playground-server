package com.playground.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "존재하지 않는 회원입니다."),
    IS_EXISTS_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_002", "이미 존재하는 회원입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorDescription;
}
