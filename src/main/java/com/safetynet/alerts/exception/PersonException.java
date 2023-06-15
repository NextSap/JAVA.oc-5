package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class PersonException {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundError(PersonNotFoundException exception) {
        return new ResponseEntity<>(ErrorModel.builder().field("person").cause(exception.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleAlreadyExistError(PersonAlreadyExistsException exception) {
        return new ResponseEntity<>(ErrorModel.builder().field("person").cause(exception.getMessage()).build(), HttpStatus.CONFLICT);
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
