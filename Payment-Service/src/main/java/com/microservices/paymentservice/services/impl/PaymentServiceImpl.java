package com.microservices.paymentservice.services.impl;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentProcessingResult;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.entities.AuthenticatedUser;
import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;
import com.microservices.paymentservice.exception.exceptions.PaymentAlreadyExistsException;
import com.microservices.paymentservice.exception.exceptions.PaymentAlreadyRefundedException;
import com.microservices.paymentservice.exception.exceptions.PaymentCannotBeRefundedException;
import com.microservices.paymentservice.exception.exceptions.PaymentNotFoundException;
import com.microservices.paymentservice.processor.PaymentProcessor;
import com.microservices.paymentservice.repositories.PaymentRepository;
import com.microservices.paymentservice.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {
    private final static Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final ModelMapper modelMapper ;
    private final PaymentRepository paymentRepository ;
    private final PaymentProcessor paymentProcessor;

    public PaymentServiceImpl(ModelMapper modelMapper, PaymentRepository paymentRepository, PaymentProcessor paymentProcessor) {
        this.modelMapper = modelMapper;
        this.paymentRepository = paymentRepository;
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public PaymentResponseDTO processPayment(CreatePaymentRequestDTO paymentRequest)  {
        if(paymentRepository.findByBookingId(paymentRequest.getBookingId()).isPresent()){
            LOG.info("Payment Already Exists For Booking With Id ==> " + paymentRequest.getBookingId());
            throw new PaymentAlreadyExistsException(paymentRequest.getBookingId());
        }
        Payment payment = new Payment();
        payment.setActive(true);
        payment.setAmount(paymentRequest.getAmount());
        payment.setBookingId(paymentRequest.getBookingId());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setCustomerId(paymentRequest.getCustomerId());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionReference("TNX000" + (int)( Math.random() * 999999999));
        PaymentProcessingResult paymentProcessResponse = paymentProcessor.processPayment(payment);
        payment.setPaymentStatus(paymentProcessResponse.getPaymentStatus());
        payment.setPaymentGatewayReference(paymentProcessResponse.getGatewayReference());
        payment.setMessage(paymentProcessResponse.getMessage());
        return modelMapper.map(  paymentRepository.save(payment)  ,PaymentResponseDTO.class);
    }
    @Override
    public PaymentResponseDTO getPaymentByBookingId(String bookingId) {
        Payment reterivedPayment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(()-> new PaymentNotFoundException("Payment With Booking Id ==> " + bookingId + " Not Found" )) ;
        return modelMapper.map( reterivedPayment , PaymentResponseDTO.class);
    }

    @Override
    public PaymentResponseDTO getPaymentById(String paymentId) {
        Payment reterivedPayment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new PaymentNotFoundException("Payment With Id ==> " + paymentId + " Not Found" )) ;
        AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        if( user.getRole().equals("CUSTOMER") && !user.getUserId().equals(reterivedPayment.getCustomerId())){
            LOG.info("Customer With Id ==> " + user.getUserId() + " Not Permitted To Access The Payment Wth Id ==> " + reterivedPayment.getPaymentId());
            throw new RuntimeException("Customer With Id ==> " + user.getUserId() + " Not Permitted To Access The Payment Wth Id ==> " + reterivedPayment.getPaymentId());
        }
        return modelMapper.map(reterivedPayment,PaymentResponseDTO.class);
    }
    @Override
    public Page<PaymentResponseDTO> getAllPayments(int pageno, int pagesize, String sortby, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable page = PageRequest.of(pageno,pagesize,sort);
        Page<Payment> pagedPayments = paymentRepository.findAll(page) ;
        return pagedPayments.map((payment)-> modelMapper.map(payment,PaymentResponseDTO.class));
    }
    @Override
    public Page<PaymentResponseDTO> getAllCustomerPayments(int pageno, int pagesize, String sortby, Boolean ascending) {
        AuthenticatedUser user = (AuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        Sort sort = ascending ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable page = PageRequest.of(pageno,pagesize,sort);
        Page<Payment> pagedPayments = paymentRepository.findByCustomerId(user.getUserId(),page) ;
        return pagedPayments.map((payment)-> modelMapper.map(payment,PaymentResponseDTO.class));
    }
    @Override
    public Page<PaymentResponseDTO> getAllPaymentsByStatus(PaymentStatus paymentStatus, int pageno, int pagesize, String sortby, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable page = PageRequest.of(pageno,pagesize,sort);
        Page<Payment> pagedPayments = paymentRepository.findByPaymentStatus(paymentStatus,page) ;
        return pagedPayments.map((payment)-> modelMapper.map(payment,PaymentResponseDTO.class));
    }
    @Override
    public Page<PaymentResponseDTO> getAllPaymentsByPaymentStatus(PaymentMethode paymentMethode, int pageno, int pagesize, String sortby, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable page = PageRequest.of(pageno,pagesize,sort);
        Page<Payment> pagedPayments = paymentRepository.findByPaymentMethod(paymentMethode,page) ;
        return pagedPayments.map((payment)-> modelMapper.map(payment,PaymentResponseDTO.class));
    }
    @Override
    public PaymentResponseDTO processBookingPaymentRefund(String bookingId) {
        Payment retrivedPayment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(()-> new RuntimeException("Payment With Id ==> " + bookingId + " Not Found"));
        if( retrivedPayment.getPaymentStatus().equals(PaymentStatus.REFUNDED)){
            LOG.info("Payment Refund Status ==> " + retrivedPayment.getPaymentStatus() + " Payment Already Refunded ");
            retrivedPayment.setActive(false);
            paymentRepository.save(retrivedPayment);
            throw new PaymentAlreadyRefundedException("Payment Already Refunded");
        }

        if( !retrivedPayment.getPaymentStatus().equals(PaymentStatus.SUCCESS)){
            LOG.info("Payment Refund Status ==> " + retrivedPayment.getPaymentStatus() + " Payment Should Be Completed To Be Refunded");
            throw new PaymentCannotBeRefundedException("Payment Should Be Completed To Be Refunded");
        }

        PaymentProcessingResult result =  paymentProcessor.refundPayment(retrivedPayment);
        retrivedPayment.setPaymentStatus(result.getPaymentStatus());
        retrivedPayment.setPaymentGatewayReference(result.getGatewayReference());
        retrivedPayment.setMessage( result.getMessage());
        retrivedPayment.setRefundTransactionReference("TNXRNF0000" +  (int)( Math.random() * 999999999));
        return modelMapper.map(  paymentRepository.save(retrivedPayment)  ,PaymentResponseDTO.class);
    }

    @Override
    @Transactional
    public PaymentResponseDTO retryPaymentByBookingId(String bookingId) {
        Payment reterivedpayment = paymentRepository
                .findTopByBookingIdOrderByCreatedAtDesc(bookingId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Payment with Booking Id ==> " + bookingId + " Not Found"
                        ));
        if( !reterivedpayment.getPaymentStatus().equals(PaymentStatus.FAILED)){
            throw new RuntimeException("Payment Status ==> " + reterivedpayment.getPaymentStatus());
        }
        Payment payment = new Payment();
        payment.setActive(true);
        payment.setAmount(reterivedpayment.getAmount());
        payment.setBookingId(reterivedpayment.getBookingId());
        payment.setPaymentMethod(reterivedpayment.getPaymentMethod());
        payment.setCustomerId(reterivedpayment.getCustomerId());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionReference("TNX000" + (int)( Math.random() * 999999999));
        PaymentProcessingResult paymentProcessResponse = paymentProcessor.processPayment(payment);
        payment.setPaymentStatus(paymentProcessResponse.getPaymentStatus());
        payment.setPaymentGatewayReference(paymentProcessResponse.getGatewayReference());
        payment.setMessage(paymentProcessResponse.getMessage());
        return modelMapper.map(  paymentRepository.save(payment)  ,PaymentResponseDTO.class);
    }
}
