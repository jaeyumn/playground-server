package com.playground.domain.config;

import com.playground.domain.member.service.EmailService;
import com.playground.global.config.MailConfig;
import com.playground.infra.auth.domain.JwtManager;
import com.playground.infra.auth.infrastructure.JwtProperties;
import com.playground.infra.auth.service.AuthService;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class MockConfig {

    @MockBean
    EmailService emailService;

    @MockBean
    AuthService authService;

    @MockBean
    JwtManager jwtManager;

    @MockBean
    JwtProperties jwtProperties;

    @MockBean
    MailConfig mailConfig;
}
