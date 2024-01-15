package com.playground.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Auth
    MEMBER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "AUTH_001", "인증되지 않은 사용자입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "액세스 토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "리프레시 토큰이 유효하지 않습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.FORBIDDEN, "AUTH_004", "해당 요청에 대한 권한이 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "존재하지 않는 회원입니다."),
    ALREADY_EXISTS_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_002", "이미 존재하는 회원입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "MEMBER_003", "비밀번호가 일치하지 않습니다."),
    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER_004", "이미 존재하는 이메일입니다."),
    UNABLE_TO_CREATE_EMAIL_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "MEMBER_005", "인증 번호 생성에 실패했습니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "MEMBER_006", "인증 번호 전송에 실패했습니다."),
    INCORRECT_EMAIL_CHECK_CODE(HttpStatus.BAD_REQUEST, "MEMBER_007", "인증 번호가 일치하지 않습니다."),
    EMAIL_VERIFICATION_TIME_EXPIRE(HttpStatus.BAD_REQUEST, "MEMBER_008", "인증 시간이 만료되었습니다."),

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_001", "존재하지 않는 카테고리입니다."),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_001", "존재하지 않는 게시글입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorDescription;
    }
