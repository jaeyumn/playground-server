package com.playground.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 6, max = 20, message = "아이디는 6-20자 이내여야 합니다.")
    private String username;

    @NotBlank
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotBlank
    @Size(max = 20, message = "이름은 20자를 초과할 수 없습니다.")
    private String name;

//    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
//    private String phoneNumber;

    @Email
    private String email;

    @Builder
    public SignUpRequestDto(String username, String password, String nickname, String name, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
    }
}
