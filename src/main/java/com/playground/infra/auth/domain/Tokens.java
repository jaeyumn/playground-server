package com.playground.infra.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tokens {

    private Token accessToken;
    private Token refreshToken;
}
