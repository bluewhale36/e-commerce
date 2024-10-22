package com.guncat.ecommerce.security.enums;

import jakarta.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;

@Getter
@ToString
public enum CustomAuthenticationException {

    UsernameNotFoundException(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized"),
    DisabledException(HttpServletResponse.SC_FORBIDDEN, "DISABLED"),
    LockedException(HttpServletResponse.SC_FORBIDDEN, "LOCKED"),
    BadCredentialsException(HttpServletResponse.SC_UNAUTHORIZED, "Bad Credentials"),
    OtherException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");

    private final int statusCode;
    private final String message;

    CustomAuthenticationException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static CustomAuthenticationException findException(AuthenticationException e) {
        return Arrays.stream(values())
                .filter(s -> s.name().equals(e.getClass().getSimpleName()))
                .findAny()
                .orElse(OtherException);
    }

}
