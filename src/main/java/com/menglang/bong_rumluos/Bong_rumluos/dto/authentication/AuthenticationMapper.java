package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Role;
import com.menglang.bong_rumluos.Bong_rumluos.entities.User;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.RoleRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    User toAuthentication(RegisterDTO dto, @Context RoleRepository roleRepository);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleToString")
    UserResponse toResponse(User user);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "updatedBy", ignore = true)
//    @Mapping(target = "firstName",source = "firstname")
//    @Mapping(target = "lastName",source = "lastname")
//    void updateUserAuth(UpdateUserDTO dto, @MappingTarget User user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleToLong")
    @Mapping(target = "firstname", source = "firstName")
    @Mapping(target = "lastname", source = "lastName")
    RegisterDTO toRegister(User user);

    @Named("mapRoleToLong")
    default Long mapRoleToLong(Role role) {
        return role.getId();
    }

    @Named("mapRoleToString")
    default String mapRoleToString(Role role) {
        return role.getName();
    }

    @Named("mapRoles")
    default Role mapRoles(Long roleId, @Context RoleRepository roleRepository) {
        return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role Not Found"));
    }

}
