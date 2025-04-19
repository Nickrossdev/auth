package com.nickross.auth.domain.auth.models.account.exceptions;

public class GeneralAccountException extends RuntimeException {
    public GeneralAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}