package com.playground.domain.member.service;

import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.domain.PasswordEncoder;
import com.playground.domain.member.dto.SignUpRequestDto;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
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
        String username = requestDto.username();
        if (memberRepository.isExistsMember(username)) {
            throw new CustomApiException(ErrorCode.IS_EXISTS_MEMBER);
        }

        memberRepository.save(
                Member.builder()
                        .username(username)
                        .password(requestDto.password())
                        .name(requestDto.name())
                        .passwordEncoder(passwordEncoder)
                        .build()
        );
    }
}
