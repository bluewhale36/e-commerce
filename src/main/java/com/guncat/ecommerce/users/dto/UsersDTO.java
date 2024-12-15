package com.guncat.ecommerce.users.dto;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import com.guncat.ecommerce.users.enums.Role;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UsersDTO {

    private String userCode;
    private String userId;
    private String password;
    private String name;
    private String tel;
    private String email;

    private IsEnabled isEnabled;
    private IsLocked isLocked;

    private List<Role> roleList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsersDTO [userCode=").append(userCode).append(", ");
        sb.append("userId=").append(userId).append(", ");
        sb.append("password=").append("[PROTECTED]").append(", ");
        sb.append("name=").append(name).append(", ");
        sb.append("tel=").append(tel).append(", ");
        sb.append("email=").append(email).append(", ");
        sb.append("isEnabled=").append(isEnabled).append(", ");
        sb.append("isLocked=").append(isLocked).append(", ");
        sb.append("usersRole=").append(roleList);
        sb.append("]");
        return sb.toString();
    }
}
