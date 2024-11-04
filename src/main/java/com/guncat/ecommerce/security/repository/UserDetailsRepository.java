package com.guncat.ecommerce.security.repository;

import com.guncat.ecommerce.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Security 에서 사용자 정보 반환을 위해 사용되는 Repository.
 *
 * @see com.guncat.ecommerce.security.domain.UserDetails_Impl
 */
public interface UserDetailsRepository extends JpaRepository<Users, String> {

    Optional<Users> findUserByUserId(String userId);

}
