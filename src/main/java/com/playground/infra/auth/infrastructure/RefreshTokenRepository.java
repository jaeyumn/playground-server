package com.playground.infra.auth.infrastructure;

import com.playground.global.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RefreshTokenRepository {

    private final RedisRepository redisRepository;

    public void add(String refreshToken, String memberId, Instant expiry) {
        redisRepository.set(refreshToken, memberId, Duration.between(Instant.now(), expiry));
    }

    public Optional<String> findMemberIdByRefreshToken(String refreshToken) {
        String memberId = redisRepository.get(refreshToken);
        if (memberId == null) {
            return Optional.empty();
        }
        return Optional.of(memberId);
    }

    public void prohibitAccessToken(String accessToken, String refreshToken, String message, Instant expiry) {
        redisRepository.remove(refreshToken);
        redisRepository.set(accessToken, message, Duration.between(Instant.now(), expiry));
    }
}
