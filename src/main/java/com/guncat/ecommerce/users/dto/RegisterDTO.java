package com.guncat.ecommerce.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class RegisterDTO {

    private final String userId;
    private final String password;
    private final String name;
    private final String tel;
    private final String email;
    private final String verificationCode;

}
