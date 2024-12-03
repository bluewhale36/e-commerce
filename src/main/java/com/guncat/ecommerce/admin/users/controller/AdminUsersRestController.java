package com.guncat.ecommerce.admin.users.controller;

import com.guncat.ecommerce.common.dto.PagingResponseDTO;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.dto.UsersPagingRequestDTO;
import com.guncat.ecommerce.users.service.IF_UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUsersRestController {

    private final IF_UsersService usersService;

    @GetMapping("/")
    public PagingResponseDTO<List<UsersDTO>> getAdminUsersPagingData(@ModelAttribute UsersPagingRequestDTO usersPagingRequestDTO) {
        System.out.println("\n\nREST CONTROLLER\n\n");
        System.out.println(usersPagingRequestDTO);
        return usersService.getUsersByPaging(usersPagingRequestDTO);
    }
}
