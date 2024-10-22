package com.guncat.ecommerce.users.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * 로그인 시 사용되는 DTO.
 */
@Getter
@ToString
public class LoginDTO {

    private final String userId;
    private final String password;
    private final boolean rememberMe;

    public LoginDTO(String userId, String password, Boolean rememberMe) {
        this.userId = userId;
        this.password = password;
        this.rememberMe = rememberMe != null ? rememberMe : false;
    }
}
