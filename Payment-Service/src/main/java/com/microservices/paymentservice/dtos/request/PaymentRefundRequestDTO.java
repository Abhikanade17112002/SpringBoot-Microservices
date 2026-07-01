package com.microservices.paymentservice.dtos.request;

public class PaymentRefundRequestDTO {
    private String bookigId ;

    public PaymentRefundRequestDTO() {
    }

    public PaymentRefundRequestDTO(String bookigId) {
        this.bookigId = bookigId;
    }

    public String getBookigId() {
        return bookigId;
    }

    public void setBookigId(String bookigId) {
        this.bookigId = bookigId;
    }

    @Override
    public String toString() {
        return "PaymentRefundRequestDTO{" +
                "bookigId='" + bookigId + '\'' +
                '}';
    }
}
