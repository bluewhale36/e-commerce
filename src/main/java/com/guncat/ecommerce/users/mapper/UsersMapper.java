package com.guncat.ecommerce.users.mapper;

import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.users.domain.entity.UsersRole;
import com.guncat.ecommerce.users.dto.UsersDTO;
import com.guncat.ecommerce.users.enums.Role;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mapping(source = "is_enabled", target = "isEnabled")
    @Mapping(source = "is_locked", target = "isLocked")
    @Mapping(source = "usersRole", target = "roleList", qualifiedByName = "mapAndSortRoleList")
    UsersDTO toDTO(Users users);

    default List<Role> mapUsersRoleToRoleList(List<UsersRole> usersRole) {
        return usersRole.stream().map(UsersRole::getRole).toList();
    }

    List<UsersDTO> toDTOs(List<Users> users);

    @Named("mapAndSortRoleList")
    default List<Role> mapAndSortRoleList(List<UsersRole> usersRoleList) {
        if (usersRoleList.isEmpty()) {
            return List.of();
        }
        return usersRoleList.stream()
                .map(UsersRole::getRole)
                .sorted((r1, r2) -> r1.ordinal() - r2.ordinal())
                .toList();
    }



}
