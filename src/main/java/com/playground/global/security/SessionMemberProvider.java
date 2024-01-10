package com.playground.global.security;

import org.springframework.stereotype.Service;

@Service
public class SessionMemberProvider {

    public String getMemberId() {
        MemberPrincipal memberPrincipal = SecurityUtils.getMemberPrincipal();

        return memberPrincipal.memberId();
    }
}
