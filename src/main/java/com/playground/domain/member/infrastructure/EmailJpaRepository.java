package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailJpaRepository extends JpaRepository<Email, Long> {

    boolean existsByEmail(String email);
}
