package com.guncat.ecommerce.security.util;

import com.guncat.ecommerce.users.enums.Role;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthority_Impl implements GrantedAuthority {

    private final String role;

    public GrantedAuthority_Impl(Role role) {
        this.role = role.getRoleName();
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }

}
