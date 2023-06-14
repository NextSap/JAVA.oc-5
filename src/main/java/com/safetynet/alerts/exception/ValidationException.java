package com.safetynet.alerts.exception;

import com.safetynet.alerts.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<ErrorDto>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErrorDto> errors = ex.getAllErrors()
                .stream().map(error -> {
                    String[] splitViolation = Objects.requireNonNull(error.getDefaultMessage()).split(":");
                    return ErrorDto.builder().field(splitViolation[0]).cause(splitViolation[1]).build();
                }).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<ErrorDto>> getErrorsMap(List<ErrorDto> errors) {
        Map<String, List<ErrorDto>> errorResponse = new HashMap<>();
        errorResponse.put("validationErrors", errors);
        return errorResponse;
    }
}
