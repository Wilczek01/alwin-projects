package com.codersteam.alwin.testdata;

import java.util.Arrays;
import java.util.List;

import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_EMAIL_2;
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_EMAIL_4;

/**
 * @author Tomasz Sliwinski
 */
public class EmailTestData {

    public static final String EMAIL_FROM = "alwin";
    public static final List<String> MISSING_ISSUES_MANAGER_EMAILS = Arrays.asList(TEST_USER_EMAIL_2, TEST_USER_EMAIL_4);
    public static final List<String> UNRESOLVED_ISSUES_MANAGER_EMAILS = Arrays.asList(TEST_USER_EMAIL_2, TEST_USER_EMAIL_4);
    public static final String MISSING_ISSUES_MAIL_BODY = "Missing issues email body";

}
