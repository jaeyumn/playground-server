package com.playground.domain.member.service;

import com.playground.domain.config.MockConfig;
import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.dto.request.SignUpRequestDto;
import com.playground.global.security.SessionMemberProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberServiceTest extends MockConfig {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SessionMemberProvider sessionMemberProvider;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 가입")
    @Test
    void signUpTest() {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        String email = "test@example.com";

        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();

        // when
        memberService.signUp(requestDto);
        Member member = memberRepository.findByUsername(username).get();

        // then
        assertThat(member.getUsername()).isEqualTo(username);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail().getEmail()).isEqualTo(email);
    }
}
