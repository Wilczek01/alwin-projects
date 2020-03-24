package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;

import java.util.Date;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestDto {

    private Long id;
    private Long issueId;
    private String requestCause;
    private OperatorUserDto requestOperator;
    private Boolean excludedFromStats;
    private String exclusionFromStatsCause;
    private IssueTerminationRequestStateDto state;
    private OperatorUserDto managerOperator;
    private String comment;
    private Date created;
    private Date updated;

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

    public String getRequestCause() {
        return requestCause;
    }

    public void setRequestCause(final String requestCause) {
        this.requestCause = requestCause;
    }

    public OperatorUserDto getRequestOperator() {
        return requestOperator;
    }

    public void setRequestOperator(final OperatorUserDto requestOperator) {
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

    public IssueTerminationRequestStateDto getState() {
        return state;
    }

    public void setState(final IssueTerminationRequestStateDto state) {
        this.state = state;
    }

    public OperatorUserDto getManagerOperator() {
        return managerOperator;
    }

    public void setManagerOperator(final OperatorUserDto managerOperator) {
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
}