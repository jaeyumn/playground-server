package com.playground.infra.auth.infrastructure;

import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import com.playground.global.security.Role;
import com.playground.infra.auth.domain.JwtManager;
import com.playground.infra.auth.domain.Payload;
import com.playground.infra.auth.domain.Token;
import com.playground.infra.auth.domain.Tokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtManagerImpl implements JwtManager {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return false;
        }

        return true;
    }

    @Override
    public Token createMemberAccessToken(String memberId) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessTokenExpSec());
        String tokenValue = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim("role", Role.MEMBER.getValue())
                .setSubject(memberId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .compact();

        return new Token(tokenValue, now, expiration);
    }

    @Override
    public Token createMemberRefreshToken(String memberId) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshTokenExpSec());
        String tokenValue = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .compact();

        Token refreshToken = new Token(tokenValue, now, expiration);
        refreshTokenRepository.add(refreshToken.getValue(), memberId, refreshToken.getExpiredAt());

        return refreshToken;
    }

    @Override
    public Payload parse(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new Payload(
                claims.getSubject(),
                claims.get("role", String.class),
                claims.getIssuedAt().toInstant(),
                claims.getExpiration().toInstant()
        );
    }

    @Override
    public Tokens issueTokens(String memberId) {
        Token accessToken = createMemberAccessToken(memberId);
        Token refreshToken = createMemberRefreshToken(memberId);

        return new Tokens(accessToken, refreshToken);
    }

    @Override
    public Tokens reissueTokens(String refreshTokenValue) {
        if (!validate(refreshTokenValue)) {
            throw new CustomApiException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String memberId = refreshTokenRepository.findMemberIdByRefreshToken(refreshTokenValue)
                .orElseThrow(() -> new CustomApiException(ErrorCode.MEMBER_NOT_AUTHENTICATED));

        Token accessToken = createMemberAccessToken(memberId);
        Payload refreshTokenPayload = parse(refreshTokenValue);
        long remainSec = Duration.between(Instant.now(), refreshTokenPayload.getExpiredAt()).toSeconds();

        Token refreshToken = null;
        if (remainSec <= jwtProperties.getRefreshTokenReissueCondSec()) {
            refreshToken = createMemberRefreshToken(memberId);
        }

        return new Tokens(accessToken, refreshToken);
    }

    @Override
    public void prohibitTokens(String accessToken, String refreshToken) {
        String BEARER_TYPE = "Bearer ";
        String accessTokenValue = accessToken.substring(BEARER_TYPE.length());
        Instant expirationOfAccessToken = parse(accessTokenValue).getExpiredAt();

        refreshTokenRepository.prohibitAccessToken(accessTokenValue, refreshToken, "logout", expirationOfAccessToken);
    }
}
