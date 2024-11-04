package com.guncat.ecommerce.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 회원 가입 시 사용되는 데이터 정의한 DTO Class.
 */
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
