package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.issue.Invoice;
import org.assertj.core.api.AssertionsForClassTypes;

import java.util.List;

import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;

/**
 * @author Adam Stepnowski
 */
public class InvoiceAssert {
    private static final String[] ISSUE_DATES = {"lastPaymentDate", "realDueDate", "corrections.lastPaymentDate", "corrections.realDueDate", "issueDate"};

    /**
     * Porównuje listę faktur, powmijajać zagłębione kolekcje danych
     * Używa komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualna lista faktur
     * @param expected - spodziewana lista faktur
     */
    public static void assertInvoiceListEquals(final List<Invoice> actual, final List<Invoice> expected) {
        assertEquals(actual, expected);
    }

    private static void assertEquals(final Object actual, final Object expected) {
        AssertionsForClassTypes.assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ISSUE_DATES)
                .usingComparatorForFields(SKIP_COMPARATOR, "issueInvoices")
                .isEqualToComparingFieldByFieldRecursively(expected);
    }

}
