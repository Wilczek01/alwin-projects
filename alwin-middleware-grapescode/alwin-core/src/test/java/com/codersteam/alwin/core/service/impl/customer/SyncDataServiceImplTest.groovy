package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.PersonDao
import com.codersteam.alwin.jpa.customer.Company
import com.codersteam.alwin.jpa.customer.Person
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.CompanyPersonTestData.companyPerson1
import static com.codersteam.alwin.testdata.CompanyPersonTestData.companyPerson2
import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.PersonTestData.person2
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.*
import static com.codersteam.alwin.testdata.aida.AidaPersonTestData.*

class SyncDataServiceImplTest extends Specification {

    @Subject
    private SyncDataServiceImpl service

    def mapper = new AlwinMapper()
    def companyDao = Mock(CompanyDao)
    def personDao = Mock(PersonDao)

    def "setup"() {
        service = new SyncDataServiceImpl(companyDao, mapper, personDao)
    }

    def "should not update company if company wasn't imported before"() {
        given:
            companyDao.findCompanyByExtId(COMPANY_ID_10) >> Optional.empty()
        when:
            service.updateCompanyIfExist(aidaCompanyDto10(), null, COMPANY_ID_10)
        then:
            0 * companyDao.update(_ as Company)
    }

    def "should not update company person if company wasn't imported before"() {
        given:
            companyDao.findCompanyByExtId(EXT_COMPANY_ID_1) >> Optional.empty()
        when:
            service.updateCompanyIfExist(null, [aidaPerson1()], EXT_COMPANY_ID_1)
        then:
            0 * companyDao.update(_ as Company)
    }

    def "should update company"() {
        given:
            companyDao.findCompanyByExtId(EXT_COMPANY_ID_1) >> Optional.of(companyFromAida1())
        and:
            personDao.findByExtPersonIdAndExtCompanyId(PERSON_ID_3, EXT_COMPANY_ID_1) >> Optional.empty()
            personDao.findByExtPersonIdAndExtCompanyId(PERSON_ID_2, EXT_COMPANY_ID_1) >> Optional.of(person2())
        when:
            service.updateCompanyIfExist(aidaCompanyDto1(), [aidaPerson2(), aidaPerson3()], EXT_COMPANY_ID_1)
        then:
            1 * personDao.create(_ as Person)
            1 * personDao.update(_ as Person)
            1 * companyDao.update(_ as Company)
    }

    def "should update persons"() {
        given:
            def companyPersons = [companyPerson1(), companyPerson2()]

        and:
            def aidaPersons = [aidaPerson1()]

        and:
            def company = company1()

        when:
            service.updatePersons(companyPersons, aidaPersons, company)

        then:
            0 * personDao.create(_ as Person)
            1 * personDao.update(_ as Person)
    }

    def "should update persons ignoring duplicates"() {
        given:
            def companyPersons = [companyPerson1(), companyPerson2(), companyPerson1()]

        and:
            def aidaPersons = [aidaPerson1()]

        and:
            def company = company1()

        when:
            service.updatePersons(companyPersons, aidaPersons, company)

        then:
            0 * personDao.create(_ as Person)
            1 * personDao.update(_ as Person)
    }
}