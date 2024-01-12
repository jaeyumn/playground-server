package com.playground.domain.post.controller;

import com.playground.domain.post.dto.request.CreatePostRequestDto;
import com.playground.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/categories/posts")
    public void createPost(@RequestBody CreatePostRequestDto requestDto) {
        postService.createPost(requestDto);
    }
}
