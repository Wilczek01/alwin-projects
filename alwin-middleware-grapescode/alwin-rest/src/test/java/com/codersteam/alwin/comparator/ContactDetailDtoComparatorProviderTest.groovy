package com.codersteam.alwin.comparator

import spock.lang.Specification

import static com.codersteam.alwin.comparator.ContactDetailDtoComparatorProvider.CONTACT_DETAIL_DTO_COMPARATOR
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetailDtosSorted
import static com.codersteam.alwin.testdata.ContactDetailTestData.contactDetailDtosUnsorted
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class ContactDetailDtoComparatorProviderTest extends Specification {

    def "should order contact details"() {
        given:
            def contactDetailsUnsorted = contactDetailDtosUnsorted()
        when:
            contactDetailsUnsorted.sort(CONTACT_DETAIL_DTO_COMPARATOR)
        then:
            assertThat(contactDetailsUnsorted).isEqualToComparingFieldByFieldRecursively(contactDetailDtosSorted())
    }
}
