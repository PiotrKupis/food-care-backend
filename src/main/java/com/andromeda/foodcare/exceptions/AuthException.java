package com.andromeda.foodcare.exceptions;

import com.andromeda.foodcare.exceptions.core.BaseException;
import com.andromeda.foodcare.exceptions.core.CriticalException;
import com.andromeda.foodcare.exceptions.core.OperationException;

public class AuthException extends BaseException {

    public AuthException(BaseException exception) {
        super(exception);
    }

    public static AuthException jwtKeystoreError() {
        return new AuthException(CriticalException.critical("Problem with jwt keystore"));
    }

    public static AuthException badCredentials() {
        return new AuthException(OperationException.operationError("Bad credentials"));
    }

    public static AuthException emailAlreadyTaken(String email) {
        return new AuthException(OperationException.operationError("Email: " + email + " already in use!" ));
    }
}
