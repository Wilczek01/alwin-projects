package com.codersteam.alwin.model.notification;

/**
 * @author Adam Stepnowski
 */
public class CreateIssueNotificationRequest {

    private Long issueId;
    private String message;
    private boolean readConfirmationRequired;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public boolean isReadConfirmationRequired() {
        return readConfirmationRequired;
    }

    public void setReadConfirmationRequired(final boolean readConfirmationRequired) {
        this.readConfirmationRequired = readConfirmationRequired;
    }
}
