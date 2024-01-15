package com.playground.domain.post.service;

import com.playground.domain.post.domain.Category;
import com.playground.domain.post.domain.CategoryRepository;
import com.playground.domain.post.dto.request.CreateCategoryRequestDto;
import com.playground.domain.post.dto.response.FindCategoriesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public FindCategoriesResponseDto findCategories() {
        List<Category> allCategories = categoryRepository.findAllCategories();

        return new FindCategoriesResponseDto(
                allCategories.stream()
                .map(Category::getTitle)
                .toList()
        );
    }
}
