package com.playground.domain.member.domain;

import com.playground.domain.post.domain.Post;
import com.playground.global.BaseEntity;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id")
    private String id;
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id")
    private Email email;

    @OneToMany(mappedBy = "poster", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(String username, String password, String nickname, String name, Email email, PasswordEncoder passwordEncoder) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.nickname = nickname;
        this.name = name;
        this.email = email;

        email.setMember(this);
    }

    public void checkPassword(String targetPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.match(targetPassword, this.password)) {
            throw new CustomApiException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    public void edit(String name, String password, PasswordEncoder passwordEncoder) {
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.password = Optional.ofNullable(
                        StringUtils.hasText(password)
                                ? passwordEncoder.encode(password)
                                : null)
                .orElse(this.password);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }
}
