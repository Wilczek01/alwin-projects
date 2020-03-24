package com.codersteam.alwin.testdata.assertion;

import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.customer.ContactDetail;
import com.codersteam.alwin.jpa.customer.Person;
import org.assertj.core.api.Assertions;

import java.util.Collection;
import java.util.Map;

import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR;
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PersonAssert {

    private static final String[] PERSON_DATES = {"birthDate", "idDocSignDate"};
    private static final String[] SKIP_FIELDS = {"contactDetails", "addresses"};

    /**
     * Porównuje osoby używająć komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     *
     * @param actual   - aktualne firma
     * @param expected - spodziewane firma
     */
    public static void assertEquals(final Person actual, final Person expected) {
        assertThat(actual).usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, PERSON_DATES).isEqualToComparingFieldByFieldRecursively(expected);
    }

    /**
     * Porównuje listę osób używająć komparatora pozwlającego porównywać java.util.Date z java.sql.Timestamp
     * Nie sprawdza kolejność adresów i kontaktów
     *
     * @param actual   - aktualne firma
     * @param expected - spodziewane firma
     */
    public static void assertPersonsWithoutOrder(final Collection<Person> actual, final Collection<Person> expected) {
        final Map<Long, Person> actualMap = actual.stream().collect(toMap(Person::getId, identity()));
        final Map<Long, Person> expectedMap = expected.stream().collect(toMap(Person::getId, identity()));
        for (final Long id : expectedMap.keySet()) {
            assertPersonWithoutOrder(actualMap.get(id), expectedMap.get(id));
        }
    }

    private static void assertPersonWithoutOrder(final Person actual, final Person expected) {
        assertPerson(actual, expected);
        assertAddresses(actual.getAddresses(), expected.getAddresses());
        assertContactDetails(actual.getContactDetails(), expected.getContactDetails());
    }

    public static void assertPerson(final Person actual, final Person expected) {
        assertThat(actual)
                .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, PERSON_DATES)
                .usingComparatorForFields(SKIP_COMPARATOR, SKIP_FIELDS)
                .isEqualToComparingFieldByFieldRecursively(expected);
    }

    private static void assertAddresses(final Collection<Address> actual, final Collection<Address> expected) {
        if (actual == null && expected == null) {
            return;
        }
        final Address[] expectedAddresses = expected.toArray(new Address[expected.size()]);
        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedAddresses);
    }

    private static void assertContactDetails(final Collection<ContactDetail> actual, final Collection<ContactDetail> expected) {
        if (actual == null && expected == null) {
            return;
        }
        final Map<Long, ContactDetail> actualMap = actual.stream().collect(toMap(ContactDetail::getId, identity()));
        final Map<Long, ContactDetail> expectedMap = expected.stream().collect(toMap(ContactDetail::getId, identity()));
        for (final Long id : expectedMap.keySet()) {
            Assertions.assertThat(actualMap.get(id)).isEqualToComparingFieldByField(expectedMap.get(id));
        }


    }

}
