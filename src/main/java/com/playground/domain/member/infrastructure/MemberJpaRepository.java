package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, String> {

    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);
}
