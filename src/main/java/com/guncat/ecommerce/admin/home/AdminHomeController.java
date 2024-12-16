package com.guncat.ecommerce.admin.home;

import com.guncat.ecommerce.security.annotation.AuthenticationPrincipalUserCode;
import com.guncat.ecommerce.security.domain.UserDetails_Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminHomeController {

    @GetMapping(value = {""})
    public String home(@AuthenticationPrincipalUserCode String userCode, Model model) {
        System.out.println("annotation : " + userCode);
        model.addAttribute("userCode", userCode);
        return "admin/home/home";
    }
}
