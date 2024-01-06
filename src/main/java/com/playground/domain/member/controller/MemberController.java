package com.playground.domain.member.controller;

import com.playground.domain.member.dto.SignUpRequestDto;
import com.playground.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/sign-up")
    public void signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        memberService.signUp(requestDto);
    }
}
