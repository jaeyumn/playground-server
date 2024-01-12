package com.playground.domain.member.domain;

import java.util.Optional;

public interface MemberRepository {

    void saveMember(Member member);

    void createEmail(Email email);

    boolean isExistsMember(String username);

    boolean isExistsEmail(String email);

    Optional<Member> findById(String memberId);

    Optional<Member> findByUsername(String username);

    void deleteMember(String memberId);
}
