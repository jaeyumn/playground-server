package com.playground.domain.member.service;

import com.playground.domain.member.domain.Email;
import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.domain.PasswordEncoder;
import com.playground.domain.member.dto.request.EditMemberRequestDto;
import com.playground.domain.member.dto.request.SignUpRequestDto;
import com.playground.domain.member.dto.response.FindMemberResponseDto;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import com.playground.global.security.SessionMemberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final SessionMemberProvider sessionMemberProvider;
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
        Email email = new Email(requestDto.getEmail());
        memberRepository.saveEmail(email);

        memberRepository.saveMember(
                Member.builder()
                        .username(username)
                        .password(requestDto.getPassword())
                        .name(requestDto.getName())
                        .email(email)
                        .passwordEncoder(passwordEncoder)
                        .build()
        );
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public void editMember(EditMemberRequestDto requestDto) {
        Member member = memberRepository.findByUsername(requestDto.username())
                .orElseThrow(() -> new CustomApiException(ErrorCode.MEMBER_NOT_FOUND));
        member.edit(requestDto.name(), requestDto.password(), passwordEncoder);
    }

    /**
     * 회원 정보 조회
     */
    public FindMemberResponseDto findMember() {
        String memberId = sessionMemberProvider.getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.MEMBER_NOT_FOUND));

        return new FindMemberResponseDto(member.getUsername(), member.getName(), member.getEmail().getEmail());
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void withdrawMember() {
        String memberId = sessionMemberProvider.getMemberId();
        memberRepository.deleteMember(memberId);
    }
}
