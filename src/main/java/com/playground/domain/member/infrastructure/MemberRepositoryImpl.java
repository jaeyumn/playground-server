package com.playground.domain.member.infrastructure;

import com.playground.domain.member.domain.Email;
import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final EmailJpaRepository emailJpaRepository;

    @Override
    public void saveMember(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public void createEmail(Email email) {
        emailJpaRepository.save(email);
    }

    @Override
    public boolean isExistsMember(String username) {
        return memberJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsEmail(String email) {
        return emailJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<Member> findById(String memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public void deleteMember(String memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}
