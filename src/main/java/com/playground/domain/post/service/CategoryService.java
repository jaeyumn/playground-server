package com.playground.domain.post.service;

import com.playground.domain.post.domain.Category;
import com.playground.domain.post.domain.CategoryRepository;
import com.playground.domain.post.dto.request.CreateCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CreateCategoryRequestDto requestDto) {
        Category category = new Category(requestDto.getTitle());
        categoryRepository.save(category);
    }
}
