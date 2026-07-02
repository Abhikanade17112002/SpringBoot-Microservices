package com.microservices.bookingservice.clinets.paymentclients;


import com.microservices.bookingservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.bookingservice.dtos.response.PaymentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient( name = "PAYMENT-SERVICE-DEV" , path = "/api/v1/internal/payments")
public interface InternalPaymentClient {
    @PostMapping
    public PaymentResponseDTO processPayment(@RequestBody CreatePaymentRequestDTO createPaymentRequestDTO);
    @GetMapping("/bookings/{bookingId}")
    public PaymentResponseDTO getPaymentByBookingId(@PathVariable( name = "bookingId") String bookingId );
    @PutMapping("/{bookingId}/retry")
    public PaymentResponseDTO retryPaymentByBookingId(@PathVariable( name = "bookingId") String bookingId );
}
