package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean match(String targetPassword, String encodedPassword) {
        return passwordEncoder.matches(targetPassword, encodedPassword);
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
