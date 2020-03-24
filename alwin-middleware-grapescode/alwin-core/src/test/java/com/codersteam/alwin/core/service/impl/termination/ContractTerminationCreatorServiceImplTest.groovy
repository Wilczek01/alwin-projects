package com.codersteam.alwin.core.service.impl.termination

import com.codersteam.aida.core.api.model.AidaContractWithSubjectsAndSchedulesDto
import com.codersteam.aida.core.api.model.PaymentScheduleWithInstalmentsDto
import com.codersteam.aida.core.api.service.ContractService
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.issue.InvoiceService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.ContractTerminationDao
import com.codersteam.alwin.jpa.termination.ContractTermination
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.core.service.impl.termination.ContractTerminationCreatorServiceImpl.MISSING_RESIDENCE_ADDRESS
import static com.codersteam.alwin.testdata.AddressTestData.address1
import static com.codersteam.alwin.testdata.AddressTestData.address2
import static com.codersteam.alwin.testdata.aida.AidaPaymentScheduleWithInstalmentsTestData.*
import static java.util.Arrays.asList
import static java.util.Collections.*
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Michal Horowic
 */
class ContractTerminationCreatorServiceImplTest extends Specification {

    @Subject
    ContractTerminationCreatorServiceImpl service

    private ContractTerminationDao contractTerminationDao = Mock(ContractTerminationDao)
    private ContractService contractService = Mock(ContractService)
    private CompanyDao companyDao = Mock(CompanyDao)
    private AlwinMapper mapper = new AlwinMapper()
    private InvoiceService invoiceService = Mock(InvoiceService)
    private DateProvider dateProvider = Mock(DateProvider)

    def "setup"() {
        def aidaService = Mock(AidaService)
        aidaService.contractService >> contractService
        this.service = new ContractTerminationCreatorServiceImpl(contractTerminationDao, aidaService, companyDao, mapper, invoiceService, dateProvider)
    }

    def "should not calculate resumption cost if there is no schedules"() {
        given:
        def schedules = []

        when:
        def cost = service.calculateResumptionCost(schedules)

        then:
        cost == BigDecimal.ZERO
    }

    def "should not calculate resumption cost if there is no main schedule"() {
        given:
        def schedule = paymentScheduleWithInstalmentsDto4()
        schedule.active = true
        def schedules = [schedule]

        when:
        def cost = service.calculateResumptionCost(schedules)

        then:
        cost == BigDecimal.ZERO
    }

    def "should not calculate resumption cost if there is no active main schedule"() {
        given:
        def schedules = [inactivePaymentScheduleWithInstalmentsDto()]

        when:
        def cost = service.calculateResumptionCost(schedules)

        then:
        cost == BigDecimal.ZERO
    }

    def "should not calculate resumption cost if there is no installments in main schedule"() {
        given:
        def schedules = [paymentScheduleWithNoRegularInstallmentsDto()]

        when:
        def cost = service.calculateResumptionCost(schedules)

        then:
        cost == BigDecimal.ZERO
    }

    def "should calculate resumption cost"() {
        given:
        def schedule = paymentScheduleWithInstalmentsDto4()
        schedule.active = true
        def schedules = [schedule, paymentScheduleWithInstalmentsDto3(), paymentScheduleWithAllInstalmentsDto()]

        when:
        def cost = service.calculateResumptionCost(schedules)

        then:
        cost == new BigDecimal("196.93")
    }

    @Unroll
    def "should calculate GPS installation charge"(boolean installGps, PaymentScheduleWithInstalmentsDto schedule, BigDecimal expectedStandardCharge, BigDecimal expectedIncreasedCharge) {
        when:
        def charge = service.calculateInstallationCharge(schedule, installGps)

        then:
        charge.standard == expectedStandardCharge
        charge.increased == expectedIncreasedCharge

        where:
        installGps | schedule                                                  | expectedStandardCharge | expectedIncreasedCharge
        false      | paymentScheduleWithInstallmentsPartialyPaidDto()          | BigDecimal.ZERO        | BigDecimal.ZERO
        false      | null                                                      | BigDecimal.ZERO        | BigDecimal.ZERO
        true       | null                                                      | BigDecimal.ZERO        | BigDecimal.ZERO
        true       | inactivePaymentScheduleWithInstalmentsDto()               | BigDecimal.ZERO        | BigDecimal.ZERO
        true       | paymentScheduleWithNoInstallmentsDto()                    | 762.6                  | 1020.9
        true       | paymentScheduleWith37InstallmentsButOnePartiallyPaidDto() | 1670.34                | 1928.64
        true       | paymentScheduleWith36InstallmentsButOnePartiallyPaidDto() | 1670.34                | 1928.64
        true       | paymentScheduleWith26InstallmentsButOnePartiallyPaidDto() | 1670.34                | 1928.64
        true       | paymentScheduleWith25InstallmentsButOnePartiallyPaidDto() | 1367.76                | 1626.06
        true       | paymentScheduleWith13InstallmentsButOnePartiallyPaidDto() | 1065.18                | 1323.48
        true       | paymentScheduleWithInstallmentsPartialyPaidDto()          | 1065.18                | 1323.48
    }

    def "should fill addresses for contract termination"() {
        given:
        def contractTermination = new ContractTermination()

        and:
        def residenceAddress = address2()
        def correspondenceAddress = address1()

        when:
        service.fillAddresses(contractTermination, new HashSet<>(asList(residenceAddress, correspondenceAddress)))

        then:
        assertThat(contractTermination.companyAddress).isEqualToComparingFieldByFieldRecursively(residenceAddress)
        contractTermination.companyAddressId == residenceAddress.id
        assertThat(contractTermination.companyCorrespondenceAddress).isEqualToComparingFieldByFieldRecursively(correspondenceAddress)
        contractTermination.companyCorrespondenceAddressId == correspondenceAddress.id

    }

    def "should not fill addresses for contract termination if there is no residence address"() {
        given:
        def contractTermination = new ContractTermination()

        and:
        def correspondenceAddress = address1()

        when:
        service.fillAddresses(contractTermination, new HashSet<>(singletonList(correspondenceAddress)))

        then:
        def exception = thrown(IllegalStateException)

        and:
        exception.message == MISSING_RESIDENCE_ADDRESS

    }

    def "should fill residence address for contract termination even if there is no correspondence address"() {
        given:
        def contractTermination = new ContractTermination()

        and:
        def residenceAddress = address2()

        when:
        service.fillAddresses(contractTermination, new HashSet<>(singletonList(residenceAddress)))

        then:
        assertThat(contractTermination.companyAddress).isEqualToComparingFieldByFieldRecursively(residenceAddress)
        contractTermination.companyAddressId == residenceAddress.id
        contractTermination.companyCorrespondenceAddress == null
        contractTermination.companyCorrespondenceAddressId == null
    }

    def "should not fill addresses for contract termination if there is no addresses"() {
        given:
        def contractTermination = new ContractTermination()

        when:
        service.fillAddresses(contractTermination, emptySet())

        then:
        def exception = thrown(IllegalStateException)

        and:
        exception.message == MISSING_RESIDENCE_ADDRESS

    }

    def "should find schedule for subject"() {
        given:
        def contract = new AidaContractWithSubjectsAndSchedulesDto()
        contract.setPaymentSchedules(asList(paymentScheduleWithInstalmentsDto2(), paymentScheduleWithInstalmentsDto4()))

        when:
        def schedule = service.findScheduleForSubject(contract)

        then:
        assertThat(schedule).isEqualToComparingFieldByFieldRecursively(paymentScheduleWithInstalmentsDto2())
    }

    @Unroll
    def "should not find schedule for subject"(List schedules, PaymentScheduleWithInstalmentsDto expectedSchedule) {
        given:
        def contract = new AidaContractWithSubjectsAndSchedulesDto()
        contract.setPaymentSchedules(schedules)

        when:
        def schedule = service.findScheduleForSubject(contract)

        then:
        schedule == null

        where:
        schedules                                           | expectedSchedule
        null                                                | null
        emptyList()                                         | null
        asList(paymentScheduleWithInstalmentsDto4())        | null
        asList(inactivePaymentScheduleWithInstalmentsDto()) | null
    }
}
