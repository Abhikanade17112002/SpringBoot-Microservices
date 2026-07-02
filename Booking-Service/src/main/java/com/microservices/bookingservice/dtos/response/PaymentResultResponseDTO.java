package com.microservices.bookingservice.dtos.response;

import com.microservices.bookingservice.enums.PaymentStatus;

public class PaymentResultResponseDTO {
    private String paymentId;
    private PaymentStatus paymentStatus;
    private String transactionRefrenceId;

    public PaymentResultResponseDTO() {
    }

    public PaymentResultResponseDTO(String paymentId, PaymentStatus paymentStatus, String transactionRefrenceId) {
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.transactionRefrenceId = transactionRefrenceId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionRefrenceId() {
        return transactionRefrenceId;
    }

    public void setTransactionRefrenceId(String transactionRefrenceId) {
        this.transactionRefrenceId = transactionRefrenceId;
    }

    @Override
    public String toString() {
        return "PaymentResultResponseDTO{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", transactionRefrenceId='" + transactionRefrenceId + '\'' +
                '}';
    }
}
