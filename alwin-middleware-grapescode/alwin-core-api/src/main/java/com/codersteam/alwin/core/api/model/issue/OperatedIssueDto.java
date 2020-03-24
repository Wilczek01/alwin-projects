package com.codersteam.alwin.core.api.model.issue;

/**
 * @author Piotr Narożnik
 */
public class OperatedIssueDto {
    private final IssueDto issue;
    private final boolean editable;

    public OperatedIssueDto(final IssueDto issue, final boolean editable) {
        this.issue = issue;
        this.editable = editable;
    }

    public IssueDto getIssue() {
        return issue;
    }

    public boolean isEditable() {
        return editable;
    }

    @Override
    public String toString() {
        return "OperatedIssueDto{" +
                "issue=" + issue +
                ", editable=" + editable +
                '}';
    }
}
