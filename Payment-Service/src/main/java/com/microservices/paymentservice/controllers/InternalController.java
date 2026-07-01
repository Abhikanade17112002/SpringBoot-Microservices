package com.microservices.paymentservice.controllers;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.services.PaymentService;
import jakarta.transaction.Status;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {
    private final PaymentService paymentService ;

    public InternalController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody CreatePaymentRequestDTO paymentRequestDTO ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( paymentService.processPayment(paymentRequestDTO));
    }
}
