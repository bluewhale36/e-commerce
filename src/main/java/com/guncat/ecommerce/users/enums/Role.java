package com.guncat.ecommerce.users.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role {
    CEO("사장"),
    STAFF("직원"),
    REGULAR("일반 회원");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public GrantedAuthority getGrantedAuthority() {
        return new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return name();
            }
        };
    }
}
