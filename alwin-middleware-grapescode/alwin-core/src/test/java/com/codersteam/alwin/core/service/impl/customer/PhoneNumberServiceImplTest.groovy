package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.alwin.db.dao.CompanyDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.SuggestedPhoneNumbersTestData.suggestedPhoneNumbers1
import static com.codersteam.alwin.testdata.SuggestedPhoneNumbersTestData.suggestedPhoneNumbers2
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class PhoneNumberServiceImplTest extends Specification {

    @Subject
    private PhoneNumberServiceImpl service

    private CompanyDao companyDao = Mock(CompanyDao)

    def "setup"() {
        service = new PhoneNumberServiceImpl(companyDao)
    }

    def "should find suggested phone numbers for company and company persons"() {
        given:
            companyDao.get(COMPANY_ID_2) >> Optional.of(company2())

        when:
            def suggestedPhoneNumbers = service.findSuggestedPhoneNumbers(COMPANY_ID_2)

        then:
            assertThat(suggestedPhoneNumbers).isEqualToComparingFieldByFieldRecursively(suggestedPhoneNumbers1())
    }

    def "should find suggested phone numbers for company persons"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())

        when:
            def suggestedPhoneNumbers = service.findSuggestedPhoneNumbers(COMPANY_ID_1)

        then:
            assertThat(suggestedPhoneNumbers).isEqualToComparingFieldByFieldRecursively(suggestedPhoneNumbers2())
    }


    def "should not find any suggested phone numbers"() {
        given:
            companyDao.get(COMPANY_ID_3) >> Optional.of(company3())

        when:
            def suggestedPhoneNumbers = service.findSuggestedPhoneNumbers(COMPANY_ID_3)

        then:
            suggestedPhoneNumbers.isEmpty()
    }
}