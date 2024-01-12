package com.playground.domain.config;

import com.playground.global.config.MailConfig;
import com.playground.infra.auth.infrastructure.JwtProperties;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class MockConfig {

    @MockBean
    private JwtProperties jwtProperties;
//    private MailConfig mailConfig;
}
