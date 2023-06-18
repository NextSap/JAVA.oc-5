package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MedicalException {

    private final Logger logger = LogManager.getLogger(MedicalException.class);

    @ExceptionHandler(MedicalNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundError(MedicalNotFoundException exception) {
        ErrorModel errorModel = ErrorModel.builder().field("medical").cause(exception.getMessage()).build();
        logger.error("MedicalNotFoundException: " + errorModel.toString());
        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicalAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleAlreadyExistError(MedicalAlreadyExistsException exception) {
        ErrorModel errorModel = ErrorModel.builder().field("medical").cause(exception.getMessage()).build();
        logger.error("MedicalAlreadyExistsException: " + errorModel.toString());
        return new ResponseEntity<>(errorModel, HttpStatus.CONFLICT);
    }

    public static class MedicalNotFoundException extends RuntimeException {
        public MedicalNotFoundException(String message) {
            super(message);
        }
    }

    public static class MedicalAlreadyExistsException extends RuntimeException {
        public MedicalAlreadyExistsException(String message) {
            super(message);
        }
    }
}
