package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, String> {
}
