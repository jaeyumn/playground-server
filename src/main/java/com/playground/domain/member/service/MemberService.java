package com.playground.domain.member.service;

import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.domain.PasswordEncoder;
import com.playground.domain.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    public void signUp(SignUpRequestDto requestDto) {
        memberRepository.save(
                Member.builder()
                        .username(requestDto.username())
                        .password(requestDto.password())
                        .name(requestDto.name())
                        .passwordEncoder(passwordEncoder)
                        .build()
        );
    }
}
