package com.microservices.paymentservice.controllers;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.request.PaymentRefundRequestDTO;
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

    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody CreatePaymentRequestDTO paymentRequestDTO ) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( paymentService.processPayment(paymentRequestDTO));
    }

    public ResponseEntity<PaymentResponseDTO> refundPayment(@Valid @RequestBody PaymentRefundRequestDTO paymentRefundRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.refundPayment(paymentRefundRequestDTO));
    }
}
