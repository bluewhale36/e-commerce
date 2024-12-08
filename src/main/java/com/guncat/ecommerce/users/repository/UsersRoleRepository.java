package com.guncat.ecommerce.users.repository;

import com.guncat.ecommerce.users.domain.entity.UsersRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 사용자 권한 관련 데이터 반환을 위해 사용되는 Repository
 */
@Repository
public interface UsersRoleRepository extends JpaRepository<UsersRole, String> {

    @Query("delete from UsersRole ur where ur.userCode = :userCode")
    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteAllByUserCode(String userCode);

}
