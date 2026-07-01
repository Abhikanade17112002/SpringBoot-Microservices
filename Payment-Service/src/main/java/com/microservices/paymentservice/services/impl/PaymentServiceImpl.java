package com.microservices.paymentservice.services.impl;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentProcessingResult;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.enums.PaymentStatus;
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
    public PaymentResponseDTO processPayment(CreatePaymentRequestDTO paymentRequestDTO) throws Exception {
        if(paymentRepository.findByBookingId(paymentRequestDTO.getBookingId()).isPresent()){
            LOG.info("Payment Already Exists For Booking With Id ==> " + paymentRequestDTO.getBookingId());
            throw new Exception("Payment Already Exists For Booking With Id ==> " + paymentRequestDTO.getBookingId());
        }
        Payment payment = new Payment();
        payment.setActive(true);
        payment.setAmount(paymentRequestDTO.getAmount());
        payment.setBookingId(paymentRequestDTO.getBookingId());
        payment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
        payment.setCustomerId(paymentRequestDTO.getCustomerId());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionReference("TNX000" + (int)( Math.random() * 999999999));
        PaymentProcessingResult paymentProcessResponse = paymentProcessor.processPayment(paymentRequestDTO);
        payment.setPaymentStatus(paymentProcessResponse.getPaymentStatus());
        payment.setPaymentGatewayReference(paymentProcessResponse.getGatewayReference());
        return modelMapper.map(  paymentRepository.save(payment)  ,PaymentResponseDTO.class);
    }
}
