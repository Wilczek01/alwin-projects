package com.codersteam.alwin.core.api.notification;

import com.codersteam.alwin.core.api.model.operator.OperatorDto;

import java.util.Date;

/**
 * @author Adam Stepnowski
 */
public class NotificationDto {

    private Long id;
    private Long issueId;
    private Date creationDate;
    private Date readDate;
    private String message;
    private OperatorDto sender;
    private boolean readConfirmationRequired;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(final Long issueId) {
        this.issueId = issueId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(final Date readDate) {
        this.readDate = readDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public OperatorDto getSender() {
        return sender;
    }

    public void setSender(final OperatorDto sender) {
        this.sender = sender;
    }

    public boolean isReadConfirmationRequired() {
        return readConfirmationRequired;
    }

    public void setReadConfirmationRequired(final boolean readConfirmationRequired) {
        this.readConfirmationRequired = readConfirmationRequired;
    }
}
