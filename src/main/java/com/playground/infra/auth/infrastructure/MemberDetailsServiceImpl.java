package com.playground.infra.auth.infrastructure;

import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.domain.PasswordEncoder;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import com.playground.infra.auth.service.MemberDetails;
import com.playground.infra.auth.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsServiceImpl implements MemberDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDetails signIn(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomApiException(ErrorCode.MEMBER_NOT_FOUND));
        member.checkPassword(password, passwordEncoder);

        return new MemberDetails(member.getId(), member.getUsername(), member.getName());
    }
}
