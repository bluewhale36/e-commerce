package com.guncat.ecommerce.security.domain;

import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.users.domain.entity.UsersRole;
import com.guncat.ecommerce.users.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Spring Security 의 {@link UserDetails} 구현체.<br/>
 * 로그인 및 Session 에서 접근해야 하는 정보를 담은 객체이다.
 */
@RequiredArgsConstructor
public class UserDetails_Impl implements UserDetails {

    private final Users users;

    /**
     * 사용자의 권한을 반환하는 method.<br/>
     * Spring Security 에서는 권한을 {@link GrantedAuthority} 의 구현체로 판단하므로,
     * 직접 정의한 권한을 해당 인터페이스의 구현체로 변환하는 작업이 필요하다.
     *
     * @return {@code Collection<? extends GrantedAuthority>}
     * @see Role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return users.getUsersRole().stream()
                .map(UsersRole::getRole)
                .map(Role::getGrantedAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !users.getIs_locked().toBoolean();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return users.getIs_enabled().toBoolean();
    }
}
