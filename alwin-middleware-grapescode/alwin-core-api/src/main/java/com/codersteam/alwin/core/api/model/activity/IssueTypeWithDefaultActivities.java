package com.codersteam.alwin.core.api.model.activity;

import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;

import java.util.List;

/**
 * @author Dariusz Rackowski
 */
public class IssueTypeWithDefaultActivities {

    private IssueTypeDto issueType;

    private List<DefaultIssueActivityDto> defaultActivities;

    public IssueTypeWithDefaultActivities() {
    }

    public IssueTypeWithDefaultActivities(final IssueTypeDto issueType, final List<DefaultIssueActivityDto> defaultActivities) {
        this.issueType = issueType;
        this.defaultActivities = defaultActivities;
    }

    public IssueTypeDto getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueTypeDto issueType) {
        this.issueType = issueType;
    }

    public List<DefaultIssueActivityDto> getDefaultActivities() {
        return defaultActivities;
    }

    public void setDefaultActivities(final List<DefaultIssueActivityDto> defaultActivities) {
        this.defaultActivities = defaultActivities;
    }
}
