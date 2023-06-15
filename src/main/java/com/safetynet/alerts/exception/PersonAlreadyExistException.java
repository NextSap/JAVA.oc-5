package com.safetynet.alerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Person already exists", code = HttpStatus.CONFLICT)
public class PersonAlreadyExistException extends RuntimeException {

    public PersonAlreadyExistException(String message) {
        super(message);
    }
}
