package com.guncat.ecommerce.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guncat.ecommerce.security.enums.CustomAuthenticationExceptions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 실패 시 실행되는 Handler.<br/>
 * Provider 객체가 {@link AuthenticationException} 을 throw 할 경우 발생.
 * {@link AuthenticationFailureHandler} 의 구현체.
 *
 * @see AuthenticationFailureHandler
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 로그인 실패 시 실행되는 Handler.<br/>
     * Provider 객체가 throw 한 Exception 에 대한 추가 로직 구현.
     * @param e Provider 객체가 발생시킨 예외 객체.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest req,
                                        HttpServletResponse res,
                                        AuthenticationException e) throws IOException {
        System.out.println("CustomAuthenticationFailureHandler#onAuthenticationFailure");

        e.printStackTrace();

        writePrintErrorResponse(res, e);
    }

    private void writePrintErrorResponse(HttpServletResponse res, AuthenticationException e) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            Map<String, Object> responseMap = new HashMap<>();

            CustomAuthenticationExceptions customE = getEnumByException(e);

            responseMap.put("status", customE.getStatusCode());
            responseMap.put("message", customE.getMessage());

            res.setContentType("application/json");
            res.setStatus(customE.getStatusCode());
            res.setCharacterEncoding(StandardCharsets.UTF_8.name());
            res.getWriter().write(objMapper.writeValueAsString(responseMap));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    /**
     * 발생한 예외에 대해 사전 정의한 메세지와 상태 코드를 저장하는 enum 객체 반환.
     * @param e 발생한 {@link AuthenticationException}
     * @return {@link CustomAuthenticationExceptions} 객체.
     */
    private CustomAuthenticationExceptions getEnumByException(AuthenticationException e) {
        System.out.println("CustomAuthenticationFailureHandler#getEnumByException");
        return CustomAuthenticationExceptions.getEnumByException(e);
    }
}