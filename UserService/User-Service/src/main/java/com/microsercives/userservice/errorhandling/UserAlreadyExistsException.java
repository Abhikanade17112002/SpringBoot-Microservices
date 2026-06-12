package com.microsercives.userservice.errorhandling;

public class UserAlreadyExistsException
        extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}