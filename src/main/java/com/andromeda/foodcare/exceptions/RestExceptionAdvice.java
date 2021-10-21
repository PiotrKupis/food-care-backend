package com.andromeda.foodcare.exceptions;

import com.andromeda.foodcare.exceptions.core.ConflictException;
import com.andromeda.foodcare.exceptions.core.CriticalException;
import com.andromeda.foodcare.exceptions.core.NotFoundException;
import com.andromeda.foodcare.exceptions.core.OperationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Class responsible for handling application exceptions.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionAdvice {

    /**
     * Method responsible for handling attempts to access to non-existing objects.
     *
     * @param exception {@link NotFoundException} object
     * @return error message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException exception) {
        return exception.getMessage();
    }

    /**
     * Method responsible for handling critical exceptions.
     *
     * @param exception {@link CriticalException} object
     * @return error message
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CriticalException.class)
    public String critical(CriticalException exception) {
        return exception.getMessage();
    }

    /**
     * Method responsible for handling bad requests.
     *
     * @param exception {@link OperationException} object
     * @return error message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OperationException.class)
    public String badRequest(OperationException exception) {
        return exception.getMessage();
    }

    /**
     * Method responsible for handling application conflicts.
     *
     * @param exception {@link ConflictException} object
     * @return error message
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public String conflict(ConflictException exception) {
        return exception.getMessage();
    }
}
