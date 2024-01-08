package com.playground.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 6, max = 20, message = "아이디는 6-20자 이내여야 합니다.")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 20, message = "이름은 20자를 초과할 수 없습니다.")
    private String name;

}
