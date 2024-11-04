package com.guncat.ecommerce.users.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 관련 request 를 처리하는 Web Controller Class.
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    /**
     * 로그인 페이지 이동.
     * @param req 이전에 접속한 페이지 경로 식별 및 저장 위한 {@link HttpServletRequest} 객체.
     * @see com.guncat.ecommerce.security.handler.CustomAuthenticationSuccessHandler#onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication)
     */
    @GetMapping("/login")
    public String login(HttpServletRequest req) {
        System.out.println("UsersController#login");

        // 이전에 접속했었던 경로.
        String prevUri = req.getHeader("Referer");

        // 이전에 접속했었던 경로 정보가 존재하며, 그 값이 현재 경로가 아닐 경우.
        if (prevUri != null && !prevUri.isBlank() && !prevUri.contains("/login")) {
            // 세션에 해당 값 저장.
            // 추후 CustomAuthenticationSuccessHandler 에서 사용됨.
            req.getSession().setAttribute("prevPage", prevUri);
        }

        return "users/login";
    }

    /**
     * 회원 가입 페이지 이동.
     */
    @GetMapping("/join")
    public String join() {
        return "users/register";
    }

    @GetMapping("/my")
    public String my() {
        return "users/mypage";
    }

}
