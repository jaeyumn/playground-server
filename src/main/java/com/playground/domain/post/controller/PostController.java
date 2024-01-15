package com.playground.domain.post.controller;

import com.playground.domain.post.dto.request.CreatePostRequestDto;
import com.playground.domain.post.dto.response.FindPostResponseDto;
import com.playground.domain.post.dto.response.FindPostsResponseDto;
import com.playground.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/categories/posts")
    public void createPost(@RequestBody CreatePostRequestDto requestDto) {
        postService.createPost(requestDto);
    }

    @GetMapping("/categories/{categoryId}/posts/{postId}")
    public FindPostResponseDto findPost(@PathVariable Long categoryId, @PathVariable Long postId) {
        return postService.findPost(categoryId, postId);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public FindPostsResponseDto findPosts(@PathVariable Long categoryId) {
        return postService.findPosts(categoryId);
    }
}
