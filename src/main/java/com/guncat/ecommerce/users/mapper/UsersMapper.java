package com.guncat.ecommerce.users.mapper;

import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.users.domain.entity.UsersRole;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.enums.Role;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mapping(source = "is_enabled", target = "isEnabled")
    @Mapping(source = "is_locked", target = "isLocked")
    @Mapping(source = "usersRole", target = "roleList")
    UsersDTO toDTO(Users users);

    default List<Role> mapUsersRoleToRoleList(List<UsersRole> usersRole) {
        return usersRole.stream().map(UsersRole::getRole).toList();
    }

    List<UsersDTO> toDTOs(List<Users> users);


}
