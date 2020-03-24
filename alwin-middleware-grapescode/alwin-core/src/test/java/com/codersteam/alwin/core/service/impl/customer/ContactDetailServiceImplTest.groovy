package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.ContactDetailDao
import com.codersteam.alwin.db.dao.PersonDao
import com.codersteam.alwin.jpa.customer.Company
import com.codersteam.alwin.jpa.customer.ContactDetail
import com.codersteam.alwin.jpa.customer.Person
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.company1
import static com.codersteam.alwin.testdata.ContactDetailTestData.*
import static com.codersteam.alwin.testdata.PersonTestData.person1

class ContactDetailServiceImplTest extends Specification {

    @Subject
    private ContactDetailServiceImpl service

    private ContactDetailDao contactDetailDao = Mock(ContactDetailDao)
    private CompanyDao companyDao = Mock(CompanyDao)
    private PersonDao personDao = Mock(PersonDao)
    private alwinMapper = new AlwinMapper()

    def "setup"() {
        service = new ContactDetailServiceImpl(personDao, alwinMapper, contactDetailDao, companyDao)
    }

    def "should find all contact details for company"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())

        when:
            def contactDetails = service.findAllContactDetailsForCompany(COMPANY_ID_1)

        then:
            contactDetails.size() == 13
    }

    def "should not find contact details for company"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.empty()

        when:
            def contactDetails = service.findAllContactDetailsForCompany(COMPANY_ID_1)

        then:
            contactDetails.size() == 0
    }

    def "should create new contact detail for company"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())
        when:
            service.createNewContactDetailForCompany(COMPANY_ID_1, CONTACT_DETAIL_DTO_1)
        then:
            1 * companyDao.update(_ as Company)
    }

    def "should not create contact detail if company does not exit"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.empty()
        when:
            service.createNewContactDetailForCompany(COMPANY_ID_1, CONTACT_DETAIL_DTO_1)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMPANY_ID_1
            0 * companyDao.update(_ as Company)
    }

    def "should find all contact details for person"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.of(person1())

        when:
            def contactDetails = service.findAllContactDetailsForPerson(COMPANY_ID_1)

        then:
            contactDetails.size() == 13
    }

    def "should not find contact details for person"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.empty()

        when:
            def contactDetails = service.findAllContactDetailsForPerson(COMPANY_ID_1)

        then:
            contactDetails.size() == 0
    }

    def "should create new contact detail for person"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.of(person1())
        when:
            service.createNewContactDetailForPerson(COMPANY_ID_1, CONTACT_DETAIL_DTO_1)
        then:
            1 * personDao.update(_ as Person)
    }

    def "should not create contact detail if person does not exit"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.empty()
        when:
            service.createNewContactDetailForPerson(COMPANY_ID_1, CONTACT_DETAIL_DTO_1)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMPANY_ID_1
            0 * personDao.update(_ as Person)
    }

    def "should update contact detail"() {
        given:
            contactDetailDao.get(CONTACT_DETAIL_ID_1) >> Optional.of(contactDetail1())
        when:
            service.updateContactDetail(CONTACT_DETAIL_DTO_1)
        then:
            1 * contactDetailDao.update(_ as ContactDetail) >> {
                List args ->
                    ContactDetail contactDetail = (ContactDetail) args[0]

                    // import data should not be overwritten
                    assert contactDetail.importedFromAida == contactDetail1().importedFromAida
                    assert contactDetail.importedType == contactDetail1().importedType
            }
    }

    def "should not update contact detail if contact detail not exist"() {
        given:
            contactDetailDao.get(CONTACT_DETAIL_ID_1) >> Optional.empty()
        when:
            service.updateContactDetail(CONTACT_DETAIL_DTO_1)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == CONTACT_DETAIL_ID_1
        and:
            0 * contactDetailDao.update(_ as ContactDetail)
    }
}
