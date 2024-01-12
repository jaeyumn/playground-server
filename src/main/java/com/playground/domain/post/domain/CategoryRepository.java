package com.playground.domain.post.domain;

import java.util.Optional;

public interface CategoryRepository {

    void save(Category category);

    Optional<Category> findCategoryById(Long id);
}
