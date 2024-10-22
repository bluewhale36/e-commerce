package com.guncat.ecommerce.users.domain.entity;

import com.guncat.ecommerce.common.converter.IsEnabledConverter;
import com.guncat.ecommerce.common.converter.IsLockedConverter;
import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Table(name = "users")
public class Users {

    @Id
    private String userCode;

    @Column(unique = true)
    private String userId;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String tel;

    @Column(unique = true)
    private String email;

    @Column
    @Convert(converter = IsEnabledConverter.class)
    private IsEnabled is_enabled;

    @Column
    @Convert(converter = IsLockedConverter.class)
    private IsLocked is_locked;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_user_code")
    private List<UsersRole> usersRole;

}
