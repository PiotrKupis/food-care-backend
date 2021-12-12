package com.andromeda.foodcare.exceptions;

import com.andromeda.foodcare.exceptions.core.BaseException;
import com.andromeda.foodcare.exceptions.core.ConflictException;
import com.andromeda.foodcare.exceptions.core.NotFoundException;

public class BusinessException extends BaseException {

    public BusinessException(BaseException exception) {
        super(exception);
    }

    public static BusinessException businessNotFound() {
        return new BusinessException(
            NotFoundException.notFound("Business not found"));
    }

    public static BusinessException userAlreadyHasBusiness() {
        return new BusinessException(ConflictException.conflict("User already has business"));
    }
}
