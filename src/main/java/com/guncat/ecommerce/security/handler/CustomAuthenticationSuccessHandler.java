package com.guncat.ecommerce.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 로그인 성공에 대한 Handler.<br/>
 * {@link org.springframework.security.authentication.AuthenticationProvider#supports(Class)} 에서 {@code true} 반환 시 성공으로 간주.
 * {@link AuthenticationSuccessHandler} 의 구현체.
 *
 * @see AuthenticationSuccessHandler
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 로그인 성공 시 실행되는 method.<br/>
     * 1. 페이지 접근 거부에 의해 로그인 시도 및 성공 시, 거부되었던 페이지로 redirect.<br/>
     * 2. 접속해있던 페이지에서 로그인 페이지로 이동 및 성공 시, 기존에 접속했던 페이지로 redirect.<br/>
     * 3. 이 외의 경우에서는 {@code /} 로 redirect.
     * @param auth Provider 에서 반환된 {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken} 객체.
     * @see com.guncat.ecommerce.users.controller.UsersController#login(HttpServletRequest)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse res, Authentication auth) throws IOException {

        System.out.println("CustomAuthenticationSuccessHandler#onAuthenticationSuccess");

        // 기본 리다이렉트 경로.
        String redirectUri = "/";

        /*
            1번 경우.
            접속이 거부되었던 페이지 경로를 저장하는 객체.
         */
        RequestCache reqCache = new HttpSessionRequestCache();
        SavedRequest savedReq = reqCache.getRequest(req, res);

        /*
            2번 경우.
            기존에 접속해있던 페이지 경로를 저장하는 session.
         */
        String prevPage = (String) req.getSession().getAttribute("prevPage");
        // 기존에 이동한 경로에 대한 세션이 있을 경우
        if (prevPage != null) {
            // 해당 정보 지워준다. (memory 관리)
            req.getSession().removeAttribute("prevPage");
        }
        System.out.println(prevPage);
        /*
            경우에 따른 리다이렉트 경로 분기.
         */
        if (savedReq != null) { // 1번 경우.
            redirectUri = savedReq.getRedirectUrl();
            // 해당 정보 지워준다. (memory 관리)
            reqCache.removeRequest(req, res);
        } else if (prevPage != null && !prevPage.isEmpty()) { // 2번 경우.
            redirectUri = prevPage;
        }

        // 리다이렉트 작업 수행.
        res.sendRedirect(redirectUri);
    }

}
