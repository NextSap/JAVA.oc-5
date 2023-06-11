package com.safetynet.alerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "FireStation not found", code = HttpStatus.NOT_FOUND)
public class FireStationNotFoundException extends RuntimeException {
    public FireStationNotFoundException(String message) {
        super(message);
    }
}
