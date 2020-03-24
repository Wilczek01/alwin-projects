package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestDto;

import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Piotr Naroznik
 */
public class IssueTerminationRequestDtoAssert {

    private static final String[] SKIP_FIELDS = {"requestOperator", "managerOperator"};
    private static final String[] DATES_FIELDS = {"updated", "created"};

    public static void assertEquals(final IssueTerminationRequestDto actual, final IssueTerminationRequestDto expected) {
        assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, DATES_FIELDS)
                .usingComparatorForFields(SKIP_COMPARATOR, SKIP_FIELDS)
                .isEqualToComparingFieldByFieldRecursively(expected);
        OperatorAssert.assertEquals(actual.getRequestOperator(), expected.getRequestOperator());
        OperatorAssert.assertEquals(actual.getManagerOperator(), expected.getManagerOperator());
    }
}