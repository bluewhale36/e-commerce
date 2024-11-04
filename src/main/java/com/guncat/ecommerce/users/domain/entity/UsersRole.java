package com.guncat.ecommerce.users.domain.entity;

import com.guncat.ecommerce.users.enums.Role;
import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자 권한 정보 저장의 Entity Class.<br/>
 * users_role Table 과 데이터를 교환한다.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "users_role")
public class UsersRole {

    @Id
    private String roleCode;

    @Column(name = "role_user_code")
    private String userCode;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
}
