package com.andromeda.foodcare.exceptions.core;

import lombok.Getter;

/**
 * Base class for custom exceptions.
 */
@Getter
public class BaseException extends RuntimeException {

    protected BaseException(String message) {
        super(message);
    }

    protected BaseException(Throwable cause) {
        super(cause);
    }
}
