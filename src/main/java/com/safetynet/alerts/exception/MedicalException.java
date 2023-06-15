package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MedicalException {

    @ExceptionHandler(MedicalNotFoundException.class)
    public ResponseEntity<ErrorModel> handleNotFoundError(MedicalNotFoundException exception) {
        return new ResponseEntity<>(ErrorModel.builder().field("medical").cause(exception.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicalAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleAlreadyExistError(MedicalAlreadyExistsException exception) {
        return new ResponseEntity<>(ErrorModel.builder().field("medical").cause(exception.getMessage()).build(), HttpStatus.CONFLICT);
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
