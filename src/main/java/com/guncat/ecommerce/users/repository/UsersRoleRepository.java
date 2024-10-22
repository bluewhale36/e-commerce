package com.guncat.ecommerce.users.repository;

import com.guncat.ecommerce.users.domain.entity.UsersRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRoleRepository extends JpaRepository<UsersRole, String> {
}
