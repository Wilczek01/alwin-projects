package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.AddressDao
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.PersonDao
import com.codersteam.alwin.jpa.customer.Address
import com.codersteam.alwin.jpa.customer.Company
import com.codersteam.alwin.jpa.customer.Person
import com.codersteam.alwin.testdata.PersonTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AddressTestData.*
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.company1
import static com.codersteam.alwin.testdata.PersonTestData.person1
import static org.assertj.core.api.Assertions.assertThat

class AddressServiceImplTest extends Specification {

    @Subject
    private AddressServiceImpl service

    private PersonDao personDao = Mock(PersonDao)
    private AddressDao addressDao = Mock(AddressDao)
    private CompanyDao companyDao = Mock(CompanyDao)
    private alwinMapper = new AlwinMapper()

    def "setup"() {
        service = new AddressServiceImpl(personDao, alwinMapper, addressDao, companyDao)
    }

    def "should find all addresses for company"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())

        when:
            def addresses = service.findAllAddressesForCompany(COMPANY_ID_1)

        then:
            addresses.size() == 2
    }

    def "should not find addresses for person"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.empty()

        when:
            def addresses = service.findAllAddressesForPerson(COMPANY_ID_1)

        then:
            addresses.size() == 0
    }

    def "should create new address for person"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.of(person1())
        when:
            service.createNewAddressForPerson(COMPANY_ID_1, addressDto1())
        then:
            1 * personDao.update(_ as Person)
    }

    def "should not create address if person does not exit"() {
        given:
            personDao.get(COMPANY_ID_1) >> Optional.empty()
        when:
            service.createNewAddressForPerson(COMPANY_ID_1, addressDto1())
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMPANY_ID_1
            0 * personDao.update(_ as Person)
    }

    def "should find all addresses for person"() {
        given:
            personDao.get(PersonTestData.PERSON_ID_1) >> Optional.of(person1())

        when:
            def addresses = service.findAllAddressesForPerson(PersonTestData.PERSON_ID_1)

        then:
            addresses.size() == 2
    }

    def "should not find addresses for company"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.empty()

        when:
            def addresses = service.findAllAddressesForCompany(COMPANY_ID_1)

        then:
            addresses.size() == 0
    }

    def "should create new address for company"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.of(company1())
        when:
            service.createNewAddressForCompany(COMPANY_ID_1, addressDto1())
        then:
            1 * companyDao.update(_ as Company)
    }

    def "should not create address if company does not exit"() {
        given:
            companyDao.get(COMPANY_ID_1) >> Optional.empty()
        when:
            service.createNewAddressForCompany(COMPANY_ID_1, addressDto1())
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == COMPANY_ID_1
            0 * companyDao.update(_ as Company)
    }

    def "should update address"() {
        given:
            addressDao.get(ADDRESS_ID_1) >> Optional.of(address1())
        when:
            service.updateAddress(addressDto1())
        then:
            1 * addressDao.update(_ as Address)
    }

    def "should not update address if address not exist"() {
        given:
            addressDao.get(ADDRESS_ID_1) >> Optional.empty()
        when:
            service.updateAddress(addressDto1())
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == ADDRESS_ID_1
            0 * addressDao.update(_ as Address)
    }

    def "should not return address if not exists"() {
        given:
            addressDao.get(ADDRESS_ID_1) >> Optional.empty()
        when:
            service.findAddress(ADDRESS_ID_1)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == ADDRESS_ID_1
    }

    def "should return address"() {
        given:
            addressDao.get(ADDRESS_ID_1) >> Optional.of(address1())
        when:
            def address = service.findAddress(ADDRESS_ID_1)
        then:
            assertThat(address).isEqualToComparingFieldByField(addressDto1())
    }

}
