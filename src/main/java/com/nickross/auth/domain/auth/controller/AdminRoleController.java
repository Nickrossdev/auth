package com.nickross.auth.domain.auth.controller;

import com.nickross.auth.domain.auth.models.role.RoleService;
import com.nickross.auth.domain.auth.models.role.dto.RoleRegisterRequest;
import com.nickross.auth.domain.auth.models.role.dto.RoleResponse;
import com.nickross.auth.domain.auth.models.role.dto.RoleUpdateRequest;
import com.nickross.auth.dto.ApiResponse;
import com.nickross.auth.util.ApiResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminRoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>,Void>> getAllRoles() {
        List<RoleResponse> data = roleService.getAllRoles();
        return ResponseEntity.ok(ApiResponseUtil.success("Roles obtenidos",data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse,Void>> getRoleById(@PathVariable Integer id) {
        RoleResponse data = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponseUtil.success(data));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse,Void>> createRole(@RequestBody @Valid RoleRegisterRequest request) {
        RoleResponse data = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseUtil.success(data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Integer id, @RequestBody RoleUpdateRequest request) {
        RoleResponse data = roleService.updateRole(id, request);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ApiResponse<Void,Void>> updateRoleActiveStatus(@PathVariable Integer id, @RequestBody @NotNull(message = "el estado es obligatorio") boolean active) {
        roleService.updateRoleActiveStatus(id, active);
        return ResponseEntity.ok().body(ApiResponseUtil.success("Rol desactivado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void,Void>> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().body(ApiResponseUtil.success("Rol Eliminado"));
    }
}
