package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.operator.Operator;
import org.assertj.core.api.AssertionsForClassTypes;

import java.util.Comparator;
import java.util.List;

import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;

public class OperatorAssert {

    private static final String[] DATES = {"user.creationDate", "user.updateDate", "parentOperator.user.creationDate", "parentOperator.user.updateDate"};

    public static void assertEquals(final Object actual, final Object expected) {
        if (actual == null && expected == null) {
            return;
        }
        AssertionsForClassTypes.assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, DATES)
                .usingComparatorForFields((Comparator<List<Operator>>) (o1, o2) -> 0, "user.operators")
                .usingComparatorForFields((Comparator<List<Operator>>) (o1, o2) -> 0, "parentOperator.user.operators")
                .isEqualToComparingFieldByFieldRecursively(expected);

    }
}
