package com.playground.domain.member.controller;

import com.playground.domain.member.dto.request.EditMemberRequestDto;
import com.playground.domain.member.dto.request.SignUpRequestDto;
import com.playground.domain.member.dto.response.FindMemberResponseDto;
import com.playground.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/sign-up")
    public void signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        memberService.signUp(requestDto);
    }

    @PatchMapping("/members/me/edit")
    public void editMember(@RequestBody EditMemberRequestDto requestDto) {
        memberService.editMember(requestDto);
    }

    @GetMapping("/members/me")
    public FindMemberResponseDto findMember() {
        return memberService.findMember();
    }

    @DeleteMapping("/members/me")
    public void withdrawMember() {
        memberService.withdrawMember();
    }
}
