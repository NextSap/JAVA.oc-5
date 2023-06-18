package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FireStationException {

    private final Logger logger = LogManager.getLogger(FireStationException.class);

    @ExceptionHandler(FireStationNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundError(FireStationNotFoundException exception) {
        ErrorModel errorModel = ErrorModel.builder().field("fireStation").cause(exception.getMessage()).build();
        logger.error("FireStationNotFoundException: " + errorModel.toString());
        return new ResponseEntity<>(errorModel, org.springframework.http.HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FireStationAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleAlreadyExistError(FireStationAlreadyExistsException exception) {
        ErrorModel errorModel = ErrorModel.builder().field("fireStation").cause(exception.getMessage()).build();
        logger.error("FireStationAlreadyExistsException: " + errorModel.toString());
        return new ResponseEntity<>(errorModel, org.springframework.http.HttpStatus.CONFLICT);
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
