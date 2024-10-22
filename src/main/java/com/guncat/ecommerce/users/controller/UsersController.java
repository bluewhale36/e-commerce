package com.guncat.ecommerce.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 관련 request 를 처리하는 Web Controller Class.
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/join")
    public String join() {
        return "users/register";
    }

    @GetMapping("/my")
    public String my() {
        return "users/mypage";
    }

}
