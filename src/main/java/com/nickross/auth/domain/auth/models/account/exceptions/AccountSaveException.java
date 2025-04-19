package com.nickross.auth.domain.auth.models.account.exceptions;

public class AccountSaveException extends RuntimeException {
    public AccountSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
