package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.member.service.EmailService;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import com.playground.global.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailServiceImpl implements EmailService {

    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private final JavaMailSender mailSender;

    private final String AUTH_CODE_PREFIX = "EmailAuthCode ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    /**
     * 인증 메일 전송
     */
    @Override
    public void sendAuthCode(String email) {
        if (memberRepository.isExistsEmail(email)) {
            throw new CustomApiException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }

        String title = "[재윤의 놀이터] 이메일 인증 번호입니다.";
        String authCode = createAuthCode();

        sendEmail(email, title, authCode);
        redisRepository.set(AUTH_CODE_PREFIX + email, authCode, Duration.ofMillis(authCodeExpirationMillis));
    }

    private String createAuthCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomApiException(ErrorCode.UNABLE_TO_CREATE_EMAIL_CODE);
        }
    }

    private void sendEmail(String email, String title, String text) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(text, true);
        };

        try {
            mailSender.send(preparator);
        } catch (RuntimeException e) {
            throw new CustomApiException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }
    }

    /**
     * 인증 번호 검증
     */
    @Override
    public void verifiedAuthCode(String email, String code) {
        if (!checkCorrectCode(email, code)) {
            throw new CustomApiException(ErrorCode.INCORRECT_EMAIL_CHECK_CODE);
        }
        redisRepository.remove(AUTH_CODE_PREFIX + email);
    }

    private boolean checkCorrectCode(String email, String code) {
        try {
            return redisRepository.get(AUTH_CODE_PREFIX + email).equals(code);
        } catch (Exception e) {
            throw new CustomApiException(ErrorCode.EMAIL_VERIFICATION_TIME_EXPIRE);
        }
    }

}
