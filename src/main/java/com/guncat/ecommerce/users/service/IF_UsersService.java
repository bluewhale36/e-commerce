package com.guncat.ecommerce.users.service;

import com.guncat.ecommerce.security.domain.UserDetails_Impl;
import com.guncat.ecommerce.users.dto.RegisterDTO;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 사용자 관련 Service Layer 의 method 정의.
 */
public interface IF_UsersService {

    /**
     * 회원 가입.
     * @param registerDTO 회원 가입 시 필요한 정보.
     */
    public void register(RegisterDTO registerDTO);

    /**
     * 중복된 userId 존재 여부 확인.
     * @param userId 중복 여부를 확인할 userId 문자열.
     * @return 중복된 userId 가 존재 할 경우 {@code 1L} 이상, 없을 경우 {@code 0L}.
     */
    public Long chkDuplicatedUserId(String userId);

    /**
     * 중복된 Email 존재 여부 확인.
     * @param email 중복 여부를 확인할 Email 문자열.
     * @return 중복된 Email 이 존재 할 경우 {@code 1L} 이상, 없을 경우 {@code 0L}.
     */
    public Long chkDuplicatedEmail(String email);

    /**
     * 현재 로그인한 사용자의 userId 반환.
     * @return 로그인한 사용자가 있을 경우 {@link UserDetails_Impl#getUsername()} 값. 로그인한 사용자가 없을 경우 {@code null} 반환.
     * @see UserDetails_Impl
     * @see UserDetails
     * @deprecated {@code Thymeleaf} 의 {@link org.springframework.security.core.context.SecurityContextHolder} 객체 접근 방법 연구 중.
     */
    @Deprecated
    public String getCurrentUserId();

}
