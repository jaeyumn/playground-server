package com.playground.infra.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
