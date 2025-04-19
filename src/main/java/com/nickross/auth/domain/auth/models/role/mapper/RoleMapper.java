package com.nickross.auth.domain.auth.models.role.mapper;

import com.nickross.auth.domain.auth.models.role.dto.RoleRegisterRequest;
import com.nickross.auth.domain.auth.models.role.dto.RoleResponse;
import com.nickross.auth.domain.auth.models.role.dto.RoleUpdateRequest;
import com.nickross.auth.domain.auth.models.role.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper {
    public Role convertToEntity(RoleRegisterRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setActive(request.getActive() != null ? request.getActive() : true);
        return role;
    }
    public Role convertToEntity(RoleUpdateRequest request, Role role) {
        role.setName(request.getName() != null ? request.getName() : role.getName());
        role.setDescription(request.getDescription() != null ? request.getDescription() : role.getDescription());
        role.setActive(request.getActive() != null ? request.getActive() : role.isActive());
        return role;
    }

    public RoleResponse convertToResponse(Role role) {
        return new RoleResponse(role.getId(),role.getName(),role.getDescription(),role.isActive());
    }

    public List<RoleResponse> convertToResponse(List<Role> roleList) {
        return roleList.stream()
                .map(role -> {
                    return new RoleResponse(role.getId(), role.getName(), role.getDescription(), role.isActive());
                })
                .toList();
    }

}
