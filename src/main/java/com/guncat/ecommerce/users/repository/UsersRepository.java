package com.guncat.ecommerce.users.repository;

import com.guncat.ecommerce.users.domain.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * 사용자 관련 데이터 반환을 위해 사용되는 Repository.
 */
public interface UsersRepository extends JpaRepository<Users, String> {

    /**
     * 매개변수의 아이디 값과 같은 Tuple 수 반환.
     *
     * @param userId 아이디 문자열.
     * @return 매개변수의 아이디 문자열에 대한 Tuple 의 개수.
     */
    Long countByUserId(String userId);

    /**
     * 매개변수의 이메일 값과 같은 Tuple 수 반환.
     *
     * @param email 이메일 문자열.
     * @return 매개변수의 이메일 문자열에 대한 Tuple 의 개수.
     */
    Long countByEmail(String email);

}
