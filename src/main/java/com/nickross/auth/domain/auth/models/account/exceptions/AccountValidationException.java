package com.nickross.auth.domain.auth.models.account.exceptions;

public class AccountValidationException extends RuntimeException {
    public AccountValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
