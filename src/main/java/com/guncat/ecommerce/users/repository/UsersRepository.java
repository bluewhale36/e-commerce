package com.guncat.ecommerce.users.repository;

import com.guncat.ecommerce.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {

    Long countByUserId(String userId);

    Long countByEmail(String email);

}
