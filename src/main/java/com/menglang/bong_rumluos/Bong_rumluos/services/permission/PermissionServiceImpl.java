package com.menglang.bong_rumluos.Bong_rumluos.services.permission;

import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Permission;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.PermissionRepository;
import com.menglang.bong_rumluos.Bong_rumluos.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse create(PermissionRequest dto) {
        Permission permission = permissionMapper.toPermission(dto);
        try {
            return permissionMapper.toResponse(permissionRepository.save(permission));

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public PermissionResponse view(Long id) {
        Permission permission=this.findById(id);
        return permissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse delete(Long id) {
        Permission permission=this.findById(id);
        try {
            permissionRepository.delete(permission);
            return permissionMapper.toResponse(permission);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public PermissionResponse update(Long id, PermissionRequest dto) {
        Permission permission=findById(id);
        permissionMapper.updatePermission(dto,permission);
        try {
            Permission permissionUpdate=permissionRepository.save(permission);
            return permissionMapper.toResponse(permissionUpdate);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public Page<Permission> getAll(int page, int size, String orderBy, String sortBy, String query) {
        Pageable pageable= PageableResponse.handlePageable(page,size,orderBy,sortBy);
        return permissionRepository.getAllPermission(query,pageable);

    }

    private Permission findById(Long id){
        return permissionRepository.findById(id).orElseThrow(()->new NotFoundException("Permission Not Found."));
    }
}
