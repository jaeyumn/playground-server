package com.playground.domain.member.dto.request;

import lombok.Builder;

@Builder
public record EditMemberRequestDto(
        String username,
        String password,
        String name
) {
}
