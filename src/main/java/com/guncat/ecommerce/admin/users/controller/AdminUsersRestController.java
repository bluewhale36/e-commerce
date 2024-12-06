package com.guncat.ecommerce.admin.users.controller;

import com.guncat.ecommerce.common.dto.AbstractPagingRequest;
import com.guncat.ecommerce.common.dto.PagingResponseDTO;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.dto.UsersPagingRequestDTO;
import com.guncat.ecommerce.users.exception.UnknownFilterTypeException;
import com.guncat.ecommerce.users.service.IF_UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUsersRestController {

    private final IF_UsersService usersService;

    @GetMapping("/")
    public PagingResponseDTO<List<UsersDTO>> getAdminUsersPagingData(@ModelAttribute UsersPagingRequestDTO usersPagingRequestDTO) {
        return usersService.getUsersByPaging(usersPagingRequestDTO);
    }

    @ExceptionHandler(UnknownFilterTypeException.class)
    public ResponseEntity<String> handlerUnknownFilterTypeException(UnknownFilterTypeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
