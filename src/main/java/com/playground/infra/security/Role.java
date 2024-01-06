package com.playground.infra.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    ;

    private final String value;
}
