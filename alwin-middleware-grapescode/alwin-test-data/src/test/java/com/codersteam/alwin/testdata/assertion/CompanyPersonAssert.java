package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.customer.CompanyPerson;

import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CompanyPersonAssert {

    private static final String[] DATE_FIELDS = {"person.birthDate", "person.idDocSignDate", "company.externalDBAgreementDate", "company.ratingDate"};
    private static final String[] SKIP_FIELDS = {"person.contactDetails", "person.addresses", "company.companyPersons", "company.contactDetails", "company.addresses"};

    /**
     * Porównuje osoby firm używająć komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualne osoby firmy
     * @param expected - spodziewane osoby firmy
     */
    public static void assertEquals(final CompanyPerson actual, final CompanyPerson expected) {
        assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, DATE_FIELDS)
                .usingComparatorForFields(SKIP_COMPARATOR, SKIP_FIELDS)
                .isEqualToComparingFieldByFieldRecursively(expected);
    }
}