package com.playground.domain.member.dto.request;

public record VerificationEmailCodeRequestDto(
        String email,
        String code
) {
}
