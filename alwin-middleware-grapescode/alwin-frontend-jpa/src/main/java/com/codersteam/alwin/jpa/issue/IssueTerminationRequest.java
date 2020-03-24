package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.IssueTerminationRequestState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Encja do przechowywania żądań przedterminowego zakończenia zlecenia
 *
 * @author Piotr Naroznik
 */
@Entity
@Table(name = "issue_termination_request")
public class IssueTerminationRequest {

    @SequenceGenerator(name = "issueTerminationRequestSEQ", sequenceName = "issue_termination_request_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueTerminationRequestSEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Column(name = "request_cause", length = 1000, nullable = false)
    private String requestCause;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_operator_id", referencedColumnName = "id", nullable = false)
    private Operator requestOperator;

    @Column(name = "excluded_from_stats", nullable = false)
    private Boolean excludedFromStats;

    @Column(name = "exclusion_from_stats_cause", length = 500)
    private String exclusionFromStatsCause;

    @Column(name = "state", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private IssueTerminationRequestState state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_operator_id", referencedColumnName = "id")
    private Operator managerOperator;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "updated", nullable = false)
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(final Issue issue) {
        this.issue = issue;
    }

    public String getRequestCause() {
        return requestCause;
    }

    public void setRequestCause(final String requestCause) {
        this.requestCause = requestCause;
    }

    public Operator getRequestOperator() {
        return requestOperator;
    }

    public void setRequestOperator(final Operator requestOperator) {
        this.requestOperator = requestOperator;
    }

    public Boolean getExcludedFromStats() {
        return excludedFromStats;
    }

    public void setExcludedFromStats(final Boolean excludedFromStats) {
        this.excludedFromStats = excludedFromStats;
    }

    public String getExclusionFromStatsCause() {
        return exclusionFromStatsCause;
    }

    public void setExclusionFromStatsCause(final String exclusionFromStatsCause) {
        this.exclusionFromStatsCause = exclusionFromStatsCause;
    }

    public IssueTerminationRequestState getState() {
        return state;
    }

    public void setState(final IssueTerminationRequestState state) {
        this.state = state;
    }

    public Operator getManagerOperator() {
        return managerOperator;
    }

    public void setManagerOperator(final Operator managerOperator) {
        this.managerOperator = managerOperator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "IssueTerminationRequest{" +
                "id=" + id +
                ", issue=" + issue +
                ", requestCause='" + requestCause + '\'' +
                ", requestOperator=" + requestOperator +
                ", excludedFromStats=" + excludedFromStats +
                ", exclusionFromStatsCause='" + exclusionFromStatsCause + '\'' +
                ", state=" + state +
                ", managerOperator=" + managerOperator +
                ", comment='" + comment + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
