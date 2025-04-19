package com.nickross.auth.domain.auth.models.role;

import com.nickross.auth.domain.auth.models.account_role.AccountRoleService;
import com.nickross.auth.domain.auth.models.role.dto.RoleRegisterRequest;
import com.nickross.auth.domain.auth.models.role.dto.RoleResponse;
import com.nickross.auth.domain.auth.models.role.dto.RoleUpdateRequest;
import com.nickross.auth.domain.auth.models.role.exceptions.RedundantRoleStateException;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleInUseException;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleNameAlreadyExistsException;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleNotFoundException;
import com.nickross.auth.domain.auth.models.role.mapper.RoleMapper;
import com.nickross.auth.domain.auth.models.role.repository.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AccountRoleService accountRoleService;

    public Role findEntityById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("El rol con el id " + id + " no fue encontrado"));
    }
    public Role findEntityByName(String name) {
        return roleRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> new RoleNotFoundException("El rol: " + name + " no fue encontrado"));
    }

    public List<Role> findAllEntityById(List<Integer> rolesId) {
        return roleRepository.findAllById(rolesId);
    }

    public List<RoleResponse> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleMapper.convertToResponse(roleList);
    }

    public List<RoleResponse> getAllRolesById(List<Integer> list) {
        List<Role> roleList = roleRepository.findAll();
        return roleMapper.convertToResponse(roleList);
    }

    public RoleResponse getRoleById(Integer id) {
        Role role = findEntityById(id);
        return roleMapper.convertToResponse(role);
    }

    public RoleResponse getRoleByName(String name) {
        Role role = findEntityByName(name);
        return roleMapper.convertToResponse(role);
    }


    public boolean roleNameExist(String name) {
        return roleRepository.existsByName(name);
    }

    public void validateUniqueRoleName(String name) {
        if (roleNameExist(name)) {
            throw new RoleNameAlreadyExistsException("El nombre: " + name + " ya existe");
        }
    }

    public RoleResponse createRole(@Valid RoleRegisterRequest request) {
        Role role = roleMapper.convertToEntity(request);
        validateUniqueRoleName(role.getName());
        role = roleRepository.save(role);
        return roleMapper.convertToResponse(role);
    }

    public RoleResponse updateRole(Integer id, @Valid RoleUpdateRequest request) {
        Role role = findEntityById(id);
        role = roleMapper.convertToEntity(request, role);

        // Compara el nombre nuevo con el actual para ver si realmente cambió
        if (!role.getName().equals(request.getName())) {
            validateUniqueRoleName(role.getName());
        }

        role = roleRepository.save(role);
        return roleMapper.convertToResponse(role);
    }

    public void updateRoleActiveStatus(Integer id, boolean active) {
        Role role = findEntityById(id);
        if (role.isActive() == active) {
            throw new RedundantRoleStateException("El rol " + role.getName().toLowerCase() + "ya está marcado como: " + (active ? "activo" : "inactivo"));
        }
        role.setActive(active);
        roleRepository.save(role);
    }

    public void deleteRole(Integer id) {
        boolean exists = accountRoleService.existsAccountRoleByRoleId(id);
        if (exists) {
            throw new RoleInUseException("El rol esta asignado a uno o mas cuentas");
        }
        Role role = findEntityById(id);
        roleRepository.delete(role);
    }
}
