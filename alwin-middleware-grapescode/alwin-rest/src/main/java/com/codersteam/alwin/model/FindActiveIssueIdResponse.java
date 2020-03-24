package com.codersteam.alwin.model;

import io.swagger.annotations.ApiModel;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("unused")
@ApiModel("Komunikat odpowiedzi na pobranie identyfikatora aktywnego zlecenia dla firmy")
public class FindActiveIssueIdResponse {

    private final Long issueId;

    public FindActiveIssueIdResponse(final Long issueId) {
        this.issueId = issueId;
    }

    public Long getIssueId() {
        return issueId;
    }
}
