package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.role.RoleMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.role.RoleRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.role.RoleResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Role;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.RoleRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest dto) {
        return ResponseEntity.ok(roleService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> view(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.view(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable("id") Long id, @RequestBody RoleRequest dto) {
        return ResponseEntity.ok(roleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.delete(id));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy,
            @RequestParam(name = "query", defaultValue = "") String query
    ) {
        Page<Role> rolePage = roleService.getAll(page, size, orderBy, sortBy, query);
        List<RoleResponse> roleResponseList = rolePage.getContent().stream().map(roleMapper::toResponse).toList();
        return BaseResponse.success(roleResponseList, rolePage, "successful");
    }

}
