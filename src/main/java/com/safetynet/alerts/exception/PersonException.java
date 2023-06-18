package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class PersonException {

    private final Logger logger = LogManager.getLogger(PersonException.class);

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundError(PersonNotFoundException exception) {
        ErrorModel errorModel = ErrorModel.builder().field("person").cause(exception.getMessage()).build();
        logger.error("PersonNotFoundException: " + errorModel.toString());
        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleAlreadyExistError(PersonAlreadyExistsException exception) {
        ErrorModel errorModel = ErrorModel.builder().field("person").cause(exception.getMessage()).build();
        logger.error("PersonAlreadyExistsException: " + errorModel.toString());
        return new ResponseEntity<>(errorModel, HttpStatus.CONFLICT);
    }

    public static class PersonNotFoundException extends RuntimeException {
        public PersonNotFoundException(String message) {
            super(message);
        }
    }

    public static class PersonAlreadyExistsException extends RuntimeException {
        public PersonAlreadyExistsException(String message) {
            super(message);
        }
    }
}
