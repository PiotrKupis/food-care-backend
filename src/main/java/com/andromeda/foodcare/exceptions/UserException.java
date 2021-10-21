package com.andromeda.foodcare.exceptions;

import com.andromeda.foodcare.exceptions.core.BaseException;
import com.andromeda.foodcare.exceptions.core.NotFoundException;

public class UserException extends BaseException {

    public UserException(BaseException exception) {
        super(exception);
    }

    public static UserException userNotFound(String email) {
        return new UserException(
            NotFoundException.notFound("User with email: " + email + " not found"));
    }
}
