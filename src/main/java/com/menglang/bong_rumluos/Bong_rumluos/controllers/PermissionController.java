package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Permission;
import com.menglang.bong_rumluos.Bong_rumluos.services.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;

    @PostMapping
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionRequest dto) {
        return ResponseEntity.ok(permissionService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> view(@PathVariable("id") Long id) {
        return ResponseEntity.ok(permissionService.view(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> update(@PathVariable("id") Long id, @RequestBody PermissionRequest dto) {
        return ResponseEntity.ok(permissionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PermissionResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(permissionService.delete(id));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy,
            @RequestParam(name = "query",defaultValue = "") String query
    ) {
        Page<Permission> permissionPage = permissionService.getAll(page, size, orderBy, sortBy, query);
        List<PermissionResponse> permissionList = permissionPage.getContent().stream().map(permissionMapper::toResponse).toList();
        return BaseResponse.success(permissionList, permissionPage, "successful");
    }

}
