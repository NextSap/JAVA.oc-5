package com.safetynet.alerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "FireStation already exist", code = HttpStatus.CONFLICT)
public class FireStationAlreadyExistException extends RuntimeException {

    public FireStationAlreadyExistException(String message) {
        super(message);
    }
}
