package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;

import static com.codersteam.alwin.testdata.EmailTestData.UNRESOLVED_ISSUES_MANAGER_EMAILS;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.UnresolvedIssueTestData.unresolvedIssueDtos;

/**
 * @author Piotr Naroznik
 */
public final class UnresolvedIssuesEmailMessageTestData {

    private static final String DATE_FROM = "2017-08-11 01:12:23.000000";
    private static final String DATE_TO = "2017-09-12 01:23:34.000000";

    public static final UnresolvedIssuesEmailMessage UNRESOLVED_ISSUES_EMAIL_MESSAGE = new UnresolvedIssuesEmailMessage(UNRESOLVED_ISSUES_MANAGER_EMAILS,
            parse(DATE_FROM), parse(DATE_TO), unresolvedIssueDtos());

    private UnresolvedIssuesEmailMessageTestData() {
    }
}
