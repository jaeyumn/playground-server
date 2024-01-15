package com.playground.domain.post.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    void save(Category category);

    Optional<Category> findCategoryById(Long id);

    List<Category> findAllCategories();
}
