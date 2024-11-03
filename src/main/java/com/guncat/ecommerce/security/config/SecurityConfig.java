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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationProvider authenticationProvider;

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
                        .userDetailsService(userDetailsService));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        ((ProviderManager) authenticationManager).getProviders().add(authenticationProvider);
        return authenticationManager;
    }

}
