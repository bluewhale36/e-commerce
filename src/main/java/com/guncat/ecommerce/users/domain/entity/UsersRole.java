package com.guncat.ecommerce.users.domain.entity;

import com.guncat.ecommerce.users.enums.Role;
import jakarta.persistence.*;
import lombok.*;

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
