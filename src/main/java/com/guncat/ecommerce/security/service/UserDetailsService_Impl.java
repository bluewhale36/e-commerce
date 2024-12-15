package com.guncat.ecommerce.security.service;

import com.guncat.ecommerce.security.domain.UserDetails_Impl;
import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.security.repository.UserDetailsRepository;
import com.guncat.ecommerce.users.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security 의 {@link UserDetailsService} 구현체.
 * DB 에서 사용자 정보를 반환하는 Repository 의 method 를 호출한다.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService_Impl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    private final UsersMapper usersMapper;

    /**
     * Username 을 바탕으로 사용자 정보를 반환하는 method.
     * @param userId 필요한 사용자 정보를 식별하는 username.
     * @return {@link UserDetails} 구현체.
     * @throws UsernameNotFoundException 매개변수의 Username 을 바탕으로 데이터를 가져오지 못한 경우.
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("UserDetailsService_Impl#loadUserByUsername");
        Users user = userDetailsRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new UserDetails_Impl(usersMapper.toDTO(user));
    }
}
