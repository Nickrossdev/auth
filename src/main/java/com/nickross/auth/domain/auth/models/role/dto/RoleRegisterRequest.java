package com.nickross.auth.domain.auth.models.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRegisterRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "La descripcion es obligatoria")
    private String description;
    private Boolean active;
}
