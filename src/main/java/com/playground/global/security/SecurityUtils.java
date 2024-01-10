package com.playground.global.security;

import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static MemberPrincipal getMemberPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof MemberPrincipal) {
                return (MemberPrincipal) principal;
            }
        }

        throw new CustomApiException(ErrorCode.MEMBER_NOT_AUTHENTICATED);
    }
}
