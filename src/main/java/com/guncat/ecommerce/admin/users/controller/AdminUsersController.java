package com.guncat.ecommerce.admin.users.controller;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import com.guncat.ecommerce.users.enums.Role;
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
    public String adminUsers(Model model) {
        model.addAttribute("filter_Role", Role.values());
        model.addAttribute("filter_IsEnabled", IsEnabled.values());
        model.addAttribute("filter_IsLocked", IsLocked.values());
        return "admin/users/users";
    }

    @GetMapping("/details/{userCode}")
    public String adminUsersDetails(@PathVariable("userCode") String userCode, Model model) {
        model.addAttribute("user", usersService.getUserByUserCode(userCode));
        return "admin/users/user-details";
    }

}

