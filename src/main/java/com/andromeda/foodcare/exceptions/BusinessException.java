package com.andromeda.foodcare.exceptions;

import com.andromeda.foodcare.exceptions.core.BaseException;

public class BusinessException extends BaseException {

    public BusinessException(BaseException exception) {
        super(exception);
    }
}
