package com.andromeda.foodcare.exceptions.core;

/**
 * Class responsible for creating not found exception.
 */
public class NotFoundException extends BaseException {

    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException notFound(String message) {
        return new NotFoundException(message);
    }
}
