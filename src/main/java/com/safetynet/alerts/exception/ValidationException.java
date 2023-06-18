package com.safetynet.alerts.exception;

import com.safetynet.alerts.object.model.ErrorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationException {

    private final Logger logger = LogManager.getLogger(ValidationException.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<ErrorModel>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErrorModel> errors = ex.getAllErrors()
                .stream().map(error -> {
                    String[] splitViolation = Objects.requireNonNull(error.getDefaultMessage()).split(":");
                    return ErrorModel.builder().field(splitViolation[0]).cause(splitViolation[1]).build();
                }).collect(Collectors.toList());

        logger.error("ValidationException: " + Arrays.toString(errors.toArray()));

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<ErrorModel>> getErrorsMap(List<ErrorModel> errors) {
        Map<String, List<ErrorModel>> errorResponse = new HashMap<>();
        errorResponse.put("validationErrors", errors);
        return errorResponse;
    }
}
