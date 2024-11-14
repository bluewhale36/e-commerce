package com.guncat.ecommerce.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 커스텀 Provider 객체.<br/>
 * {@link UserDetailsService} 구현체가 반환한 사용자 정보를 기반으로 인증 작업 수행.<br/>
 * 성공 시 {@link org.springframework.security.web.authentication.AuthenticationSuccessHandler} 구현체가,
 * 실패 시 {@link org.springframework.security.web.authentication.AuthenticationFailureHandler} 구현체가 호출된다.
 */
@Component
@RequiredArgsConstructor
public class CustomProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 실제 인증 로직 구현된 method.<br/>
     * {@link UserDetailsService} 구현체가 반환한 사용자 정보를 기반으로 아이디, 비밀번호, 권한 등 확인.
     *
     * @param auth 사용자 인증 정보를 담은 {@link Authentication} 구현체.
     * @return 인증에 성공할 경우 {@link UsernamePasswordAuthenticationToken} 객체 반환.<br/>
     * @throws AuthenticationException 인증에 실패할 경우 반환되는 예외의 구현체.
     * @throws UsernameNotFoundException 매개변수가 {@code null} 값일 경우.
     * @throws BadCredentialsException 아이디 또는 비밀번호가 맞지 않을 경우.
     * @throws DisabledException 계정이 비활성화 되었을 경우.
     * @throws LockedException 계정이 잠겼을 경우.
     * @throws CredentialsExpiredException 계정의 비밀번호가 만료되었을 경우.
     * @throws AccountExpiredException 계정이 만료되었을 경우.
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        System.out.println("provider#authenticate");

        // Authentication 객체에서 사용자가 입력한 아이디, 비밀번호 반환받는다.
        String userId = auth.getName();
        String password = auth.getCredentials().toString();

        System.out.println(userId);
        System.out.println(password);

        // UserDetailsService 구현체에서 userId 로 사용자 정보 반환받는다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        // 인증 로직
        /* userDetails 객체가 반환되지 못한 경우 */
        if (userDetails == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        /* 아이디 또는 비밀번호가 맞지 않을 경우 */
        else if (!userId.equals(userDetails.getUsername())
                || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        /* 비활성화된 계정일 경우 */
        else if (!userDetails.isEnabled()) {
            throw new DisabledException("Disabled");
        }
        /* 잠김 처리된 계정일 경우 */
        else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("Locked");
        }
        /* 비밀번호가 만료되었을 경우 */
        else if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("Expired");
        }
        /* 계정이 만료되었을 경우 */
        else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("Expired");
        }

        /*
            위 경우에 모두 해당되지 않을 경우 인증에 성공한 것으로 간주,
            UsernamePasswordAuthenticationToken 반환.
         */
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
        );
    }

    /**
     * {@link AuthenticationProvider#authenticate} 에서 반환되는 {@link Authentication} 구현체가 올바른지 판단.
     * 인증 및 인가 로직 수행 후 올바른 Token 이 생성되었는지 점검한다.<br/>
     * 해당 method 에서 {@code true} 가 반환될 경우 {@link org.springframework.security.web.authentication.AuthenticationSuccessHandler} 구현체가,
     * {@code false} 가 반환될 경우 {@link org.springframework.security.web.authentication.AuthenticationFailureHandler} 구현체가 호출된다.
     *
     * @param authentication
     * @return 매개변수의 객체가 {@link UsernamePasswordAuthenticationToken} 클래스의 인스턴스일 경우 {@code true} 반환.<br/>
     * 그렇지 않을 경우 {@code false} 반환.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("provider#supports");
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
