package com.guncat.ecommerce.security.repository;

import com.guncat.ecommerce.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<Users, String> {

    Optional<Users> findUserByUserId(String userId);

}
