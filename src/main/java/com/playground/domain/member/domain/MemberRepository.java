package com.playground.domain.member.domain;

import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    boolean isExistsMember(String username);

    Optional<Member> findByUsername(String username);
}
