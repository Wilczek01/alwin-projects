package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.jpa.issue.Issue;
import org.assertj.core.api.AssertionsForClassTypes;

import java.util.List;

import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;

public class IssueAssert {

    private static final String[] ISSUE_DATES = {"expirationDate", "startDate", "assignee.user.updateDate", "assignee.user.creationDate",
            "customer.company.externalDBAgreementDate", "customer.company.ratingDate", "customer.person.birthDate", "customer.person.idDocSignDate",
            "contract.customer.person.birthDate", "contract.customer.person.idDocSignDate", "contract.customer.company.externalDBAgreementDate",
            "contract.customer.company.ratingDate", "balanceUpdateDate", "invoices.lastPaymentDate", "invoices.realDueDate", "terminationDate",
            "terminationOperator.user.updateDate", "terminationOperator.user.creationDate", "contract.customer.accountManager.user.updateDate",
            "contract.customer.accountManager.user.creationDate", "assignee.parentOperator.user.updateDate", "assignee.parentOperator.user.creationDate",
            "dpdStartDate"};

    private static final String[] SKIP_FIELDS = {"customer.company.companyPersons", "customer.company.addresses", "customer.company.contactDetails", "contract" +
            ".customer.company.addresses", "contract.customer.company.contactDetails", "activities", "assignee.user.operators", "parentIssue",
            "terminationOperator.user.operators", "invoices.issue", "issueInvoices", "contract.customer.accountManager.user.operators", "assignee.parentOperator.user.operators"};

    /**
     * Porównuje listę zleceń, pomijając zagnieżdzeone kolekcje danych w firmie i kliencie
     * Używa komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualna lista zleceń
     * @param expected - spodziewana lista zleceń
     */
    public static void assertIssueListEquals(final List<Issue> actual, final List<Issue> expected) {
        assertEquals(actual, expected);
    }

    /**
     * Porównuje zlecenia, pomijając zagnieżdzeone kolekcje danych w firmie i kliencie
     * Używa komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualne zlecenie
     * @param expected - spodziewane zlecenie
     */
    public static void assertIssueEquals(final Issue actual, final Issue expected) {
        assertEquals(actual, expected);
    }

    /**
     * Porównuje zlecenia, pomijając zagnieżdzeone kolekcje danych w firmie i kliencie
     * Używa komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualne zlecenie
     * @param expected - spodziewane zlecenie
     */
    public static void assertIssueDtoEquals(final IssueDto actual, final IssueDto expected) {
        assertEquals(actual, expected);
    }

    private static void assertEquals(final Object actual, final Object expected) {
        AssertionsForClassTypes.assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, ISSUE_DATES)
                .usingComparatorForFields(SKIP_COMPARATOR, SKIP_FIELDS)
                .isEqualToComparingFieldByFieldRecursively(expected);
    }
}
