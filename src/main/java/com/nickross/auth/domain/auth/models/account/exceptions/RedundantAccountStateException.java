package com.nickross.auth.domain.auth.models.account.exceptions;

public class RedundantAccountStateException extends RuntimeException {
    public RedundantAccountStateException(String message) {
        super(message);
    }
}
