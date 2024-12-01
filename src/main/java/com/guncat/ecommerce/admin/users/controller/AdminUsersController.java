package com.guncat.ecommerce.admin.users.controller;

import com.guncat.ecommerce.users.service.IF_UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUsersController {

    private final IF_UsersService usersService;

    @GetMapping(value = {"", "/"})
    public String humanResourceManagement(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          Model model) {
        System.out.println("admin users");
        model.addAttribute("contents", usersService.getUsersByPageNum(page));
        return "admin/users/users";
    }

    @GetMapping("/details/{userCode}")
    public String userDetail(@PathVariable("userCode") String userCode, Model model) {
        System.out.println(userCode);
        model.addAttribute("user", usersService.getUserByUserCode(userCode));
        return "admin/users/user-details";
    }

}

