package com.microservices.paymentservice.controllers;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.InternalPaymentResponseDTO;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/payments")
public class InternalController {
    private final PaymentService paymentService ;

    public InternalController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public InternalPaymentResponseDTO processPayment(@Valid @RequestBody CreatePaymentRequestDTO paymentRequestDTO ) throws Exception {
        return paymentService.processPayment(paymentRequestDTO);
    }
    @PostMapping("/booking/{bookingId}/refund")
    public InternalPaymentResponseDTO refundPaymentWithBookingId(@PathVariable(name = "bookingId") String bookingId) {
        return paymentService.refundPaymentWithBookingId(bookingId);
    }

    @GetMapping("/booking/{bookingId}")
    public PaymentResponseDTO getPaymentByBookingId(@PathVariable( name = "bookingId") String bookingId ){
        return paymentService.getPaymentByBookingId(bookingId) ;
    }

    @PutMapping("/{bookingId}/retry")
    public InternalPaymentResponseDTO retryPaymentByBookingId(@PathVariable( name = "bookingId") String bookingId ){
        return paymentService.retryPaymentByBookingId(bookingId) ;
    }
}
