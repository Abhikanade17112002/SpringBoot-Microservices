package com.microservices.paymentservice.controllers;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
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

    @GetMapping("/")
    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody CreatePaymentRequestDTO paymentRequestDTO ) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( paymentService.processPayment(paymentRequestDTO));
    }

//    public ResponseEntity<PaymentResponseDTO> refundPayment(@Valid @RequestBody PaymentRefundRequestDTO paymentRefundRequestDTO) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(paymentService.refundPayment(paymentRefundRequestDTO));
//    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@Valid @PathVariable( name = "bookingId") String bookingId ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getPaymentByBookingId(bookingId) ) ;
    }
}
