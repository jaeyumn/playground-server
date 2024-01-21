package com.playground.domain.member.controller;

import com.playground.domain.member.dto.request.SendVerificationEmailRequestDto;
import com.playground.domain.member.dto.request.VerificationEmailCodeRequestDto;
import com.playground.domain.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/emails/verification/send")
    public void sendVerificationEmail(@RequestBody SendVerificationEmailRequestDto requestDto) {
        emailService.sendAuthCode(requestDto.email());
    }

    @PostMapping("/emails/verification/check")
    public void verificationEmail(@RequestBody VerificationEmailCodeRequestDto requestDto) {
        emailService.verifiedAuthCode(requestDto.email(), requestDto.code());
    }
}
