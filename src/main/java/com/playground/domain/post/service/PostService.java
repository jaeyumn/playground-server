package com.playground.domain.post.service;

import com.playground.domain.post.dto.request.CreatePostRequestDto;
import com.playground.domain.post.dto.response.FindPostResponseDto;
import com.playground.domain.post.dto.response.FindPostsResponseDto;

public interface PostService {

    void createPost(CreatePostRequestDto requestDto);

    FindPostResponseDto findPost(Long categoryId, Long postId);

    FindPostsResponseDto findPosts(Long categoryId);
}
