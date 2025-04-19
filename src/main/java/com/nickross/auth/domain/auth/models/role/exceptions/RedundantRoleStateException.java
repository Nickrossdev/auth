package com.nickross.auth.domain.auth.models.role.exceptions;

public class RedundantRoleStateException extends RuntimeException {
    public RedundantRoleStateException(String message) {
        super(message);
    }
}
