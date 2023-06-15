package com.safetynet.alerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Medical record already exists", code = HttpStatus.CONFLICT)
public class MedicalRecordAlreadyExistException extends RuntimeException {

    public MedicalRecordAlreadyExistException(String message) {
        super(message);
    }
}
