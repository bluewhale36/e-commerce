package com.guncat.ecommerce.security.domain;

import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.users.domain.entity.UsersRole;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Spring Security 의 {@link UserDetails} 구현체.<br/>
 * 로그인 및 Session 에서 접근해야 하는 정보를 담은 객체이다.
 */
@Getter
@AllArgsConstructor
public class UserDetails_Impl implements UserDetails, CredentialsContainer {

    private UsersDTO usersDTO;

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
        return usersDTO.getRoleList().stream()
                .map(Role::getGrantedAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usersDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return usersDTO.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !usersDTO.getIsLocked().toBoolean();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usersDTO.getIsEnabled().toBoolean();
    }

    /**
     * AuthenticationManager 에서 인증 완료 후 비밀번호 정보를 지울 때 호출되는 method.
     *
     * @see org.springframework.security.authentication.ProviderManager#authenticate(Authentication)
     * @see CredentialsContainer
     */
    @Override
    public void eraseCredentials() {
        usersDTO = UsersDTO.builder()
                .userCode(usersDTO.getUserCode())
                .userId(usersDTO.getUserId())
                .password(null)
                .name(usersDTO.getName())
                .tel(usersDTO.getTel())
                .email(usersDTO.getEmail())
                .isEnabled(usersDTO.getIsEnabled())
                .isLocked(usersDTO.getIsLocked())
                .roleList(usersDTO.getRoleList())
                .build();
    }

    @Override
    public String toString() {
        return usersDTO.toString();
    }
}
