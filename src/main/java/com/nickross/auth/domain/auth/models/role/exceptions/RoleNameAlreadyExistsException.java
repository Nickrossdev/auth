package com.nickross.auth.domain.auth.models.role.exceptions;

public class RoleNameAlreadyExistsException extends RuntimeException {
    public RoleNameAlreadyExistsException(String message) {
        super(message);
    }
}
