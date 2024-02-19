package io.vicarius.quotaservice.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandler {

    @ResponseBody
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDTO handleEntityExistsException(EntityExistsException ex) {

        return new ErrorDTO(400, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDTO handleEntityNotFoundException(EntityNotFoundException ex) {

        return new ErrorDTO(404, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(QuotaExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    protected ErrorDTO handleQuotaExceededException(QuotaExceededException ex) {

        return new ErrorDTO(429, ex.getMessage());
    }
}
