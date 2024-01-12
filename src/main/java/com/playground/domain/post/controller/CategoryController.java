package com.playground.domain.post.controller;

import com.playground.domain.post.dto.request.CreateCategoryRequestDto;
import com.playground.domain.post.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public void createCategory(@RequestBody CreateCategoryRequestDto requestDto) {
        categoryService.createCategory(requestDto);
    }
}
