package com.safetynet.alerts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Medical record not found", value = HttpStatus.NOT_FOUND)
public class MedicalRecordNotFoundException extends RuntimeException {

        public MedicalRecordNotFoundException(String message) {
            super(message);
        }
}