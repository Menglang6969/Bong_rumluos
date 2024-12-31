package com.menglang.bong_rumluos.Bong_rumluos.services.role;

import com.menglang.bong_rumluos.Bong_rumluos.dto.role.RoleMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.role.RoleRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.role.RoleResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Role;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.PermissionRepository;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.RoleRepository;
import com.menglang.bong_rumluos.Bong_rumluos.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponse create(RoleRequest dto) {
        Role role = roleMapper.toRole(dto, permissionRepository);
        try {
            Role roleSaved = roleRepository.save(role);
            return roleMapper.toResponse(roleSaved);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public RoleResponse view(Long id) {
        return roleMapper.toResponse(findById(id));
    }

    @Override
    public RoleResponse delete(Long id) {
        Role role=findById(id);
        try{
            roleRepository.delete(role);
            return roleMapper.toResponse(role);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public RoleResponse update(Long id, RoleRequest dto) {
        Role role=this.findById(id);
        roleMapper.updateToRole(dto,role,permissionRepository);
        try {
           Role roleUpdate= roleRepository.save(role);
           return roleMapper.toResponse(roleUpdate);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public Page<Role> getAll(int page, int size, String orderBy, String sortBy, String query) {
        Pageable pageable= PageableResponse.handlePageable(page,size,orderBy,sortBy);
        return roleRepository.getAllRoleByName(query,pageable);

    }

    private Role findById(Long id){
        return roleRepository.findById(id).orElseThrow(()->new NotFoundException("Role Not Found."));
    }
}
