package com.guncat.ecommerce.security.enums;


import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.AuthenticationException;

/**
 * Provider 객체에 의해 발생한 예외의 HTTP Status Code 및 반환 메세지를 사전 정의한 Enum Class.<br/>
 * {@link com.guncat.ecommerce.security.handler.CustomAuthenticationFailureHandler} 에서 에러 출력 시 사용됨.
 */
@Getter
@ToString
public enum CustomAuthenticationExceptions {

    BadCredentialsException(HttpServletResponse.SC_FORBIDDEN, "잘못된 아이디 또는 비밀번호입니다."),
    LockedException(HttpServletResponse.SC_FORBIDDEN, "계정이 잠긴 상태입니다."),
    DisabledException(HttpServletResponse.SC_FORBIDDEN, "계정이 비활성화된 상태입니다."),
    AccountExpiredException(HttpServletResponse.SC_FORBIDDEN, "계정이 만료된 상태입니다."),
    CredentialsExpiredException(HttpServletResponse.SC_FORBIDDEN, "비밀번호가 만료된 상태입니다."),
    InternalException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.");

    private final int statusCode;
    private final String message;

    CustomAuthenticationExceptions(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * 반환될 수 있는 Enum 객체 저장한 static map.
     * 추후 {@link CustomAuthenticationExceptions#getEnumByException(AuthenticationException)} 에서 사용됨.
     */
    private static final Map<String, CustomAuthenticationExceptions> EXCEPTION_MAP =
            Arrays.stream(CustomAuthenticationExceptions.values())
                    .collect(Collectors.toMap(Enum::name, Function.identity()));

    /**
     * 발생한 AuthenticationException 에 따라 지정된 Enum 객체 반환.<br/>
     * @param e {@link AuthenticationException} 객체.
     * @return 발생한 예외와 이름이 동일한 {@link CustomAuthenticationExceptions} 객체. <br/>
     * 일치하는 예외가 없을 경우 {@link CustomAuthenticationExceptions#InternalException} 객체.
     */
    public static CustomAuthenticationExceptions getEnumByException(AuthenticationException e) {
        return EXCEPTION_MAP.getOrDefault(
                e.getClass().getSimpleName(), CustomAuthenticationExceptions.InternalException
        );
    }
}
