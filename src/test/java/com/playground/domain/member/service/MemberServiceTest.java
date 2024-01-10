package com.playground.domain.member.service;

import com.playground.domain.config.MockConfig;
import com.playground.domain.member.domain.Email;
import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.domain.PasswordEncoder;
import com.playground.domain.member.dto.request.EditMemberRequestDto;
import com.playground.domain.member.dto.request.SignUpRequestDto;
import com.playground.domain.member.dto.response.FindMemberResponseDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberServiceTest extends MockConfig{

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Email email = new Email("test@email.com");
        memberRepository.saveEmail(email);
        entityManager.flush();

        memberRepository.saveMember(
                Member.builder()
                        .username("testUser")
                        .password("123456")
                        .name("test")
                        .email(email)
                        .passwordEncoder(passwordEncoder)
                        .build()
        );
        entityManager.flush();
    }

    @DisplayName("회원 가입")
    @Test
    void signUpTest() {
        // given
        String username = "testId";
        String name = "test";
        String email = "test1@email.com";

        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .username(username)
                .password("1234")
                .name(name)
                .email(email)
                .build();

        // when
        memberService.signUp(requestDto);

        // then
        Member member = memberRepository.findByUsername(username).get();
        assertThat(member.getUsername()).isEqualTo(username);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail().getEmail()).isEqualTo(email);
    }

    @DisplayName("회원 정보 수정")
    @Test
    void editMemberTest() {
        // given
        String username = "testUser";
        String editName = "edit";
        EditMemberRequestDto requestDto = EditMemberRequestDto.builder()
                .username(username)
                .name(editName)
                .build();

        // when
        memberService.editMember(requestDto);

        // then
        Member member = memberRepository.findByUsername(username).get();
        assertThat(member.getName()).isEqualTo(editName);
    }

    @Disabled
    @DisplayName("회원 정보 조회")
    @Test
    void findMemberTest() {
        // given
        String username = "testUser";
        String email = "test@email.com";
        String name = "test";

        // when
        FindMemberResponseDto responseDto = memberService.findMember();

        // then
        assertThat(responseDto.username()).isEqualTo(username);
        assertThat(responseDto.email()).isEqualTo(email);
        assertThat(responseDto.name()).isEqualTo(name);
    }
}