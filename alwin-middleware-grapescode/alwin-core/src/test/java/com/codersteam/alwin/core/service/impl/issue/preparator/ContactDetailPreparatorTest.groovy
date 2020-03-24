package com.codersteam.alwin.core.service.impl.issue.preparator

import com.codersteam.alwin.testdata.aida.AidaCompanyTestData
import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.ContactDetailTestData.allAidaCompany10ContactDetails
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class ContactDetailPreparatorTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = ContactDetailPreparator.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should prepare contact details"() {
        when:
            def contactDetails = ContactDetailPreparator.prepareContactDetails(AidaCompanyTestData.aidaCompanyDto10())
        then:
            assertThat(contactDetails).isEqualToComparingFieldByFieldRecursively(allAidaCompany10ContactDetails())
    }
}
