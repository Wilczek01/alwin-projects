package com.codersteam.alwin.core.service.impl.termination

import com.codersteam.aida.core.api.model.ContractSubjectDto
import com.codersteam.aida.core.api.service.ContractSubjectService
import com.codersteam.alwin.core.api.model.termination.TerminationDto
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.db.dao.CompanyDao
import com.codersteam.alwin.db.dao.ContractTerminationDao
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.efaktura.ContractTerminationClient
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationException
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationRequestDto
import com.codersteam.alwin.efaktura.model.termination.ContractTerminationResponseDto
import com.codersteam.alwin.efaktura.model.termination.data.ContractTypeDto
import com.codersteam.alwin.efaktura.model.termination.data.TerminationTypeDto
import com.codersteam.alwin.jpa.termination.ContractTermination
import com.codersteam.alwin.jpa.termination.ContractTerminationSubject
import com.codersteam.alwin.jpa.type.IssueState
import com.codersteam.alwin.testdata.*
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.common.termination.ContractTerminationState.FAILED
import static com.codersteam.alwin.common.termination.ContractTerminationState.ISSUED
import static com.codersteam.alwin.common.termination.ContractTerminationType.CONDITIONAL
import static com.codersteam.alwin.testdata.ContractTerminationSubjectTestData.*

/**
 * @author Dariusz Rackowski
 */
class ProcessContractTerminationServiceImplTest extends Specification {

    @Subject
    private ProcessContractTerminationServiceImpl service

    ContractTerminationDao contractTerminationDao = Mock(ContractTerminationDao)
    ContractTerminationClient contractTerminationClient = Mock(ContractTerminationClient)
    CompanyDao companyDao = Mock(CompanyDao)
    IssueDao issueDao = Mock(IssueDao)
    ContractSubjectService contractSubjectService = Mock(ContractSubjectService)
    private AidaService aidaService = Mock(AidaService)

    def "setup"() {
        aidaService.getContractSubjectService() >> contractSubjectService
        service = Spy(ProcessContractTerminationServiceImpl, constructorArgs: [contractTerminationDao, contractTerminationClient,
                                                                               companyDao, issueDao, aidaService])
    }

    def "should successfully execute basic scenario of sending contract execution"() {
        given:
        TerminationDto terminationDto = ContractTerminationTestData.terminationDtoForProcessTest()
        String loggedOperatorFullName = "Jan Kowalski"
        and:
        contractTerminationDao.findByIdsIn(_ as Collection) >> [ContractTerminationTestData.contractTermination1104()]
        issueDao.findCompanyActiveIssueId(_ as Long) >> Optional.of(2345L)
        issueDao.get(2345L) >> Optional.of(IssueTestData.issueWithState(IssueState.IN_PROGRESS))
        companyDao.findCompanyByExtId(_ as Long) >> Optional.of(CompanyTestData.company1())
        contractSubjectService.findContractSubjectByContractNo(_ as String) >> [subjectsFromAida(1096L), subjectsFromAida(1097L)]
        when:
        service.sendTermination(terminationDto, loggedOperatorFullName)
        then:
        1 * contractTerminationClient.generateContractTermination(_ as ContractTerminationRequestDto) >> { List arguments ->
            ContractTerminationRequestDto request = arguments[0] as ContractTerminationRequestDto
            assert request.contactEmail == null
            assert request.contactPhoneNo == null
            assert request.issuingOperator == loggedOperatorFullName
            assert request.suspensionDate == ContractTerminationTestData.TERMINATION_DATE_1104
            assert request.terminationType == TerminationTypeDto.valueOf(ContractTerminationTestData.TYPE_1104.name())
            assert request.contracts.size() == 1
            assert request.contracts[0].activationFee == ContractTerminationTestData.RESUMPTION_COST_1104
            assert request.contracts[0].contractNo == ContractTerminationTestData.CONTRACT_NUMBER_1104
            assert request.contracts[0].contractType == ContractTypeDto.valueOf(ContractTerminationTestData.CONTRACT_TYPE_1104.name())
            assert request.contracts[0].subjects != null
            assert request.contracts[0].subjects.size() == 2
            assert request.contracts[0].subjects[0].plateNo == "registration-number-1096"
            assert request.contracts[0].subjects[0].serialNo == "serial-number-1096"
            assert request.contracts[0].subjects[0].subject == "name-1096"
            assert request.contracts[0].subjects[0].gpsFee == ContractTerminationSubjectTestData.GPS_INSTALLATION_CHARGE_1106
            assert request.contracts[0].subjects[0].hasGps == ContractTerminationSubjectTestData.GPS_TO_INSTALL_1106
            assert request.contracts[0].subjects[1].plateNo == "registration-number-1097"
            assert request.contracts[0].subjects[1].serialNo == "serial-number-1097"
            assert request.contracts[0].subjects[1].subject == "name-1097"
            assert request.contracts[0].subjects[1].gpsFee == new BigDecimal("0.00")
            assert request.contracts[0].subjects[1].hasGps == ContractTerminationSubjectTestData.GPS_TO_INSTALL_1107
            assert request.contracts[0].invoices != null
            assert request.contracts[0].invoices.size() == 2
            assert request.contracts[0].invoices[0].contractNo == FormalDebtCollectionInvoiceTestData.CONTRACT_NUMBER_3
            assert request.contracts[0].invoices[0].currency == FormalDebtCollectionInvoiceTestData.CURRENCY_3
            assert request.contracts[0].invoices[0].dueDate == FormalDebtCollectionInvoiceTestData.DUE_DATE_3
            assert request.contracts[0].invoices[0].invoiceBalance == FormalDebtCollectionInvoiceTestData.CURRENT_BALANCE_3.negate()
            assert request.contracts[0].invoices[0].invoiceNo == FormalDebtCollectionInvoiceTestData.INVOICE_NUMBER_3
            assert request.contracts[0].invoices[0].invoiceSum == FormalDebtCollectionInvoiceTestData.GROSS_AMOUNT_3
            assert request.contracts[0].invoices[0].issueDate == FormalDebtCollectionInvoiceTestData.ISSUE_DATE_3
            assert request.contracts[0].invoices[0].leoId == FormalDebtCollectionInvoiceTestData.LEO_ID_3
            assert request.contracts[0].invoices[1].contractNo == FormalDebtCollectionInvoiceTestData.CONTRACT_NUMBER_4
            assert request.contracts[0].invoices[1].currency == FormalDebtCollectionInvoiceTestData.CURRENCY_4
            assert request.contracts[0].invoices[1].dueDate == FormalDebtCollectionInvoiceTestData.DUE_DATE_4
            assert request.contracts[0].invoices[1].invoiceBalance == FormalDebtCollectionInvoiceTestData.CURRENT_BALANCE_4.negate()
            assert request.contracts[0].invoices[1].invoiceNo == FormalDebtCollectionInvoiceTestData.INVOICE_NUMBER_4
            assert request.contracts[0].invoices[1].invoiceSum == FormalDebtCollectionInvoiceTestData.GROSS_AMOUNT_4
            assert request.contracts[0].invoices[1].issueDate == FormalDebtCollectionInvoiceTestData.ISSUE_DATE_4
            assert request.contracts[0].invoices[1].leoId == FormalDebtCollectionInvoiceTestData.LEO_ID_4

            def response = new ContractTerminationResponseDto()
            response.documentHashes = ["hash1", "hash2"]
            response
        }
        and:
        1 * contractTerminationDao.update(_ as ContractTermination) >> { List arguments ->
            ContractTermination termination = arguments[0] as ContractTermination
            assert termination.type == CONDITIONAL
            assert termination.state == ISSUED
            assert termination.subjects[0].gpsToInstall == Boolean.TRUE
            assert termination.subjects[0].gpsInstallationCharge == new BigDecimal("900.00")
            assert termination.subjects[1].gpsToInstall == Boolean.FALSE
            assert termination.subjects[1].gpsInstallationCharge == new BigDecimal("0.00")
            assert termination.contractTerminationAttachments.size() == 2
            assert termination.contractTerminationAttachments[0].reference == "hash1"
            assert termination.contractTerminationAttachments[1].reference == "hash2"
            termination
        }

    }


    def "should store failure information when contract termination client throws exception"() {
        given:
        TerminationDto terminationDto = ContractTerminationTestData.terminationDtoForProcessTest()
        String loggedOperatorFullName = "Jan Kowalski"
        and:
        contractTerminationDao.findByIdsIn(_ as Collection) >> [ContractTerminationTestData.contractTermination1104()]
        issueDao.findCompanyActiveIssueId(_ as Long) >> Optional.of(2345L)
        issueDao.get(2345L) >> Optional.of(IssueTestData.issueWithState(IssueState.IN_PROGRESS))
        companyDao.findCompanyByExtId(_ as Long) >> Optional.of(CompanyTestData.company1())
        contractSubjectService.findContractSubjectByContractNo(_ as String) >> [subjectsFromAida(1096L), subjectsFromAida(1097L)]
        when:
        service.sendTermination(terminationDto, loggedOperatorFullName)
        then:
        1 * contractTerminationClient.generateContractTermination(_ as ContractTerminationRequestDto) >> { List arguments ->
            throw new ContractTerminationException("Message with information from contract termination client")
        }
        and:
        1 * contractTerminationDao.update(_ as ContractTermination) >> { List arguments ->
            ContractTermination termination = arguments[0] as ContractTermination
            assert termination.type == CONDITIONAL
            assert termination.state == FAILED
            assert termination.processingMessage == "Message with information from contract termination client"
            termination
        }

    }

    ContractSubjectDto subjectsFromAida(Long id) {
        def aidaSubject = new ContractSubjectDto()
        aidaSubject.setSubjectId(id)
        aidaSubject.setRegistrationNumber("registration-number-${id}")
        aidaSubject.setSerialNumber("serial-number-${id}")
        aidaSubject.setName("name-${id}")
        aidaSubject
    }

    @Unroll
    def "should determine gps fee"(ContractTerminationSubject subject, BigDecimal expectedGpsFee) {
        expect:
            service.determineGpsFee(subject) == expectedGpsFee
        where:
            subject                                            | expectedGpsFee
            contractTerminationSubjectWithoutGpsInstallation() | BigDecimal.ZERO
            contractTerminationSubject101()                    | GPS_INCREASED_INSTALLATION_CHARGE_101
            contractTerminationSubject126()                    | GPS_INSTALLATION_CHARGE_126

    }

}
