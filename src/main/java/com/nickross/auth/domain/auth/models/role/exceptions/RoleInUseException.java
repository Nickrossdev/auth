package com.nickross.auth.domain.auth.models.role.exceptions;

public class RoleInUseException extends RuntimeException {
    public RoleInUseException(String message) {
        super(message);
    }
}
