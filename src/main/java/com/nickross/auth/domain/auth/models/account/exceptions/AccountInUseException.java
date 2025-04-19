package com.nickross.auth.domain.auth.models.account.exceptions;

public class AccountInUseException extends RuntimeException {
    public AccountInUseException(String message) {
        super(message);
    }
}
