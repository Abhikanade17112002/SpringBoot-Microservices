package com.microservices.paymentservice.services.impl;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.request.PaymentRefundRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentProcessingResult;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.enums.PaymentStatus;
import com.microservices.paymentservice.exception.exceptions.PaymentAlreadyRefundedException;
import com.microservices.paymentservice.exception.exceptions.PaymentCannotBeRefundedException;
import com.microservices.paymentservice.exception.exceptions.PaymentNotFoundException;
import com.microservices.paymentservice.processor.PaymentProcessor;
import com.microservices.paymentservice.repositories.PaymentRepository;
import com.microservices.paymentservice.services.PaymentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            throw new PaymentNotFoundException(paymentRequest.getBookingId());
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
    public PaymentResponseDTO refundPayment(PaymentRefundRequestDTO paymentRefundRequest) {
        Payment retrivedPayment = paymentRepository.findByBookingId(paymentRefundRequest.getBookigId())
                .orElseThrow(()-> new RuntimeException("Payment With Id ==> " + paymentRefundRequest.getBookigId() + " Not Found"));
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
}
