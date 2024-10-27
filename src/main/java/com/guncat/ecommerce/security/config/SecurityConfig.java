package com.guncat.ecommerce.security.config;

import com.guncat.ecommerce.users.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Consumer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests( req -> req
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/users/login", "/users/join", "/users/dup/**").permitAll()
                        .requestMatchers("/email/**").permitAll()
                        .requestMatchers("/css/**", "/scss/**","/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyRole(Role.CEO.name(), Role.STAFF.name())
                        .anyRequest().authenticated())
                .formLogin( form -> form
                        .loginPage("/users/login").permitAll()
                        .loginProcessingUrl("/users/login").permitAll()
                        .usernameParameter("userId").passwordParameter("password")
                        .defaultSuccessUrl("/", true))
                .rememberMe( remember -> remember
                        .rememberMeParameter("rememberMe")
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .alwaysRemember(false)
                        .userDetailsService(userDetailsService));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
