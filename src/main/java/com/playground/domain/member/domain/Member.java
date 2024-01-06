package com.playground.domain.member.domain;

import com.playground.global.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;

    @Builder
    public Member(String username, String password, String name, PasswordEncoder passwordEncoder) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.name = name;
    }
}
