package com.codersteam.alwin.core.service.mapper

import com.codersteam.aida.core.api.model.CompanySegment
import com.codersteam.alwin.common.issue.Segment
import com.codersteam.alwin.common.prop.AlwinProperties
import com.codersteam.alwin.common.prop.AlwinPropertyKey
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto
import com.codersteam.alwin.core.api.model.activity.ActivityDto
import com.codersteam.alwin.core.api.model.activity.ActivityTypeDto
import com.codersteam.alwin.core.api.model.customer.CompanyDto
import com.codersteam.alwin.core.api.model.issue.*
import com.codersteam.alwin.core.api.model.message.SmsTemplateDto
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto
import com.codersteam.alwin.core.api.model.termination.TerminationContractSubjectDto
import com.codersteam.alwin.core.api.model.termination.TerminationDto
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData
import com.codersteam.alwin.jpa.activity.ActivityDetailProperty
import com.codersteam.alwin.jpa.customer.Company
import com.codersteam.alwin.jpa.customer.Person
import com.codersteam.alwin.jpa.issue.Issue
import com.codersteam.alwin.jpa.issue.IssueTypeConfiguration
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice
import com.codersteam.alwin.testdata.ContractTerminationSubjectTestData
import com.codersteam.alwin.testdata.ContractTerminationTestData
import com.codersteam.alwin.testdata.DemandForPaymentTestData
import com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData
import com.codersteam.alwin.testdata.aida.AidaInvoiceTestData
import org.assertj.core.api.AssertionsForClassTypes
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.codersteam.alwin.core.api.model.issue.IssuePriorityDto.HIGH
import static com.codersteam.alwin.core.api.model.issue.IssuePriorityDto.NORMAL
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.*
import static com.codersteam.alwin.testdata.ActivityTestData.activity1
import static com.codersteam.alwin.testdata.ActivityTestData.activityDto1
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.*
import static com.codersteam.alwin.testdata.ActivityTypeTestData.TEST_ACTIVITY_TYPE_DTO_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.AssignedIssuesWithNormalPriorityTestData.issue5
import static com.codersteam.alwin.testdata.CompanyTestData.*
import static com.codersteam.alwin.testdata.ContractTerminationTestData.DMS_DOCUMENT_URL
import static com.codersteam.alwin.testdata.InvoiceTestData.*
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID
import static com.codersteam.alwin.testdata.IssueTypeConfigurationTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueType1
import static com.codersteam.alwin.testdata.IssueTypeTestData.issueTypeWithOperatorTypesDto1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.messageTemplate1
import static com.codersteam.alwin.testdata.MessageTemplateTestData.smsTemplateDto1
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.issue15
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithNormalPriorityTestData.unassignedIssueDto15
import static com.codersteam.alwin.testdata.PersonTestData.createPerson
import static com.codersteam.alwin.testdata.PersonTestData.createPersonDto
import static com.codersteam.alwin.testdata.TagTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.WalletTestData.*
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.companyFromAida10
import static com.codersteam.alwin.testdata.assertion.PersonAssert.assertEquals
import static com.codersteam.alwin.testdata.assertion.SkipComparator.SKIP_COMPARATOR
import static java.lang.Boolean.FALSE
import static java.lang.Boolean.TRUE
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

class AlwinMapperTest extends Specification {

    @Subject
    private AlwinMapper alwinMapper

    private DateProvider dateProvider = Mock(DateProvider)
    private AlwinProperties alwinProperties = Mock(AlwinProperties)

    def setup() {
        alwinMapper = new AlwinMapper(dateProvider, alwinProperties)
    }

    def "should clone issue without invoices and tags"() {
        given:
            def issue = issue3()

        when:
            def copyIssue = alwinMapper.map(issue, Issue.class)

        then:
            copyIssue.id == null
            copyIssue.assignee == null
            copyIssue.startDate == null
            copyIssue.issueType == null
            copyIssue.issueState == null
            copyIssue.parentIssue == null
            copyIssue.dpdStartDate == null
            assertThat(copyIssue).isEqualToIgnoringGivenFields(issue, 'id', 'assignee', 'startDate', 'issueType', 'issueState',
                    'parentIssue', 'expirationDate', 'issueInvoices', 'tags', 'dpdStartDate')
            copyIssue.getIssueInvoices().size() == 0
    }

    def "should clone issue with invoices"() {
        given:
            def issue = issue1()

        when:
            def copyIssue = alwinMapper.map(issue, Issue.class)

        then:
            copyIssue.id == null
            copyIssue.assignee == null
            copyIssue.startDate == null
            copyIssue.issueType == null
            copyIssue.issueState == null
            copyIssue.parentIssue == null
            copyIssue.dpdStartDate == null
            assertThat(copyIssue).isEqualToIgnoringGivenFields(issue, 'id', 'assignee', 'startDate', 'issueType', 'issueState',
                    'parentIssue', 'expirationDate', 'issueInvoices', 'tags', 'dpdStartDate')
            copyIssue.getIssueInvoices().size() == 1
            def copyInvoice = copyIssue.getIssueInvoices().get(0)
            copyInvoice.issue == copyIssue
            copyInvoice.invoice.number == INVOICE_NUMBER_1
            copyIssue.getTags().size() == issue.getTags().size()
    }

    def "should map activity to activity dto"() {
        given:
            def activity = activity1()

        when:
            def activityDto = alwinMapper.map(activity, ActivityDto.class)

        then:
            assertThat(activityDto).isEqualToComparingFieldByFieldRecursively(activityDto1())
    }

    def "should map aida company to company"() {
        given:
            def aidaCompany = aidaCompanyDto10()

        when:
            def company = alwinMapper.map(aidaCompany, Company.class)

        then:
            assertThat(company).isEqualToComparingFieldByFieldRecursively(companyFromAida10())
    }

    def "should map issue to unresolved issue dto"() {
        given:
            def issue = issue1()

        when:
            def unresolvedIssueDto = alwinMapper.map(issue, UnresolvedIssueDto.class)

        then:
            assertThat(unresolvedIssueDto).isEqualToComparingFieldByFieldRecursively(expectedUnresolvedIssueDto())
    }

    def "should map issue type to dto with operator types"() {
        given:
            def issueType = issueType1()
        when:
            def issueTypeDto = alwinMapper.map(issueType, IssueTypeDto)
        then:
            assertThat(issueTypeDto).isEqualToComparingFieldByFieldRecursively(issueTypeWithOperatorTypesDto1())
    }

    def "should map message template to sms template dto"() {
        given:
            def messageTemplate = messageTemplate1()

        when:
            def smsTemplateDto = alwinMapper.map(messageTemplate, SmsTemplateDto.class)

        then:
            assertThat(smsTemplateDto).isEqualToComparingFieldByFieldRecursively(smsTemplateDto1())
    }

    @Unroll
    def "should map aida segment value [#aidaSegment] to value [#expetedSegment]"() {
        expect:
            alwinMapper.map(aidaSegment, Segment.class) == expetedSegment

        where:
            aidaSegment      | expetedSegment
            null             | null
            CompanySegment.A | Segment.A
            CompanySegment.B | Segment.B
    }

    def "should map issue wallet to issue wallet dto"() {
        when:
            def walletDto = alwinMapper.map(issueWalletToMap(), IssueWalletDto.class)
            walletDto.setCurrentIssueQueueCount(CURRENT_ISSUE_QUEUE_COUNT_1)
        then:
            assertThat(walletDto).isEqualToComparingFieldByFieldRecursively(walletWithDurationDto1())
    }

    def "should map tag wallet to tag wallet dto"() {
        when:
            def walletDto = alwinMapper.map(tagWallet1(), TagWalletDto.class)
            walletDto.setCurrentIssueQueueCount(CURRENT_ISSUE_QUEUE_COUNT_1)
        then:
            assertThat(walletDto).isEqualToComparingFieldByFieldRecursively(tagWalletDto1())
    }

    def "should map issue state wallet to issue state wallet dto"() {
        when:
            def walletDto = alwinMapper.map(wallet1(), IssueStateWalletDto.class)
        then:
            assertThat(walletDto).isEqualToComparingFieldByFieldRecursively(issueStateWalletDto1())
    }

    @Unroll
    def "should map invoice to invoice dto for issue [#issueId]"() {
        given:
            def invoice = testInvoice()

        when:
            def invoiceDto = alwinMapper.map(invoice, InvoiceDto.class, new IssueMappingContext(issueId))

        then:
            assertThat(invoiceDto).isEqualToComparingFieldByFieldRecursively(testInvoiceDto(excluded, issueSubject))

        where:
            issueId               | excluded | issueSubject
            NOT_EXISTING_ISSUE_ID | FALSE    | FALSE
            ISSUE_ID_2            | TRUE     | FALSE
            ISSUE_ID_3            | FALSE    | TRUE
    }

    def "should map company to company dto"() {
        when:
            def companyDto = alwinMapper.map(company2(), CompanyDto.class)
        then:
            assertThat(companyDto).isEqualToComparingFieldByFieldRecursively(ompanyDto2())
    }

    def "should map company with empty persons to company dto"() {
        when:
            def companyDto = alwinMapper.map(company1(), CompanyDto.class)
        then:
            assertThat(companyDto).isEqualToComparingFieldByFieldRecursively(companyDto1())
    }

    def "should map crete person dto to person"() {
        when:
            def person = alwinMapper.map(createPersonDto(), Person.class)
        then:
            assertEquals(person, createPerson())
    }

    def "should map activity detail property to dto"() {
        when:
            def activityDetailProperty = alwinMapper.map(activityDetailProperty1(), ActivityDetailPropertyDto.class)
        then:
            assertThat(activityDetailProperty).isEqualToComparingFieldByFieldRecursively(ACTIVITY_DETAIL_PROPERTY_DTO_1)
    }

    def "should map activity detail property dto to entity"() {
        when:
            def activityDetailProperty = alwinMapper.map(ACTIVITY_DETAIL_PROPERTY_DTO_1, ActivityDetailProperty.class)
        then:
            assertThat(activityDetailProperty).isEqualToComparingFieldByFieldRecursively(activityDetailProperty1())
    }

    def "should map issue to issue dto and set current balance"() {
        given:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            def issueDto = alwinMapper.map(issue15(), IssueDto.class)
        then:
            issueDto.currentBalancePLN == unassignedIssueDto15().currentBalancePLN
            issueDto.currentBalanceEUR == unassignedIssueDto15().currentBalanceEUR
            assertThat(issueDto).isEqualToComparingFieldByFieldRecursively(unassignedIssueDto15())
    }

    def "should map issue to issue dto and set DPD start and estimated DPD"() {
        given:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            def issueDto = alwinMapper.map(issue15(), IssueDto.class)
        then:
            issueDto.dpdStart == unassignedIssueDto15().dpdStart
            issueDto.dpdEstimated == unassignedIssueDto15().dpdEstimated
    }

    def "should map issue to issue dto and set DPD to 0 if dpdStartDate not set"() {
        given:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        and:
            def issue = issue15()
            issue.dpdStartDate = null
        when:
            def issueDto = alwinMapper.map(issue, IssueDto.class)
        then:
            issueDto.dpdStart == 0
            issueDto.dpdEstimated == 0
    }

    def "should map issue type configuration to issue type configuration dto"() {
        when:
            def issueTypeConfigurationDto = alwinMapper.map(issueTypeConfigurationToMap(), IssueTypeConfigurationDto.class)
        then:
            assertThat(issueTypeConfigurationDto).isEqualToComparingFieldByFieldRecursively(issueTypeConfigurationWithOperatorTypeDto())
    }

    def "should map issue type configuration dto to issue type configuration"() {
        when:
            def issueTypeConfigurationDto = alwinMapper.map(issueTypeConfigurationWithOperatorTypeDto(), IssueTypeConfiguration.class)
        then:
            assertThat(issueTypeConfigurationDto).isEqualToComparingFieldByFieldRecursively(issueTypeConfigurationWithoutParentOperator())
    }

    @Unroll
    def "should map issue with priority [#priority.key]"() {
        given:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            def mapped = alwinMapper.map(issue, IssueDto.class)
        then:
            mapped.priority == priority
        where:
            issue    | priority
            issue1() | HIGH
            issue5() | NORMAL
    }

    def "should map activity type dto to activity type dto"() {
        given:
            def activityTypeDto = TEST_ACTIVITY_TYPE_DTO_1

        expect:
            activityTypeDto.activityTypeDetailProperties.size() > 0

        when:
            def newActivityTypeDto = alwinMapper.map(activityTypeDto, ActivityTypeDto)

        then:
            newActivityTypeDto.activityTypeDetailProperties == null
            assertThat(newActivityTypeDto).usingComparatorForFields(SKIP_COMPARATOR, 'activityTypeDetailProperties')
                    .isEqualToComparingFieldByFieldRecursively(activityTypeDto)
    }

    def "should map invoice with corrections"() {
        given:
            def invoiceWithCorrections = testInvoice11()
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
        when:
            def invoiceDto = alwinMapper.map(invoiceWithCorrections, InvoiceDto)
        then:
            def corrections = invoiceDto.corrections
            AssertionsForClassTypes.assertThat(corrections)
                    .usingComparatorForFields(SKIP_COMPARATOR, 'excluded')
                    .isEqualToComparingFieldByFieldRecursively(invoiceCorrectionsDto11())
    }

    def "should map issue to issue dto with sorted tags"() {
        given:
            dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")
            def issue = issue1()
            issue.tags = new LinkedHashSet<>([testTag3(), testTag1()])
        when:
            def issueDto = alwinMapper.map(issue, IssueDto.class)
        then:
            assertThat(issueDto.getTags()).containsExactly(testTagDto1(), testTagDto3())
    }

    def "should map activity detail property to activity detail property DTO with sorted dictionary values"() {
        given:
            def activityDetailProperty = activityDetailProperty11()
            activityDetailProperty.dictionaryValues = new HashSet<>([
                    activityTypeDetailPropertyDictValue4(),
                    activityTypeDetailPropertyDictValue1(),
                    activityTypeDetailPropertyDictValue3(),
                    activityTypeDetailPropertyDictValue2(),
            ])
        when:
            def activityDetailPropertyDto = alwinMapper.map(activityDetailProperty, ActivityDetailPropertyDto)
        then:
            assertThat(activityDetailPropertyDto.dictionaryValues).containsExactly(
                    activityTypeDetailPropertyDictValueDto1(),
                    activityTypeDetailPropertyDictValueDto2(),
                    activityTypeDetailPropertyDictValueDto3(),
                    activityTypeDetailPropertyDictValueDto4()
            )
    }

    def "should map AidaInvoiceWithCorrectionsDto to FormalDebtCollectionInvoice"() {
        given:
            def expectedInvoice = FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice5()
            expectedInvoice.id = null
        when:
            def formalDebtCollectionInvoice = alwinMapper.map(AidaInvoiceTestData.dueInvoiceWithCorrections5(), FormalDebtCollectionInvoice)
        then:
            assertThat(formalDebtCollectionInvoice).isEqualToComparingFieldByFieldRecursively(expectedInvoice)
    }

    def "should map ContractTermination to TerminationDto"() {
        given:
            def expectedTerminationDto = ContractTerminationTestData.terminationDto101()
        and:
            alwinProperties.getProperty(AlwinPropertyKey.DMS_DOCUMENT_URL) >> DMS_DOCUMENT_URL
        when:
            def terminationDto = alwinMapper.map(ContractTerminationTestData.contractTermination101(), TerminationDto.class)
        then:
            assertThat(terminationDto).isEqualToComparingFieldByFieldRecursively(expectedTerminationDto)
    }

    def "should map ContractTermination to TerminationContractDto"() {
        given:
            def expectedTerminationContractDto101 = ContractTerminationTestData.terminationContractDto101()
        when:
            def terminationContractDto = alwinMapper.map(ContractTerminationTestData.contractTermination101(), TerminationContractDto.class)
        then:
            assertThat(terminationContractDto).isEqualToComparingFieldByFieldRecursively(expectedTerminationContractDto101)
    }

    def "should map ContractTerminationSubject to TerminationContractSubjectDto"() {
        given:
            def expectedTerminationContractSubjectDto1 = ContractTerminationSubjectTestData.terminationContractSubjectDto101()
        when:
            def terminationContractSubjectDto = alwinMapper.map(ContractTerminationSubjectTestData.contractTerminationSubject101(), TerminationContractSubjectDto.class)
        then:
            assertThat(terminationContractSubjectDto).isEqualToComparingFieldByFieldRecursively(expectedTerminationContractSubjectDto1)
    }

    def "should map ContractTermination to ContractTerminationInitialData"() {
        given:
            def contractTermination = ContractTerminationTestData.contractTermination104()
        when:
            def contractTerminationInitialData = alwinMapper.map(contractTermination, ContractTerminationInitialData)
        then:
            contractTerminationInitialData
            contractTerminationInitialData.precedingContractTerminationId == contractTermination.id
            !contractTerminationInitialData.invoices.isEmpty()
    }

    def "should map DemandForPayment to ContractTerminationInitialData"() {
        given:
            def demandForPayment = DemandForPaymentTestData.demandForPaymentWithCompanyName2()
        when:
            def contractTerminationInitialData = alwinMapper.map(demandForPayment, ContractTerminationInitialData)
        then:
            contractTerminationInitialData
            contractTerminationInitialData.precedingDemandForPaymentId == demandForPayment.id
    }
}
