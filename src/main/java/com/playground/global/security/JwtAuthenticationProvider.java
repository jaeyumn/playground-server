package com.playground.global.security;

import com.playground.global.redis.RedisRepository;
import com.playground.infra.auth.domain.JwtManager;
import com.playground.infra.auth.domain.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class JwtAuthenticationProvider {

    private final JwtManager jwtManager;
    private final RedisRepository redisRepository;

    protected Authentication authenticateAccessToken(String accessToken) {
        if (!jwtManager.validate(accessToken)) {
            return null;
        }
        if (StringUtils.hasText(redisRepository.get(accessToken))) {
            return null;
        }

        Payload payload = jwtManager.parse(accessToken);
        String memberId = payload.getId();
        String role = payload.getRole();

        return new UsernamePasswordAuthenticationToken(
                new MemberPrincipal(memberId),
                null,
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }
}
