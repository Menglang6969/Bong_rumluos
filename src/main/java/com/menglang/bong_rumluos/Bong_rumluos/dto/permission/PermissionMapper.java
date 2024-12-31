package com.menglang.bong_rumluos.Bong_rumluos.dto.permission;

import com.menglang.bong_rumluos.Bong_rumluos.dto.customer.CustomerRequest;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

//    @Mapping(target = "id",ignore = true)
    Permission toPermission(PermissionRequest dto);


    PermissionResponse toResponse(Permission dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updatePermission(PermissionRequest dto, @MappingTarget Permission permission);
}
