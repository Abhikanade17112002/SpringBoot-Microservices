package com.microservices.notificationservice.exceptions;

import com.microservices.notificationservice.dtos.ApiErrorResponseDTO;
import com.microservices.notificationservice.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleSdkClientException(
            SdkClientException exception,
            HttpServletRequest request) {

        ApiErrorResponseDTO response = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
                "Unable to communicate with AWS SES",
                request.getRequestURI(),
                ErrorCode.AWS_SDK_CLIENT_EXCEPTION,
                null
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
    @ExceptionHandler(AwsServiceException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleAwsServiceException(
            AwsServiceException exception,
            HttpServletRequest request) {

        ErrorCode errorCode = ErrorCode.AWS_SERVICE_EXCEPTION;
        HttpStatus status = HttpStatus.BAD_GATEWAY;

        String awsErrorCode = exception.awsErrorDetails() != null
                ? exception.awsErrorDetails().errorCode()
                : null;

        if ("MessageRejected".equals(awsErrorCode)) {
            errorCode = ErrorCode.AWS_SES_MESSAGE_REJECTED_EXCEPTION;
            status = HttpStatus.BAD_REQUEST;

        } else if ("BadRequestException".equals(awsErrorCode)) {
            errorCode = ErrorCode.AWS_SES_BAD_REQUEST_EXCEPTION;
            status = HttpStatus.BAD_REQUEST;

        } else if ("LimitExceededException".equals(awsErrorCode)) {
            errorCode = ErrorCode.AWS_SES_LIMIT_EXCEEDED_EXCEPTION;
            status = HttpStatus.TOO_MANY_REQUESTS;

        } else if ("MailFromDomainNotVerifiedException".equals(awsErrorCode)) {
            errorCode = ErrorCode.AWS_SES_MAIL_FROM_DOMAIN_NOT_VERIFIED_EXCEPTION;
            status = HttpStatus.BAD_REQUEST;
        }

        String message = exception.awsErrorDetails() != null
                && exception.awsErrorDetails().errorMessage() != null
                ? exception.awsErrorDetails().errorMessage()
                : "AWS SES failed to send the email";

        ApiErrorResponseDTO response = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                errorCode,
                null
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }


}
