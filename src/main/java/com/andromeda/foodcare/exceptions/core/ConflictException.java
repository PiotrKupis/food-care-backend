package com.andromeda.foodcare.exceptions.core;

/**
 * Class responsible for creating conflict exception.
 */
public class ConflictException extends BaseException {

    private ConflictException(String message) {
        super(message);
    }

    public static ConflictException conflict(String message) {
        return new ConflictException(message);
    }
}
