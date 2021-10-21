package com.andromeda.foodcare.exceptions.core;

/**
 * Class responsible for creating operation exception.
 */
public class OperationException extends BaseException {

    private OperationException(String message) {
        super(message);
    }

    public static OperationException operationError(String message) {
        return new OperationException(message);
    }
}
