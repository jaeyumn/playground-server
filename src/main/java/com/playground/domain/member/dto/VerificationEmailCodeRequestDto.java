package com.playground.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerificationEmailCodeRequestDto {

    private String email;
    private String code;
}
