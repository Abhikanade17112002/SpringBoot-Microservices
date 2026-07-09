package com.microservices.bookingservice.clinets.paymentclients;


import com.microservices.bookingservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.bookingservice.dtos.response.InternalPaymentResponseDTO;
import com.microservices.bookingservice.dtos.response.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient( name = "PAYMENT-SERVICE-DEV" , path = "/api/v1/internal/payments")
public interface InternalPaymentClient {
    @PostMapping
    public InternalPaymentResponseDTO processPayment(@RequestBody CreatePaymentRequestDTO createPaymentRequestDTO);
    @GetMapping("/booking/{bookingId}")
    public PaymentResponseDTO getPaymentByBookingId(@PathVariable( name = "bookingId") String bookingId );
    @PutMapping("/{bookingId}/retry")
    public PaymentResponseDTO retryPaymentByBookingId(@PathVariable( name = "bookingId") String bookingId );
    @PostMapping("/booking/{bookingId}/refund")
    public InternalPaymentResponseDTO processBookingRefundWithId(@PathVariable(name = "bookingId") String BookingId);
}
