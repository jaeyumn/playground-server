package com.playground.domain.post.domain;

import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public Category(String title) {
        this.title = title;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public Post getPost(Long postId) {
        return this.posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new CustomApiException(ErrorCode.POST_NOT_FOUND));
    }
}
