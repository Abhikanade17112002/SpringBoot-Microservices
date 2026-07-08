package com.microservices.bookingservice.exception.exceptions;

import com.microservices.bookingservice.enums.ErrorCode;

import java.util.List;

public class DownstreamValidationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final List<String> validationErrors;

    public DownstreamValidationException(String message, ErrorCode errorCode, List<String> validationErrors) {
        super(message);
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}