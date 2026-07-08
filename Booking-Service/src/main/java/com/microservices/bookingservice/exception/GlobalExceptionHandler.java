package com.microservices.bookingservice.exception;

import com.microservices.bookingservice.dtos.response.ApiErrorResponseDTO;
import com.microservices.bookingservice.enums.ErrorCode;
import com.microservices.bookingservice.exception.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handlePaymentNotFoundException(
            PaymentNotFoundException exception,
            HttpServletRequest request
    ) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                ErrorCode.PAYMENT_NOT_FOUND_EXCEPTION,
                null
        );
    }

    @ExceptionHandler(PaymentAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponseDTO> handlePaymentAlreadyExistsException(
            PaymentAlreadyExistsException exception,
            HttpServletRequest request
    ) {

        return buildResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                ErrorCode.PAYMENT_ALREADY_EXISTS_EXCEPTION,
                null
        );
    }

    @ExceptionHandler(PaymentAlreadyRefundedException.class)
    public ResponseEntity<ApiErrorResponseDTO> handlePaymentAlreadyRefundedException(
            PaymentAlreadyRefundedException exception,
            HttpServletRequest request
    ) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                ErrorCode.PAYMENT_ALREADY_REFUNDED_EXCEPTION,
                null
        );
    }

    @ExceptionHandler(PaymentCannotBeRefundedException.class)
    public ResponseEntity<ApiErrorResponseDTO> handlePaymentCannotBeRefundedException(
            PaymentCannotBeRefundedException exception,
            HttpServletRequest request
    ) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                ErrorCode.PAYMENT_CANNOT_BE_REFUNDED_EXCEPTION,
                null
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleIllegalArgumentException(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
                Collections.emptyList()
        );
    }
    @ExceptionHandler(DownstreamValidationException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleDownstreamValidationException(
            DownstreamValidationException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                exception.getErrorCode(),
                exception.getValidationErrors()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        List<String> validationErrors =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                request.getRequestURI(),
                ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
                validationErrors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {

        List<String> validationErrors =
                exception.getConstraintViolations()
                        .stream()
                        .map(violation -> violation.getMessage())
                        .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                request.getRequestURI(),
                ErrorCode.CONSTRAINT_VIOLATION_EXCEPTION,
                validationErrors
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDTO> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getRequestURI(),
                ErrorCode.GENERIC_EXCEPTION,
                Collections.emptyList()
        );
    }

    private ResponseEntity<ApiErrorResponseDTO> buildResponse(
            HttpStatus status,
            String message,
            String path,
            ErrorCode errorCode,
            List<String> validationErrors
    ) {

        ApiErrorResponseDTO response =
                new ApiErrorResponseDTO(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        message,
                        path,
                        errorCode,
                        validationErrors
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}