package com.microservices.notificationservice.dtos;

public class SendEmailResponseDTO {
    private Boolean isEmailSentSuccessfully ;
    private String  messageId ;
    private String  emailIdSubject ;
    private String  emailIdBody ;

    public SendEmailResponseDTO() {
    }

    public SendEmailResponseDTO(Boolean isEmailSentSuccessfully, String messageId, String emailIdSubject, String emailIdBody) {
        this.isEmailSentSuccessfully = isEmailSentSuccessfully;
        this.messageId = messageId;
        this.emailIdSubject = emailIdSubject;
        this.emailIdBody = emailIdBody;
    }

    public Boolean getEmailSentSuccessfully() {
        return isEmailSentSuccessfully;
    }

    public void setEmailSentSuccessfully(Boolean emailSentSuccessfully) {
        isEmailSentSuccessfully = emailSentSuccessfully;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEmailIdSubject() {
        return emailIdSubject;
    }

    public void setEmailIdSubject(String emailIdSubject) {
        this.emailIdSubject = emailIdSubject;
    }

    public String getEmailIdBody() {
        return emailIdBody;
    }

    public void setEmailIdBody(String emailIdBody) {
        this.emailIdBody = emailIdBody;
    }


    @Override
    public String toString() {
        return "SendEmailResponseDTO{" +
                "isEmailSentSuccessfully=" + isEmailSentSuccessfully +
                ", messageId='" + messageId + '\'' +
                ", emailIdSubject='" + emailIdSubject + '\'' +
                ", emailIdBody='" + emailIdBody + '\'' +
                '}';
    }
}
