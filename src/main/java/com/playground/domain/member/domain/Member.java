package com.playground.domain.member.domain;

import com.playground.global.BaseEntity;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id")
    private Email email;

    @Builder
    public Member(String username, String password, String name, String email, PasswordEncoder passwordEncoder) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.name = name;
        this.email = new Email(email, this);
    }

    public void checkPassword(String targetPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.match(targetPassword, this.password)) {
            throw new CustomApiException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
