package com.playground.domain.post.dto.response;

import com.playground.domain.post.dto.PostDetail;

import java.util.List;

public record FindPostsResponseDto(List<PostDetail> posts) {
}
