package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.issue.IssueTerminationRequest;

import static com.codersteam.alwin.testdata.assertion.IssueAssert.assertIssueEquals;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestAssert {

    private static final String[] SKIP_FIELDS = {"issue", "requestOperator", "managerOperator", "contract.customer.accountManager.user.operators"};
    private static final String[] DATES_FIELDS = {"contract.customer.accountManager.user.updateDate", "contract.customer.accountManager.user.created", "updated",
            "created"};

    public static void assertEquals(final IssueTerminationRequest actual, final IssueTerminationRequest expected) {
        assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, DATES_FIELDS)
                .usingComparatorForFields(SKIP_COMPARATOR, SKIP_FIELDS)
                .isEqualToComparingFieldByFieldRecursively(expected);
        assertIssueEquals(actual.getIssue(), expected.getIssue());
        OperatorAssert.assertEquals(actual.getRequestOperator(), expected.getRequestOperator());
        OperatorAssert.assertEquals(actual.getManagerOperator(), expected.getManagerOperator());
    }
}