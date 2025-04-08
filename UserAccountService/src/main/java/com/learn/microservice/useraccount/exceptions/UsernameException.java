package com.learn.microservice.useraccount.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UsernameException extends AuthenticationException {

    private static final String MESSAGE = "The username already exists";

    public UsernameException() {
        super(MESSAGE);
    }

    public UsernameException(String message) {
        super(message);
    }
}