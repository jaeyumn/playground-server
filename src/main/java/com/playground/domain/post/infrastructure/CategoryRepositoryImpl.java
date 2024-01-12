package com.playground.domain.post.infrastructure;

import com.playground.domain.post.domain.Category;
import com.playground.domain.post.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public void save(Category category) {
        categoryJpaRepository.save(category);
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryJpaRepository.findById(id);
    }
}
