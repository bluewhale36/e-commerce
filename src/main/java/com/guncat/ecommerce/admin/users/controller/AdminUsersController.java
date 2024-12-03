package com.guncat.ecommerce.admin.users.controller;

import com.guncat.ecommerce.users.service.IF_UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUsersController {

    private final IF_UsersService usersService;

    @GetMapping("")
    public String adminUsers() {
        System.out.println("admin users");
        return "admin/users/users";
    }

    @GetMapping("/details/{userCode}")
    public String adminUsersDetails(@PathVariable("userCode") String userCode, Model model) {
        System.out.println(userCode);
        model.addAttribute("user", usersService.getUserByUserCode(userCode));
        return "admin/users/user-details";
    }

}

