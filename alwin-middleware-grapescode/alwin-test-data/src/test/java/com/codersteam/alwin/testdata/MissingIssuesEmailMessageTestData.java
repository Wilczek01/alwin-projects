package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.email.MissingIssuesEmailMessage;

import static com.codersteam.alwin.testdata.EmailTestData.MISSING_ISSUES_MANAGER_EMAILS;
import static com.codersteam.alwin.testdata.MissingIssueTestData.MISSING_ISSUE_DTOS;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;

/**
 * @author Tomasz Sliwinski
 */
public final class MissingIssuesEmailMessageTestData {

    private static final String DATE_FROM = "2017-08-11";
    private static final String DATE_TO = "2017-09-12";

    public static final MissingIssuesEmailMessage MISSING_ISSUES_EMAIL_MESSAGE = new MissingIssuesEmailMessage(MISSING_ISSUES_MANAGER_EMAILS,
            parse(DATE_FROM), parse(DATE_TO), MISSING_ISSUE_DTOS);

    private MissingIssuesEmailMessageTestData() {
    }
}
