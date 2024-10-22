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

    private final IF_HomeService homeService;
    private final IF_UsersService usersService;


    @GetMapping("/")
    public String home(Model model) {
        String userId = usersService.getCurrentUserId();
        model.addAttribute("userId", userId);
        return "home/home";
    }

}
