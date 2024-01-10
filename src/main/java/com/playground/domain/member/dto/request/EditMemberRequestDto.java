package com.playground.domain.member.dto.request;

public record EditMemberRequestDto(
        String username,
        String password,
        String name
) {
}
