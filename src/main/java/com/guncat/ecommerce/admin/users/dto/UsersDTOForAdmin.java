package com.guncat.ecommerce.admin.users.dto;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.enums.Role;
import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
@ToString
public class UsersDTOForAdmin {

    private final UsersDTO usersDTO;
    private final List<Role> roleList;
    private final List<IsEnabled> isEnabledList;
    private final List<IsLocked> isLockedList;

}
