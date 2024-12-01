package com.arca.auth_service.modules.user.exception;

public class PasswordConfirmationException extends RuntimeException
{
    public PasswordConfirmationException(String message) {
        super(message);
    }
}
