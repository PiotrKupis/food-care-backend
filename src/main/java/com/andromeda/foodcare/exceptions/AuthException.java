package com.andromeda.foodcare.exceptions;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

    public static AuthException jwtKeystoreError() {
        return new AuthException("Problem with jwt keystore");
    }
}
