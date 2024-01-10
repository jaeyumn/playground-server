package com.playground.domain.member.service;

import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.domain.PasswordEncoder;
import com.playground.domain.member.dto.SignUpRequestDto;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        if (memberRepository.isExistsMember(username)) {
            throw new CustomApiException(ErrorCode.ALREADY_EXISTS_MEMBER);
        }

        memberRepository.save(
                Member.builder()
                        .username(username)
                        .password(requestDto.getPassword())
                        .name(requestDto.getName())
                        .passwordEncoder(passwordEncoder)
                        .build()
        );
    }
}
