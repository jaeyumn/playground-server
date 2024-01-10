package com.playground.domain.member.dto.response;

public record FindMemberResponseDto(
        String username,
        String name,
        String email
) {}
