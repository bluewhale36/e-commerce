package com.guncat.ecommerce.users.enums;

import com.guncat.ecommerce.security.util.GrantedAuthority_Impl;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/**
 * 사용자 권한을 정의한 Enum Class.
 */
@Getter
public enum Role {
    CEO("ROLE_CEO", "사장"),
    STAFF("ROLE_STAFF", "직원"),
    REGULAR("ROLE_REGULAR", "일반 회원");

    private final String roleName;
    private final String description;

    Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    /**
     * 사용자 권한을 {@link GrantedAuthority} 구현체로 반환하는 method.<br/>
     * Spring Security 에서 개발자 정의의 권한을 인식하도록 하기 위함.
     *
     * @return {@link GrantedAuthority_Impl}
     */
    public GrantedAuthority getGrantedAuthority() {
        return new GrantedAuthority_Impl(this);
    }
}
