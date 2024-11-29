package com.guncat.ecommerce.users.dto;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import com.guncat.ecommerce.users.enums.Role;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UsersDTO {

    private String userCode;
    private String userId;
    private String name;
    private String tel;
    private String email;

    private IsEnabled isEnabled;
    private IsLocked isLocked;

    private List<Role> roleList;
}
