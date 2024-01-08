package com.playground.infra.auth.controller;

import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import com.playground.infra.auth.domain.Tokens;
import com.playground.infra.auth.dto.SignInRequestDto;
import com.playground.infra.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final String BEARER_TYPE = "Bearer ";

    private final AuthService authService;

    @PostMapping("/auth/sign-in")
    public ResponseEntity<Void> signIn(@RequestBody @Valid SignInRequestDto requestDto) {
        Tokens tokens = authService.signIn(requestDto);
        ResponseCookie refreshTokenCookie =
                ResponseCookie
                        .from("refreshToken", tokens.getRefreshToken().getValue())
                        .path("/")
                        .httpOnly(true)
                        .maxAge(Duration.between(Instant.now(), tokens.getRefreshToken().getExpiredAt()))
                        .build();

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers -> {
                    headers.add(
                            HttpHeaders.AUTHORIZATION,
                            BEARER_TYPE + tokens.getAccessToken().getValue()
                    );
                    headers.add(
                            HttpHeaders.SET_COOKIE,
                            refreshTokenCookie.toString()
                    );
                })
                .build();
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String refreshToken = getRefreshTokenValue(request);
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        authService.logout(accessToken, refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .headers(headers -> headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()))
                .build();
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<Void> reissueToken(HttpServletRequest request) {
        String refreshToken = getRefreshTokenValue(request);

        Tokens tokens = authService.reissue(refreshToken);
        String refreshTokenCookie =
                tokens.getRefreshToken() != null ?
                        ResponseCookie
                                .from("refreshToken", tokens.getRefreshToken().getValue())
                                .path("/")
                                .httpOnly(true)
                                .maxAge(Duration.between(Instant.now(), tokens.getRefreshToken().getExpiredAt()).getSeconds())
                                .build()
                                .toString()
                        : null;

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers -> {
                    headers.add(
                            HttpHeaders.AUTHORIZATION,
                            BEARER_TYPE + tokens.getAccessToken().getValue()
                    );
                    if (refreshTokenCookie != null) {
                        headers.add(
                                HttpHeaders.SET_COOKIE,
                                refreshTokenCookie
                        );
                    }
                })
                .build();
    }

    private String getRefreshTokenValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new CustomApiException(ErrorCode.MEMBER_NOT_AUTHENTICATED);
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new CustomApiException(ErrorCode.MEMBER_NOT_AUTHENTICATED));
    }
}
