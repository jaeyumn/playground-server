package com.playground.domain.member.dto;

import java.util.Objects;

public record SignUpRequestDto(String username, String password, String name) {

    public SignUpRequestDto {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(name);
    }
}
