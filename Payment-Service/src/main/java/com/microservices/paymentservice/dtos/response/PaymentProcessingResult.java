package com.microservices.paymentservice.dtos.response;

import com.microservices.paymentservice.enums.PaymentStatus;

public class PaymentProcessingResult {

    private PaymentStatus paymentStatus;

    private String gatewayReference;

    private String message;

    public PaymentProcessingResult() {
    }

    public PaymentProcessingResult(PaymentStatus paymentStatus, String gatewayReference, String message) {
        this.paymentStatus = paymentStatus;
        this.gatewayReference = gatewayReference;
        this.message = message;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getGatewayReference() {
        return gatewayReference;
    }

    public void setGatewayReference(String gatewayReference) {
        this.gatewayReference = gatewayReference;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentProcessingResult{" +
                "paymentStatus=" + paymentStatus +
                ", gatewayReference='" + gatewayReference + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
