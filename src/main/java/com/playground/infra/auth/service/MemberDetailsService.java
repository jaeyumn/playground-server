package com.playground.infra.auth.service;

public interface MemberDetailsService {

    MemberDetails signIn(String username, String password);
}
