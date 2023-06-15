package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FireStationException {

    @ExceptionHandler(FireStationNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundError(FireStationNotFoundException exception) {
        return new ResponseEntity<>(ErrorModel.builder().field("fireStation").cause(exception.getMessage()).build(), org.springframework.http.HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FireStationAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleAlreadyExistError(FireStationAlreadyExistsException exception) {
        return new ResponseEntity<>(ErrorModel.builder().field("fireStation").cause(exception.getMessage()).build(), org.springframework.http.HttpStatus.CONFLICT);
    }

    public static class FireStationNotFoundException extends RuntimeException {
        public FireStationNotFoundException(String message) {
            super(message);
        }
    }

    public static class FireStationAlreadyExistsException extends RuntimeException {
        public FireStationAlreadyExistsException(String message) {
            super(message);
        }
    }
}
