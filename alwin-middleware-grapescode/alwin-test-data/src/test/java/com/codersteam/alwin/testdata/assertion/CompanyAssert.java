package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.Company;
import com.codersteam.alwin.jpa.customer.CompanyPerson;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.customer.Person;
import org.assertj.core.api.Assertions;

import java.util.Collection;
import java.util.List;

import static com.codersteam.alwin.testdata.assertion.PersonAssert.assertPersonsWithoutOrder;
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CompanyAssert {

    private static final String[] COMPANY_DATES = {"externalDBAgreementDate", "ratingDate"};
    private static final String[] SKIP_FIELDS = {"companyPersons", "contactDetails", "addresses"};

    /**
     * Porównuje firmy używająć komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualne firma
     * @param expected - spodziewane firma
     */
    public static void assertEquals(final Company actual, final Company expected) {
        assertThat(actual).usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, COMPANY_DATES).isEqualToComparingFieldByFieldRecursively(expected);
    }

    /**
     * Porównuje firmy używająć komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp.
     * Pomijaja kolejność adresów, kontaków i osób
     *
     * @param actual   - aktualne firma
     * @param expected - spodziewane firma
     */
    public static void assertEqualsWithoutOrder(final Company actual, final Company expected) {
        assertCompany(actual, expected);
        assertPersons(getPersons(actual), getPersons(expected));
        assertAddresses(actual.getAddresses(), expected.getAddresses());
        assertContactDetails(actual.getContactDetails(), expected.getContactDetails());
    }

    private static List<Person> getPersons(final Company actual) {
        return actual.getCompanyPersons().stream().map(CompanyPerson::getPerson).collect(toList());
    }

    public static void assertCompany(final Company actual, final Company expected) {
        assertThat(actual).usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, COMPANY_DATES)
                .usingComparatorForFields(SKIP_COMPARATOR, SKIP_FIELDS)
                .isEqualToComparingFieldByFieldRecursively(expected);
    }

    private static void assertPersons(final Collection<Person> actual, final Collection<Person> expected) {
        assertPersonsWithoutOrder(actual, expected);
    }

    private static void assertAddresses(final Collection<Address> actual, final Collection<Address> expected) {
        final Address[] expectedAddresses = expected.toArray(new Address[expected.size()]);
        Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedAddresses);
    }

    private static void assertContactDetails(final Collection<ContactDetail> actual, final Collection<ContactDetail> expected) {
        final ContactDetail[] expectedContacts = expected.toArray(new ContactDetail[expected.size()]);
        Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedContacts);
    }
}
