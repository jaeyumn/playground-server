package com.playground.domain.member.domain;

public interface MemberRepository {

    void save(Member member);

    boolean isExistsMember(String username);
}
