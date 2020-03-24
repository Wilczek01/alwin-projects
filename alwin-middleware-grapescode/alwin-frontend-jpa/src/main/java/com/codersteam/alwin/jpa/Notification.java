package com.codersteam.alwin.jpa;

import com.codersteam.alwin.jpa.operator.Operator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Adam Stepnowski
 */
@Entity
@Table(name = "notification")
public class Notification {

    @SequenceGenerator(name = "notificationSEQ", sequenceName = "notification_id_seq", allocationSize = 1, initialValue = 100)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationSEQ")
    private Long id;

    @Column(name = "issue_id", nullable = false)
    private Long issueId;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "read_date")
    private Date readDate;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", nullable = false)
    private Operator sender;

    @Column(name = "read_confirmation_required", nullable = false)
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

    public Operator getSender() {
        return sender;
    }

    public void setSender(final Operator sender) {
        this.sender = sender;
    }

    public boolean isReadConfirmationRequired() {
        return readConfirmationRequired;
    }

    public void setReadConfirmationRequired(final boolean allowedToMarkAsRead) {
        this.readConfirmationRequired = allowedToMarkAsRead;
    }
}
