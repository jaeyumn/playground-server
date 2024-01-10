package com.playground.domain.member.service;

public interface EmailService {

    void sendAuthCode(String email);

    void verifiedAuthCode(String email, String code);
}
