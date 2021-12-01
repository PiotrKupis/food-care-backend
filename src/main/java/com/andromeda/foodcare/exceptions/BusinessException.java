package com.andromeda.foodcare.exceptions;

import com.andromeda.foodcare.exceptions.core.BaseException;
import com.andromeda.foodcare.exceptions.core.NotFoundException;

public class BusinessException extends BaseException {

    public BusinessException(BaseException exception) {
        super(exception);
    }

    public static BusinessException businessNotFound() {
        return new BusinessException(
            NotFoundException.notFound("Business not found"));
    }
}
