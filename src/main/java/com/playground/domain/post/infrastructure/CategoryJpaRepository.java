package com.playground.domain.post.infrastructure;

import com.playground.domain.post.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
