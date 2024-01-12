package com.playground.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostRequestDto {

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String content;

    private Long categoryId;
}
