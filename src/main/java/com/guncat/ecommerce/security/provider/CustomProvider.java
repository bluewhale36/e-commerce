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

@Component
@RequiredArgsConstructor
public class CustomProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param auth the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
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
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * <code>Authentication</code> object. It simply indicates it can support closer
     * evaluation of it. An <code>AuthenticationProvider</code> can still return
     * <code>null</code> from the {@link #authenticate(Authentication)} method to indicate
     * another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("provider#supports");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
