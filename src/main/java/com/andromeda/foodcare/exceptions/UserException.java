package com.andromeda.foodcare.exceptions;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public static UserException userNotFound(String email) {
        return new UserException("User with email: " + email + " not found");
    }
}
