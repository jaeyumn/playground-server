package com.playground.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public record SendVerificationEmailRequestDto(
        String email
) {
}
