package com.playground.domain.post.service;

import com.playground.domain.post.dto.request.CreatePostRequestDto;

public interface PostService {

    void createPost(CreatePostRequestDto requestDto);
}
