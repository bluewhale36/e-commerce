package com.guncat.ecommerce.security.service;

import com.guncat.ecommerce.security.domain.UserDetails_Impl;
import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.security.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService_Impl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("UserDetailsService_Impl#loadUserByUsername");
        Users user = userDetailsRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new UserDetails_Impl(user);
    }
}
