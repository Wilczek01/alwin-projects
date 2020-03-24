package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.CompanyPersonDao
import com.codersteam.alwin.db.dao.PersonDao
import com.codersteam.alwin.jpa.customer.CompanyPerson
import com.codersteam.alwin.jpa.customer.Person
import com.codersteam.alwin.testdata.assertion.CompanyPersonAssert
import com.codersteam.alwin.testdata.assertion.PersonAssert
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyPersonTestData.companyPerson1
import static com.codersteam.alwin.testdata.CompanyPersonTestData.createCompanyPerson
import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.PersonTestData.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Piotr Naroznik
 */
class PersonServiceImplTest extends Specification {

    @Subject
    private PersonServiceImpl service

    private CompanyPersonDao companyPersonDao = Mock(CompanyPersonDao)
    private CompanyDao companyDao = Mock(CompanyDao)
    private PersonDao personDao = Mock(PersonDao)
    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)
    private AlwinMapper mapper = new AlwinMapper(dateProvider, alwinProperties)

    def "setup"() {
        service = new PersonServiceImpl(companyPersonDao, companyDao, personDao, mapper)
    }

    def "should set contact person"() {
        given:
            1 * companyPersonDao.findByPersonIdAndCompanyId(PERSON_ID_1, COMPANY_ID_2) >> Optional.of(companyPerson1())
        when:
            service.setContactPerson(PERSON_ID_1, COMPANY_ID_2, true)
        then:
            1 * companyPersonDao.update(_ as CompanyPerson) >> {
                List args ->
                    def companyPerson = (CompanyPerson) args[0]
                    assert companyPerson.contactPerson
                    companyPerson
            }
    }

    def "should throw exception when company person not exists in set contact person"() {
        given:
            1 * companyPersonDao.findByPersonIdAndCompanyId(PERSON_ID_1, COMPANY_ID_2) >> Optional.empty()
        when:
            service.setContactPerson(PERSON_ID_1, COMPANY_ID_2, true)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == 'CompanyPersonId[personId= 1, companyId= 2]'
    }

    def "should throw exception when company not exists in create person"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.empty()
        when:
            service.createPerson(COMPANY_ID_1, createPersonDto())
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMPANY_ID_1
    }

    def "should create person"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())
        when:
            service.createPerson(COMPANY_ID_1, createPersonDto())
        then:
            1 * personDao.create(_ as Person) >> {
                List args ->
                    def person = (Person) args[0]
                    PersonAssert.assertEquals(person, createPerson())
                    person
            }
            1 * companyPersonDao.create(_ as CompanyPerson) >> {
                List args ->
                    def companyPerson = (CompanyPerson) args[0]
                    CompanyPersonAssert.assertEquals(companyPerson, createCompanyPerson())
                    companyPerson
            }
    }

    def "should get persons by companyId"() {
        given:
            companyDao.get(COMPANY_ID_2) >> Optional.of(company2())
        when:
            def persons = service.getCompanyPersonsByCompanyId(COMPANY_ID_2)
        then:
            assertThat(persons).isEqualToComparingFieldByFieldRecursively(testPersonDtosList())
    }

    def "should not get persons by companyId"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())
        when:
            def persons = service.getCompanyPersonsByCompanyId(COMPANY_ID_1)
        then:
            persons == []
    }

    def "should throw exception when company not exists"() {
        given:
            companyDao.get(NON_EXISTING_COMPANY_ID) >> Optional.empty()
        when:
            service.getCompanyPersonsByCompanyId(NON_EXISTING_COMPANY_ID)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NON_EXISTING_COMPANY_ID
    }

}
