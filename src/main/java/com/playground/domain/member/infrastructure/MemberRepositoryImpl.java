package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }
}
