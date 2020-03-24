package com.codersteam.alwin.core.service.impl.customer

import com.codersteam.aida.core.api.service.CompanyService
import com.codersteam.aida.core.api.service.PersonService
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey
import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceInputDto
import com.codersteam.alwin.core.api.model.customer.CustomerOutOfServiceInputDto
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.customer.Customer
import com.codersteam.alwin.testdata.ContractOutOfServiceTestData
import org.assertj.core.api.AssertionsForClassTypes
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.FIRST
import static com.codersteam.alwin.common.demand.DemandForPaymentTypeKey.SECOND
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.activeContractsOutOfServiceForExtCompanyId1
import static com.codersteam.alwin.testdata.ContractOutOfServiceTestData.contractsOutOfServiceForExtCompanyId1Dto
import static com.codersteam.alwin.testdata.CustomerOutOfServiceTestData.*
import static com.codersteam.alwin.testdata.CustomerTestData.ID_1
import static com.codersteam.alwin.testdata.CustomerTestData.NON_EXISTING_ID
import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer1
import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_2
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static com.codersteam.alwin.testdata.assertion.TimestampToDateComparator.TIMESTAMP_TO_DATE_COMPARATOR
import static java.util.Collections.emptyList

class CustomerServiceImplTest extends Specification {

    @Subject
    private CustomerServiceImpl service

    private CustomerDao customerDao = Mock(CustomerDao)
    private CustomerOutOfServiceDao customerOutOfServiceDao = Mock(CustomerOutOfServiceDao)
    private mapper = new AlwinMapper()
    private dateProvider = Mock(DateProvider)
    private contractOutOfServiceDao = Mock(ContractOutOfServiceDao)
    private aidaService = Mock(AidaService)
    private aidaCompanyService = Mock(CompanyService)
    private aidaPersonService = Mock(PersonService)
    private PersonDao personDao = Mock(PersonDao)
    private CompanyDao companyDao = Mock(CompanyDao)
    private FormalDebtCollectionCustomerOutOfServiceDao formalDebtCollectionCustomerOutOfServiceDao = Mock(FormalDebtCollectionCustomerOutOfServiceDao)
    private FormalDebtCollectionContractOutOfServiceDao formalDebtCollectionContractOutOfServiceDao = Mock(FormalDebtCollectionContractOutOfServiceDao)

    def "setup"() {
        aidaService.getCompanyService() >> aidaCompanyService
        aidaService.getPersonService() >> aidaPersonService
        service = new CustomerServiceImpl(customerOutOfServiceDao, contractOutOfServiceDao, formalDebtCollectionCustomerOutOfServiceDao, formalDebtCollectionContractOutOfServiceDao,
                dateProvider, mapper,customerDao, aidaService, personDao, companyDao
                )
    }

    @Unroll
    def "customer #customerId is out of service #outOfServiceMessage for #operationDate"() {
        given:
            customerOutOfServiceDao.isCustomerOutOfService(customerId, operationDate) >> isOutOfService
        expect:
            service.isCustomerOutOfService(customerId, operationDate) == isOutOfService
        where:
            customerId | operationDate                       | isOutOfService
            1L         | parse("2017-07-11 00:00:00.000000") | true
            1L         | parse("2017-07-10 00:00:00.000000") | false
            2L         | parse("2017-07-10 00:00:00.000000") | false

            outOfServiceMessage = isOutOfService ? "out of service" : "in service"
    }

    def "should check that customer is in service for formal debt collection when customer not exist for given extCompanyId"() {
        given:
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.empty()

        expect:
            !service.isFormalDebtCollectionCustomerOutOfService(EXT_COMPANY_ID_1, parse("2017-07-11"), FIRST)
    }

    @Unroll
    def "should check if customer is in service for formal debt collection"(DemandForPaymentTypeKey demandForPaymentType, boolean customerOutOfService,
                                                                            boolean expectedOutOfService) {
        given:
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.of(testCustomer1())
        and:
            def investigationDate = parse("2017-07-11")
        and:
            formalDebtCollectionCustomerOutOfServiceDao.isCustomerOutOfFormalDebtCollection(ID_1, investigationDate, demandForPaymentType) >> customerOutOfService
        expect:
            service.isFormalDebtCollectionCustomerOutOfService(EXT_COMPANY_ID_1, investigationDate, demandForPaymentType) == expectedOutOfService
        where:
            demandForPaymentType | customerOutOfService | expectedOutOfService
            FIRST                | true                 | true
            FIRST                | false                | false
            SECOND               | true                 | true
            SECOND               | false                | false
    }

    def "should find customer by external company id"() {
        given: 'external customer id'
            def aidaCompanyId = 5

        and:
            def existingCustomer = new Customer()
            existingCustomer.setId(aidaCompanyId)

        and:
            customerDao.findCustomerByExternalCompanyId(aidaCompanyId) >> Optional.of(existingCustomer)

        when:
            def customer = service.findCustomerByExtId(aidaCompanyId)

        then:
            customer.isPresent()
    }

    def "should find active contracts out of service"() {
        given:
            final long extCompanyId = EXT_COMPANY_ID_1

        and:
            def currentDate = parse("2017-07-15")
            dateProvider.getCurrentDate() >> currentDate

        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, currentDate) >> activeContractsOutOfServiceForExtCompanyId1()

        when:
            def contractsOutOfService = service.findActiveContractsOutOfService(extCompanyId)

        then:
            AssertionsForClassTypes.assertThat(contractsOutOfService)
                    .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate", "blockingOperator.user.creationDate", "blockingOperator.user.updateDate")
                    .usingComparatorForFields(SKIP_COMPARATOR, "blockingOperator.user.operators")
                    .isEqualToComparingFieldByFieldRecursively(contractsOutOfServiceForExtCompanyId1Dto())
    }

    def "should find active contract out of service numbers"() {
        given:
            final long extCompanyId = EXT_COMPANY_ID_1

        and:
            def currentDate = parse("2017-07-15")
            dateProvider.getCurrentDate() >> currentDate

        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, currentDate) >> activeContractsOutOfServiceForExtCompanyId1()

        when:
            def contractsOutOfService = service.findActiveContractOutOfServiceNumbers(extCompanyId)

        then:
            contractsOutOfService.containsAll([CONTRACT_NO_1, CONTRACT_NO_2])
    }

    def "should not find active contract out of service numbers when company has none"() {
        given:
            final long extCompanyId = EXT_COMPANY_ID_1

        and:
            def currentDate = parse("2017-07-15")
            dateProvider.getCurrentDate() >> currentDate

        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, currentDate) >> []

        when:
            def contractsOutOfService = service.findActiveContractOutOfServiceNumbers(extCompanyId)

        then:
            contractsOutOfService.isEmpty()
    }

    def "should find active customers out of service"() {
        given:
            def extCompanyId = EXT_COMPANY_ID_1

        and:
            def today = parse("2017-07-16")
            dateProvider.getCurrentDate() >> today

        and:
            customerOutOfServiceDao.findCustomersOutOfService(extCompanyId, today) >> customersOutOfServiceForExtCompanyId1()

        when:
            def customersOutOfService = service.findCustomersOutOfService(extCompanyId, true)

        then:
            AssertionsForClassTypes.assertThat(customersOutOfService)
                    .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate")
                    .isEqualToComparingFieldByFieldRecursively(customersOutOfServiceForExtCompanyIdDto1())
    }

    def "should find all customers out of service"(boolean active) {
        given:
            def extCompanyId = EXT_COMPANY_ID_1

        and:
            def today = parse("2017-07-16")
            dateProvider.getCurrentDate() >> today

        and:
            customerOutOfServiceDao.findAllCustomersOutOfService(extCompanyId) >> customersOutOfServiceForExtCompanyId1()
            customerOutOfServiceDao.findCustomersOutOfService(extCompanyId, today) >> []

        when:
            def customersOutOfService = service.findCustomersOutOfService(extCompanyId, active)

        then:
            AssertionsForClassTypes.assertThat(customersOutOfService)
                    .usingComparatorForFields(TIMESTAMP_TO_DATE_COMPARATOR, "startDate", "endDate")
                    .isEqualToComparingFieldByFieldRecursively(customersOutOfServiceForExtCompanyIdDto1())

        where:
            active << [null, false]
    }

    def "should not find active contracts out of service"() {
        given:
            final long extCompanyId = EXT_COMPANY_ID_1

        and:
            def currentDate = parse("2017-07-15")
            dateProvider.getCurrentDate() >> currentDate

        and:
            contractOutOfServiceDao.findActiveContractsOutOfService(extCompanyId, currentDate) >> []

        when:
            def contractsOutOfService = service.findActiveContractsOutOfService(extCompanyId)

        then:
            contractsOutOfService.isEmpty()
    }

    def "should block existing customer"() {
        given:
            def customer = new CustomerOutOfServiceInputDto()

        and:
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.of(testCustomer1())

        when:
            service.blockCustomer(customer, EXT_COMPANY_ID_1, OPERATOR_ID_1)

        then:
            1 * customerOutOfServiceDao.create(*_) >> { arguments ->
                def customerOutOfService = arguments[0]
                assert customerOutOfService.customer.company.extCompanyId == EXT_COMPANY_ID_1
            }
    }

    def "should import customer to block him"() {
        given:
            def customer = new CustomerOutOfServiceInputDto()

        and:
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.empty()

        and:
            companyDao.findCompanyByExtId(EXT_COMPANY_ID_1) >> Optional.empty()

        and:
            def aidaCompany = aidaCompanyDto10()
            aidaCompanyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompany

        and:
            aidaPersonService.findPersonByCompanyId(EXT_COMPANY_ID_1) >> emptyList()

        when:
            service.blockCustomer(customer, EXT_COMPANY_ID_1, OPERATOR_ID_1)

        then:
            1 * customerOutOfServiceDao.create(*_) >> { arguments ->
                def customerOutOfService = arguments[0]
                assert customerOutOfService.customer.company.extCompanyId == aidaCompany.id
            }
    }

    def "should not update customer out of service if id does not exist"() {
        given:
            def customer = new CustomerOutOfServiceInputDto()

        and:
            customerOutOfServiceDao.get(NOT_EXISTING_ID) >> Optional.empty()

        when:
            service.updateCustomerOutOfService(customer, NOT_EXISTING_ID, OPERATOR_ID_2)

        then:
            thrown(IllegalArgumentException)
    }

    def "should update customer out of service"() {
        given:
            def customer = new CustomerOutOfServiceInputDto()
            customer.reason = "new reason"

        and:
            def existingCustomerOutOfService = customerOutOfService1()
            assert existingCustomerOutOfService.reason != customer.reason

        and:
            customerOutOfServiceDao.get(existingCustomerOutOfService.id) >> Optional.of(existingCustomerOutOfService)

        when:
            service.updateCustomerOutOfService(customer, existingCustomerOutOfService.id, OPERATOR_ID_2)

        then:
            1 * customerOutOfServiceDao.update(*_) >> { arguments ->
                def customerOutOfService = arguments[0]
                assert customerOutOfService.reason == customer.reason
            }
    }

    def "should check if customer out of service exists"(int count, def expectedResult) {
        given:
            customerOutOfServiceDao.checkIfCustomerOutOfServiceExists(ID_1) >> count
        when:
            def result = service.checkIfCustomerOutOfServiceExists(ID_1)

        then:
            result == expectedResult

        where:
            count | expectedResult
            0     | false
            1     | true
    }

    def "should check if contract out of service exists"(int count, def expectedResult) {
        given:
            contractOutOfServiceDao.checkIfContractOutOfServiceExists(ID_1, EXT_COMPANY_ID_1) >> count

        when:
            def result = service.checkIfContractOutOfServiceExists(ID_1, EXT_COMPANY_ID_1)

        then:
            result == expectedResult

        where:
            count | expectedResult
            0     | false
            1     | true
    }

    def "should import customer to block his contract"() {
        given:
            def contractOutOfService = new ContractOutOfServiceInputDto()

        and:
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.empty()
            companyDao.findCompanyByExtId(EXT_COMPANY_ID_1) >> Optional.empty()

        and:
            def aidaCompany = aidaCompanyDto10()
            aidaCompanyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompany

        and:
            aidaPersonService.findPersonByCompanyId(EXT_COMPANY_ID_1) >> emptyList()

        when:
            service.blockCustomerContract(contractOutOfService, EXT_COMPANY_ID_1, CONTRACT_NO_1, OPERATOR_ID_1)

        then:
            1 * customerDao.create(*_) >> { arguments ->
                def customer = arguments[0]
                assert customer.company.extCompanyId == aidaCompany.id
            }

        and:
            1 * contractOutOfServiceDao.create(*_) >> { arguments ->
                def contract = arguments[0]
                assert contract.extCompanyId == EXT_COMPANY_ID_1
                assert contract.contractNo == CONTRACT_NO_1

                def blockingOperator = arguments[1]
                assert blockingOperator == OPERATOR_ID_1
            }
    }

    def "should block contract of existing customer"() {
        given:
            def contractOutOfService = new ContractOutOfServiceInputDto()

        and:
            customerDao.findCustomerByExternalCompanyId(EXT_COMPANY_ID_1) >> Optional.of(aidaCompanyDto10())

        when:
            service.blockCustomerContract(contractOutOfService, EXT_COMPANY_ID_1, CONTRACT_NO_1, OPERATOR_ID_1)

        then:
            0 * customerDao.create(*_)

        and:
            1 * contractOutOfServiceDao.create(*_) >> { arguments ->
                def contract = arguments[0]
                assert contract.extCompanyId == EXT_COMPANY_ID_1
                assert contract.contractNo == CONTRACT_NO_1

                def blockingOperator = arguments[1]
                assert blockingOperator == OPERATOR_ID_1
            }
    }

    def "should delete contact out of service"() {
        when:
            service.deleteContractOutOfService(ContractOutOfServiceTestData.ID_1)
        then:
            1 * contractOutOfServiceDao.delete(ContractOutOfServiceTestData.ID_1)
    }

    def "should delete customer out of service"() {
        when:
            service.deleteCustomerOutOfService(ID_1)
        then:
            1 * customerOutOfServiceDao.delete(ID_1)
    }

    def "should update customer account manager"() {
        when:
            service.updateCustomerAccountManager(ID_1, OPERATOR_ID_12)
        then:
            1 * customerDao.updateCustomerAccountManager(ID_1, OPERATOR_ID_12)
    }

    def "should check that customer exists"() {
        given:
            customerDao.get(ID_1) >> Optional.of(testCustomer1())
        expect:
            service.checkIfCustomerExists(ID_1)
    }

    def "should check that customer not exists"() {
        given:
            customerDao.get(NON_EXISTING_ID) >> Optional.empty()
        expect:
            !service.checkIfCustomerExists(NON_EXISTING_ID)
    }
}
