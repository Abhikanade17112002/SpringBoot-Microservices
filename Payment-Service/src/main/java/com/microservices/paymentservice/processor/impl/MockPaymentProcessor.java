package com.microservices.paymentservice.processor.impl;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentProcessingResult;
import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.enums.PaymentStatus;
import com.microservices.paymentservice.processor.PaymentProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
/*
 * Spring Bean Resolution Revision:
 *
 * 1. If only one implementation of an interface exists, Spring injects it automatically.
 * 2. If multiple implementations exist, Spring throws NoUniqueBeanDefinitionException.
 * 3. @Primary marks the default bean to inject when multiple candidates exist.
 * 4. @Qualifier explicitly specifies which bean to inject and takes precedence over @Primary.
 * 5. Injecting List<Interface> or Map<String, Interface> provides all implementations,
 *    which is commonly used to implement the Strategy Pattern.
 *
 * Precedence:
 * @Qualifier > @Primary > Single Bean > Exception (if multiple beans with no qualifier/primary)
 */

@Component
@Primary
public class MockPaymentProcessor implements PaymentProcessor {
    private final static Logger LOG = LoggerFactory.getLogger(MockPaymentProcessor.class);
    @Override
    public PaymentProcessingResult processPayment(Payment request) {
        LOG.info("Entered Process Payment");
        PaymentProcessingResult result = new PaymentProcessingResult() ;
        if( request.getAmount().compareTo(BigDecimal.valueOf(100000)) <= 0 ){
            result.setPaymentStatus(PaymentStatus.SUCCESS);
            result.setMessage("Mock Payment Of Amount ==> " + request.getAmount() + " Successfully Completed ");
            result.setGatewayReference("MOCK");
        }
        else{
            result.setPaymentStatus(PaymentStatus.FAILED);
            result.setMessage("Mock Payment Of Amount ==> " + request.getAmount() + " Failed");
            result.setGatewayReference("MOCK");
        }
        return result;
    }

    @Override
    public PaymentProcessingResult refundPayment(Payment payment) {
        LOG.info("Payment With Payment Id ==> " + payment.getPaymentId() + " Processed For Refund");
        return new PaymentProcessingResult(PaymentStatus.REFUNDED,"MOCK","Payment With Payment Id ==> " + payment.getPaymentId() + " Refund Successfully Completed");
    }
}
