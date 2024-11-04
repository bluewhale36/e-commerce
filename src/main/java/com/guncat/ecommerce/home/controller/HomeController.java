package com.guncat.ecommerce.home.controller;

import com.guncat.ecommerce.home.service.IF_HomeService;
import com.guncat.ecommerce.users.service.IF_UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {



    @GetMapping("/")
    public String home() {
        return "home/home";
    }

    @GetMapping("/other")
    public String other() {
        return "home/otherHome";
    }

}
