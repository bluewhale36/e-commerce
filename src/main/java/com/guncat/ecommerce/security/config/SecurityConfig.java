package com.guncat.ecommerce.security.config;

import com.guncat.ecommerce.users.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Spring Security 의 설정을 정의하는 Configuration Class.<br/>
 * Spring Security 5.X 버전부터 {@link SecurityFilterChain} 방식의 설정 권장.
 *
 * @see SecurityFilterChain
 * @see AuthenticationManager
 * @see com.guncat.ecommerce.security.handler.CustomAuthenticationSuccessHandler
 * @see com.guncat.ecommerce.security.handler.CustomAuthenticationFailureHandler
 * @see com.guncat.ecommerce.security.provider.CustomProvider
 * @see com.guncat.ecommerce.security.service.UserDetailsService_Impl
 * @see com.guncat.ecommerce.security.domain.UserDetails_Impl
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationProvider authenticationProvider;

    private final PersistentTokenRepository persistentTokenRepository;

    /**
     * Spring Security 의 {@link SecurityFilterChain} 구현체 Build.<br/>
     * 기본적으로 적용할 보안 설정, 경로 별 인증 및 인가에 따른 접근 허가 여부, 로그인 방식 및 과정, 자동 로그인 속성 정의.<br/>
     * Spring Security 5.X 버전부터 Method Chaining 방식을 권장.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests( req -> req
                        .requestMatchers("/", "/other").permitAll()
                        .requestMatchers("/users/login", "/users/join", "/users/dup/**").permitAll()
                        .requestMatchers("/email/**").permitAll()
                        .requestMatchers("/css/**", "/scss/**","/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyRole(Role.CEO.name(), Role.STAFF.name())
                        .anyRequest().authenticated())
                .formLogin( form -> form
                        .loginProcessingUrl("/users/login").permitAll()
                        .loginPage("/users/login").permitAll()
                        .usernameParameter("userId").passwordParameter("password")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler))
                .rememberMe( remember -> remember
                        .rememberMeParameter("rememberMe")
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .alwaysRemember(false)
                        .userDetailsService(userDetailsService)
                        .tokenRepository(persistentTokenRepository));

        return http.build();
    }

    /**
     * Custom 한 Provider 의 사용 여부 설정.
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        ((ProviderManager) authenticationManager).getProviders().add(authenticationProvider);
        return authenticationManager;
    }

}
