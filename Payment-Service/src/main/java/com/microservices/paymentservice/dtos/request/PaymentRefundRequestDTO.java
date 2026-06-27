package com.microservices.paymentservice.dtos.request;

public class PaymentRefundRequestDTO {
    private String refundResponse ;

    public PaymentRefundRequestDTO(String refundResponse) {
        this.refundResponse = refundResponse;
    }

    public PaymentRefundRequestDTO() {
    }

    public String getRefundResponse() {
        return refundResponse;
    }

    public void setRefundResponse(String refundResponse) {
        this.refundResponse = refundResponse;
    }

    @Override
    public String toString() {
        return "PaymentRefundRequestDTO{" +
                "refundResponse='" + refundResponse + '\'' +
                '}';
    }
}
