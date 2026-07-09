package com.microservices.paymentservice.controllers;


import com.microservices.paymentservice.dtos.response.InternalPaymentResponseDTO;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;
import com.microservices.paymentservice.services.PaymentService;
import feign.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentService paymentService ;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable(name = "paymentId") String paymentId ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.getPaymentById(paymentId));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPayments(@RequestParam( name = "pageno" , defaultValue = "0") int pageno , @RequestParam( name = "pagesize" , defaultValue = "5") int pagesize , @RequestParam(name = "sortby" , defaultValue = "paymentId") String sortby ,@RequestParam(name = "ascending", defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.getAllPayments(pageno,pagesize,sortby,ascending)) ;
    }
    @GetMapping("/my-payments")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Page<PaymentResponseDTO>> getAllCustomerPayments(@RequestParam( name = "pageno" , defaultValue = "0") int pageno , @RequestParam( name = "pagesize" , defaultValue = "5") int pagesize , @RequestParam(name = "sortby" , defaultValue = "paymentId") String sortby ,@RequestParam(name = "ascending", defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.getAllCustomerPayments(pageno,pagesize,sortby,ascending));
    }
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAdmin('ADMIN')")
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPaymentsByStatus(@PathVariable( name="status") PaymentStatus paymentStatus ,@RequestParam( name = "pageno" , defaultValue = "0") int pageno , @RequestParam( name = "pagesize" , defaultValue = "5") int pagesize , @RequestParam(name = "sortby" , defaultValue = "paymentId") String sortby ,@RequestParam(name = "ascending", defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getAllPaymentsByStatus(paymentStatus,pageno,pagesize,sortby,ascending) );
    }
    @GetMapping("/paymentmethode/{paymentmethode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPaymentsByPaymentStatus(@PathVariable( name="paymentmethode") PaymentMethode paymentMethode , @RequestParam( name = "pageno" , defaultValue = "0") int pageno , @RequestParam( name = "pagesize" , defaultValue = "5") int pagesize , @RequestParam(name = "sortby" , defaultValue = "paymentId") String sortby , @RequestParam(name = "ascending", defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getAllPaymentsByPaymentStatus(paymentMethode,pageno,pagesize,sortby,ascending) );
    }
    @PutMapping("/refund/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InternalPaymentResponseDTO> processBookingPaymentRefundById(@PathVariable(name = "bookingId") String bookingId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.refundPaymentWithBookingId(bookingId) );
    }

    @GetMapping("/bookings/{bookingId}")
    @PreAuthorize("hasAuthority('CUSTOMER','ADMIN')")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@PathVariable(name = "bookingId") String bookingId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getPaymentByBookingIdForAdminOrCustomer(bookingId) );
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAuthority('CUSTOMER','ADMIN')")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsBetweenDates(@RequestParam(name = "start") LocalDate start , @RequestParam(name = "end") LocalDate end , @RequestParam( name = "pageno" , defaultValue = "0") int pageno , @RequestParam(name = "size" , defaultValue = "5") int size , @RequestParam(name = "sortby" ,defaultValue = "paymentId") String sortby , @RequestParam(name = "ascending", defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getPaymentsBetweenDates(start,end,pageno,size,sortby,ascending) );
    }

    @GetMapping("/transaction/{transactionReference}")
    @PreAuthorize("hasAuthority('CUSTOMER','ADMIN')")
    public ResponseEntity<PaymentResponseDTO> getPaymentByTransactionReference(@PathVariable(name = "transactionReference") String transactionReference){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getPaymentByTransactionReference(transactionReference) );
    }

    @GetMapping("/gateway/{gatewayReference}")
    @PreAuthorize("hasAuthority('CUSTOMER','ADMIN')")
    public ResponseEntity<PaymentResponseDTO> getPaymentByGatewayReference(@PathVariable(name = "gatewayReference") String gatewayReference){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( paymentService.getPaymentByGatewayReference(gatewayReference) );
    }


}
