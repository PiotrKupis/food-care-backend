package com.andromeda.foodcare.exceptions.core;

/**
 * Class responsible for creating critical exception.
 */
public class CriticalException extends BaseException {

    private CriticalException(String message) {
        super(message);
    }

    public static CriticalException critical(String message) {
        return new CriticalException(message);
    }
}
