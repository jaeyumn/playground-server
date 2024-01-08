package com.playground.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void set(String key, String data, Duration duration) {
        redisTemplate.opsForValue().set(key, data, duration);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
