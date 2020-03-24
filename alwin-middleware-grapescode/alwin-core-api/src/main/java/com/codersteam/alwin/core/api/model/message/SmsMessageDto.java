package com.codersteam.alwin.core.api.model.message;

/**
 * @author Piotr Naroznik
 */
public class SmsMessageDto {

    private String phoneNumber;
    private String message;
    private Long issueId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }
}
