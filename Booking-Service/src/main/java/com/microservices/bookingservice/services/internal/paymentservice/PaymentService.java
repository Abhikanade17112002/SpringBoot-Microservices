package com.microservices.bookingservice.services.internal.paymentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.bookingservice.clinets.paymentclients.InternalPaymentClient;
import com.microservices.bookingservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.bookingservice.dtos.response.ApiErrorResponseDTO;
import com.microservices.bookingservice.dtos.response.InternalPaymentResponseDTO;
import com.microservices.bookingservice.dtos.response.PaymentResponseDTO;
import com.microservices.bookingservice.exception.exceptions.*;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final static Logger log = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private InternalPaymentClient internalPaymentClient;
    @Autowired
    private ObjectMapper objectMapper;

    @CircuitBreaker( name = "payment-service-cb", fallbackMethod = "processPaymentFallback")
    @Retry(name = "payment-service-retry")
    public InternalPaymentResponseDTO processPayment(CreatePaymentRequestDTO createPaymentRequest){
        return internalPaymentClient.processPayment(createPaymentRequest);
    }
    public InternalPaymentResponseDTO processPaymentFallback(CreatePaymentRequestDTO createPaymentRequest,Throwable exception) throws Throwable {
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        if(exception instanceof CallNotPermittedException  ) {
            throw new ServiceUnAvailableException("Payment Service is temporarily unavailable.");
        }
        if(exception instanceof FeignException) {
            String json = ((FeignException) exception).contentUTF8();
            if(json != null && !json.trim().startsWith("{") && !json.trim().endsWith("}")){
                throw new ServiceUnAvailableException(json);
            }
            try {
                ApiErrorResponseDTO error = objectMapper.readValue(json, ApiErrorResponseDTO.class);
                switch (error.getErrorCode()) {
                    case PAYMENT_NOT_FOUND_EXCEPTION ->
                            throw new PaymentNotFoundException(error.getMessage());
                    case PAYMENT_ALREADY_EXISTS_EXCEPTION ->
                            throw new PaymentAlreadyExistsException(error.getMessage());
                    case PAYMENT_ALREADY_REFUNDED_EXCEPTION ->
                            throw new PaymentAlreadyRefundedException(error.getMessage());
                    case PAYMENT_CANNOT_BE_REFUNDED_EXCEPTION ->
                            throw new PaymentCannotBeRefundedException(error.getMessage());
                    case METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
                         CONSTRAINT_VIOLATION_EXCEPTION ->
                            throw new DownstreamValidationException(error.getMessage(), error.getErrorCode(),error.getValidationErrors());
                    case FEIGN_CLIENT_EXCEPTION, GENERIC_EXCEPTION ->
                            throw new RuntimeException(error.getMessage());
                    default ->
                            throw new RuntimeException("Unknown error occurred: " + error.getMessage());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(exception.getMessage());
    }
    @CircuitBreaker( name = "payment-service-cb", fallbackMethod = "retryPaymentByBookingIdFallback")
    @Retry(name = "payment-service-retry")
    public PaymentResponseDTO retryPaymentByBookingId(String bookingId) {
        return internalPaymentClient.retryPaymentByBookingId(bookingId);
    }

    public PaymentResponseDTO retryPaymentByBookingIdFallback(String bookingId,Throwable exception) throws Throwable {
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        if(exception instanceof CallNotPermittedException  ) {
            throw new ServiceUnAvailableException("Payment Service is temporarily unavailable.");
        }
        if(exception instanceof FeignException) {
            String json = ((FeignException) exception).contentUTF8();
            if(json != null && !json.trim().startsWith("{") && !json.trim().endsWith("}")){
                throw new ServiceUnAvailableException(json);
            }
            try {
                ApiErrorResponseDTO error = objectMapper.readValue(json, ApiErrorResponseDTO.class);
                switch (error.getErrorCode()) {
                    case PAYMENT_NOT_FOUND_EXCEPTION ->
                            throw new PaymentNotFoundException(error.getMessage());
                    case PAYMENT_ALREADY_EXISTS_EXCEPTION ->
                            throw new PaymentAlreadyExistsException(error.getMessage());
                    case PAYMENT_ALREADY_REFUNDED_EXCEPTION ->
                            throw new PaymentAlreadyRefundedException(error.getMessage());
                    case PAYMENT_CANNOT_BE_REFUNDED_EXCEPTION ->
                            throw new PaymentCannotBeRefundedException(error.getMessage());
                    case METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
                         CONSTRAINT_VIOLATION_EXCEPTION ->
                            throw new DownstreamValidationException(error.getMessage(), error.getErrorCode(),error.getValidationErrors());
                    case FEIGN_CLIENT_EXCEPTION, GENERIC_EXCEPTION ->
                            throw new RuntimeException(error.getMessage());
                    default ->
                            throw new RuntimeException("Unknown error occurred: " + error.getMessage());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(exception.getMessage());
    }
    @CircuitBreaker( name = "payment-service-cb", fallbackMethod = "processBookingRefundWithIdFallback")
    @Retry(name = "payment-service-retry")
    public InternalPaymentResponseDTO processBookingRefundWithId(String bookingId) {
        return internalPaymentClient.processBookingRefundWithId(bookingId);
    }
    public InternalPaymentResponseDTO processBookingRefundWithIdFallback(String bookingId,Throwable exception) throws Throwable {
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        if(exception instanceof CallNotPermittedException  ) {
            throw new ServiceUnAvailableException("Payment Service is temporarily unavailable.");
        }
        if(exception instanceof FeignException) {
            String json = ((FeignException) exception).contentUTF8();
            if(json != null && !json.trim().startsWith("{") && !json.trim().endsWith("}")){
                throw new ServiceUnAvailableException(json);
            }
            try {
                ApiErrorResponseDTO error = objectMapper.readValue(json, ApiErrorResponseDTO.class);
                switch (error.getErrorCode()) {
                    case PAYMENT_NOT_FOUND_EXCEPTION ->
                            throw new PaymentNotFoundException(error.getMessage());
                    case PAYMENT_ALREADY_EXISTS_EXCEPTION ->
                            throw new PaymentAlreadyExistsException(error.getMessage());
                    case PAYMENT_ALREADY_REFUNDED_EXCEPTION ->
                            throw new PaymentAlreadyRefundedException(error.getMessage());
                    case PAYMENT_CANNOT_BE_REFUNDED_EXCEPTION ->
                            throw new PaymentCannotBeRefundedException(error.getMessage());
                    case METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
                         CONSTRAINT_VIOLATION_EXCEPTION ->
                            throw new DownstreamValidationException(error.getMessage(), error.getErrorCode(),error.getValidationErrors());
                    case FEIGN_CLIENT_EXCEPTION, GENERIC_EXCEPTION ->
                            throw new RuntimeException(error.getMessage());
                    default ->
                            throw new RuntimeException("Unknown error occurred: " + error.getMessage());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(exception.getMessage());
    }
    @CircuitBreaker( name = "payment-service-cb", fallbackMethod = "getPaymentByBookingIdFallback")
    @Retry(name = "payment-service-retry")
    public PaymentResponseDTO getPaymentByBookingId(String bookingId) {
        return internalPaymentClient.getPaymentByBookingId(bookingId);
    }
    public PaymentResponseDTO getPaymentByBookingIdFallback(String bookingId,Throwable exception) throws Throwable {
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        if(exception instanceof CallNotPermittedException  ) {
            throw new ServiceUnAvailableException("Payment Service is temporarily unavailable.");
        }
        if(exception instanceof FeignException) {
            String json = ((FeignException) exception).contentUTF8();
            if(json != null && !json.trim().startsWith("{") && !json.trim().endsWith("}")){
                throw new ServiceUnAvailableException(json);
            }
            try {
                ApiErrorResponseDTO error = objectMapper.readValue(json, ApiErrorResponseDTO.class);
                switch (error.getErrorCode()) {
                    case PAYMENT_NOT_FOUND_EXCEPTION ->
                            throw new PaymentNotFoundException(error.getMessage());
                    case PAYMENT_ALREADY_EXISTS_EXCEPTION ->
                            throw new PaymentAlreadyExistsException(error.getMessage());
                    case PAYMENT_ALREADY_REFUNDED_EXCEPTION ->
                            throw new PaymentAlreadyRefundedException(error.getMessage());
                    case PAYMENT_CANNOT_BE_REFUNDED_EXCEPTION ->
                            throw new PaymentCannotBeRefundedException(error.getMessage());
                    case METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
                         CONSTRAINT_VIOLATION_EXCEPTION ->
                            throw new DownstreamValidationException(error.getMessage(), error.getErrorCode(),error.getValidationErrors());
                    case FEIGN_CLIENT_EXCEPTION, GENERIC_EXCEPTION ->
                            throw new RuntimeException(error.getMessage());
                    default ->
                            throw new RuntimeException("Unknown error occurred: " + error.getMessage());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(exception.getMessage());
    }
}


