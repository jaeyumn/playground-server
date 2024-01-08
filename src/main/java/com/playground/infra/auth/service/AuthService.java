package com.playground.infra.auth.service;

import com.playground.infra.auth.domain.JwtManager;
import com.playground.infra.auth.domain.Tokens;
import com.playground.infra.auth.dto.SignInRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final JwtManager jwtManager;
    private final MemberDetailsService memberDetailsService;

    public Tokens signIn(SignInRequestDto requestDto) {
        MemberDetails memberDetails = memberDetailsService.signIn(requestDto.getUsername(), requestDto.getPassword());

        return jwtManager.issueTokens(memberDetails.getMemberId());
    }

    public void logout(String accessToken, String refreshToken) {
        jwtManager.prohibitTokens(accessToken, refreshToken);
    }

    public Tokens reissue(String refreshToken) {
        return jwtManager.reissueTokens(refreshToken);
    }
}
