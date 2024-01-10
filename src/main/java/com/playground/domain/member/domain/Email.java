package com.playground.domain.member.domain;

import com.playground.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Email extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;
    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "email")
    private Member member;

    public Email(String email) {
        this.email = email;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
