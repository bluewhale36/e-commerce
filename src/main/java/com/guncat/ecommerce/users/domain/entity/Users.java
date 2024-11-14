package com.guncat.ecommerce.users.domain.entity;

import com.guncat.ecommerce.common.converter.IsEnabledConverter;
import com.guncat.ecommerce.common.converter.IsLockedConverter;
import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 사용자 정보 저장의 Entity Class.<br/>
 * users Table 과 데이터를 교환한다.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Users [userCode=").append(userCode).append(", ");
        sb.append("userId=").append(userId).append(", ");
        sb.append("password=").append("[PROTECTED]").append(", ");
        sb.append("name=").append(name).append(", ");
        sb.append("tel=").append(tel).append(", ");
        sb.append("email=").append(email).append(", ");
        sb.append("is_enabled=").append(is_enabled).append(", ");
        sb.append("usersRole=").append(usersRole);
        sb.append("]");
        return sb.toString();
    }

}
