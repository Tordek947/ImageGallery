package net.atlassian.cmathtutor.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class,
            net.atlassian.cmathtutor.exception.EntityNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(Exception exception) {
        logException(exception);
        return buildResponseEntity(HttpStatus.NOT_FOUND);
    }

    private void logException(Exception exception) {
        log.error("Resolved exception: ", exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException exception) {
        logException(exception);
        return buildResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleInternalServerError(Exception exception) {
        logException(exception);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(null, status);
    }
}
