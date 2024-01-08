package com.playground.infra.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetails {

    private String memberId;
    private String username;
    private String name;
}
