package com.codersteam.alwin.core.api.service.issue;

/**
 * @author Tomasz Sliwinski
 */
public class IssueCreateResult {

    private final Long issueId;

    public IssueCreateResult(final Long issueId) {
        this.issueId = issueId;
    }

    public Long getIssueId() {
        return issueId;
    }
}
