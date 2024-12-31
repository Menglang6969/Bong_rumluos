package com.menglang.bong_rumluos.Bong_rumluos.dto.role;

import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Permission;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Role;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.PermissionRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions",source = "permissions",qualifiedByName = "mapToPermission")
    Role toRole(RoleRequest dto, @Context PermissionRepository repository);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "permissions",source = "permissions",qualifiedByName = "mapToPermission")
    void updateToRole(RoleRequest dto,@MappingTarget Role role,@Context PermissionRepository repository);

    @Mapping(target = "permissions",source = "permissions",qualifiedByName = "mapToPermissionRes")
    RoleResponse toResponse(Role role);

    PermissionResponse toPermissionRes(Permission permission);

    @Named("mapToPermission")
    default Permission mapToPermission(Long id,@Context PermissionRepository repository){
        return repository.findById(id).orElseThrow(()->new NotFoundException("Permission Not Found."));
    }

    @Named("mapToPermissionRes")
    default PermissionResponse mapToPermissionRes(Permission permission){
        return this.toPermissionRes(permission);
    }
}
