package com.codersteam.alwin.validator

import com.codersteam.aida.core.api.service.CompanyService
import com.codersteam.alwin.core.api.model.user.OperatorType
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.core.api.service.issue.IssueTerminationService
import com.codersteam.alwin.core.api.service.issue.IssueTypeService
import com.codersteam.alwin.core.api.service.operator.OperatorService
import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.util.IdsDto
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issueDto1
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.ISSUE_ID_19
import static com.codersteam.alwin.testdata.ClosedIssuesTestData.issueDto19
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1
import static com.codersteam.alwin.testdata.CompanyTestData.NON_EXISTING_COMPANY_ID
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.*
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.ISSUE_ID_11
import static com.codersteam.alwin.testdata.NoAssignedIssuesWithHighPriorityTestData.issueDto11
import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.aidaCompanyDto10
import static com.codersteam.alwin.validator.IssueValidator.*

class IssueValidatorTest extends Specification {

    @Subject
    private IssueValidator validator

    private IssueService issueService = Mock(IssueService)
    private CompanyService companyService = Mock(CompanyService)
    private IssueTypeService issueTypeService = Mock(IssueTypeService)
    private CustomerVerifierService customerVerifierService = Mock(CustomerVerifierService)
    private OperatorService operatorService = Mock(OperatorService)
    private DateProvider dateProvider = Mock(DateProvider)
    private IssueTerminationService issueTerminationService = Mock(IssueTerminationService)

    def "setup"() {
        def aidaService = Mock(AidaService)
        aidaService.getCompanyService() >> companyService
        validator = new IssueValidator(issueService, aidaService, issueTypeService, customerVerifierService, operatorService, dateProvider, issueTerminationService)
    }

    def "should have default public constructor"() {
        when:
            def issueValidator = new IssueValidator()
        then:
            issueValidator
    }

    def "should throw an exception if balance value is separated by comma"() {
        given:
            def balance = '13,31'

        when:
            validator.validateBalanceWithDotAndTwoDecimalValues(balance)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawna wartość salda '" + balance + "'"
    }

    def "should throw an exception if balance value is not valid number"() {
        given:
            def balance = 'im not a number'

        when:
            validator.validateBalanceWithDotAndTwoDecimalValues(balance)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == "Niepoprawna wartość salda '" + balance + "'"
    }

    def "should pass for proper balance value "() {
        given:
            def balance = '13.11'

        when:
            validator.validateBalanceWithDotAndTwoDecimalValues(balance)

        then:
            noExceptionThrown()
    }

    def "should pass for null balance value "() {
        when:
            validator.validateBalanceWithDotAndTwoDecimalValues(null)

        then:
            true
    }

    def "should assign given issue ids"(boolean updateAll, boolean expectedResult) {
        when:
            def result = validator.shouldAssignGivenIssueIds(updateAll)

        then:
            result == expectedResult

        where:
            updateAll | expectedResult
            null      | true
            false     | true
            true      | false
    }

    def "should check that issues are managed"() {
        given:
            def loggedOperatorType = OperatorType.ANALYST

        and:
            def issueIds = new IdsDto([1, 2])
            issueService.findNotManagedIssues(loggedOperatorType, issueIds.ids) >> []

        when:
            validator.validateManagedIssues(loggedOperatorType, issueIds)

        then:
            noExceptionThrown()
    }

    def "should check that not all issues are managed"() {
        given:
            def loggedOperatorType = OperatorType.ANALYST

        and:
            def issueIds = new IdsDto([1, 2])
            issueService.findNotManagedIssues(loggedOperatorType, issueIds.ids) >> [1]

        when:
            validator.validateManagedIssues(loggedOperatorType, issueIds)

        then:
            thrown(AlwinValidationException)
    }

    def "should check that there is no issues to validate"() {
        given:
            def loggedOperatorType = OperatorType.ANALYST

        and:
            def issueIds = new IdsDto([])

        when:
            validator.validateManagedIssues(loggedOperatorType, issueIds)

        then:
            thrown(AlwinValidationException)

    }

    def "should validate issue creation with empty external company id"() {
        given:
            def extCompanyId = null
        when:
            validator.validateIssueCreate(extCompanyId, ISSUE_TYPE_ID_1, OPERATOR_ID_4, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_EXT_COMPANY_ID_MESSAGE
    }

    def "should validate issue creation with empty issue type id"() {
        given:
            def issueTypeId = null
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, issueTypeId, OPERATOR_ID_4, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_ISSUE_TYPE_ID_MESSAGE
    }

    def "should validate issue creation with not existing company"() {
        given:
            companyService.findCompanyByCompanyId(NON_EXISTING_COMPANY_ID) >> null
        when:
            validator.validateIssueCreate(NON_EXISTING_COMPANY_ID, ISSUE_TYPE_ID_1, OPERATOR_ID_4, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_COMPANY_MESSAGE
    }

    def "should validate issue creation with not existing issue type"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(NOT_EXISTING_ISSUE_TYPE_ID) >> null
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, NOT_EXISTING_ISSUE_TYPE_ID, OPERATOR_ID_4, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_ISSUE_TYPE_MESSAGE
    }

    def "should validate issue creation with excluded company"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto1()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> true
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == COMPANY_EXCLUDED_MESSAGE
    }

    def "should validate issue creation with company with active issue"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto1()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> false
        and:
        issueService.doesCompanyHaveActiveIssue(EXT_COMPANY_ID_1) >> true
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == COMPANY_HAS_ACTIVE_ISSUE_MESSAGE
    }

    def "should validate issue creation with not existing assignee id"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto1()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> false
        and:
            operatorService.findOperatorById(NOT_EXISTING_OPERATOR_ID) >> null
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, NOT_EXISTING_OPERATOR_ID, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_OPERATOR_MESSAGE
    }

    def "should validate issue creation with not manager's subordinate assignee"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto1()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> false
        and:
            operatorService.findOperatorById(OPERATOR_ID_1) >> testOperatorDto1()
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, OPERATOR_ID_1, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == NOT_SUBORDINATE_OPERATOR_MESSAGE
    }

    def "should validate issue creation with wrong assignee type for issue type"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto3()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> false
        and:
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, OPERATOR_ID_2, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == WRONG_ASSIGNEE_TYPE_FOR_ISSUE_MESSAGE
    }

    def "should validate issue creation with wrong issue expiration date"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto1()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> false
        and:
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2017-10-12")
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, OPERATOR_ID_2, issueExpirationDate)
        then:
            def e = thrown(AlwinValidationException)
            e.message == WRONG_ISSUE_EXPIRATION_MESSAGE
        where:
            issueExpirationDate << [parse("2017-10-12"), parse("2017-10-11")]
    }

    def "should validate issue creation with no exceptions"() {
        given:
            companyService.findCompanyByCompanyId(EXT_COMPANY_ID_1) >> aidaCompanyDto10()
        and:
            issueTypeService.findIssueTypeById(ISSUE_TYPE_ID_1) >> issueTypeWithOperatorTypesDto1()
        and:
            customerVerifierService.isCompanyExcludedFromDebtCollection(EXT_COMPANY_ID_1) >> false
        and:
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2017-10-12")
        when:
            validator.validateIssueCreate(EXT_COMPANY_ID_1, ISSUE_TYPE_ID_1, OPERATOR_ID_4, OPERATOR_ID_2, parse("2017-10-13"))
        then:
            noExceptionThrown()
    }

    def "should validate issue termination with no existing issue "() {
        given:
            issueService.findIssue(ISSUE_ID_19) >> null
        when:
            validator.validateTerminateIssue(ISSUE_ID_19, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == String.format(MISSING_ISSUE_MESSAGE, ISSUE_ID_19)
    }

    def "should validate issue termination with closed issue "() {
        given:
            issueService.findIssue(ISSUE_ID_19) >> issueDto19()
        when:
            validator.validateTerminateIssue(ISSUE_ID_19, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == ISSUE_ALREADY_CLOSED_MESSAGE
    }

    def "should validate issue termination with empty termination cause "() {
        given:
            issueService.findIssue(ISSUE_ID_11) >> issueDto11()
        when:
            validator.validateTerminateIssue(ISSUE_ID_11, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_ISSUE_TERMINATION_CAUSE_MESSAGE
    }

    def "should validate issue termination with too long termination cause "() {
        given:
            issueService.findIssue(ISSUE_ID_11) >> issueDto11()
        when:
            validator.validateTerminateIssue(ISSUE_ID_11, 'a' * 1001, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == TOO_LONG_ISSUE_TERMINATION_CAUSE_MESSAGE
    }

    def "should validate issue termination with too long exclusion from stats cause"() {
        given:
            issueService.findIssue(ISSUE_ID_11) >> issueDto11()
        when:
            validator.validateTerminateIssue(ISSUE_ID_11, 'termination cause', 'b' * 501)
        then:
            def e = thrown(AlwinValidationException)
            e.message == TOO_LONG_ISSUE_EXCLUDED_FROM_STATS_CAUSE_MESSAGE
    }

    def "should validate issue termination request with no existing request "() {
        given:
            issueTerminationService.findTerminationRequestById(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID) >> null
        when:
            validator.validateRejectIssueTerminateRequest(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_ISSUE_TERMINATION_REQUEST_MESSAGE
    }

    def "should validate issue termination request with closed request "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_2) >> issueTerminationRequestDto2()
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_2, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == ISSUE_TERMINATION_REQUEST_ALREADY_CLOSED_MESSAGE
    }

    def "should validate issue termination request with no existing issue "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> null
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == String.format(MISSING_ISSUE_MESSAGE, ISSUE_ID_1)
    }

    def "should validate issue termination request with closed issue "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto19()
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == ISSUE_ALREADY_CLOSED_MESSAGE
    }

    def "should validate issue termination request with no existing operator "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(NOT_EXISTING_OPERATOR_ID) >> null
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, NOT_EXISTING_OPERATOR_ID, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_OPERATOR_MESSAGE
    }

    def "should validate issue termination request with wrong operator permission "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_3) >> testOperatorDto3()
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_3, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_PERMISSION_TO_MANAGE_TERMINATION_REQUEST_MESSAGE
    }

    def "should validate issue termination request with too long comment "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, 'c' * 501)
        then:
            def e = thrown(AlwinValidationException)
            e.message == TOO_LONG_COMMENT_MESSAGE
    }

    def "should validate issue termination request with no exception "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        when:
            validator.validateRejectIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, ACCEPT_COMMENT)
        then:
            noExceptionThrown()
    }

    def "should validate accept issue termination request with no existing request "() {
        given:
            issueTerminationService.findTerminationRequestById(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID) >> null
        when:
            validator.validateAcceptIssueTerminateRequest(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_ISSUE_TERMINATION_REQUEST_MESSAGE
    }

    def "should validate accept issue termination request with closed request "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_2) >> issueTerminationRequestDto2()
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_2, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == ISSUE_TERMINATION_REQUEST_ALREADY_CLOSED_MESSAGE
    }

    def "should validate accept issue termination request with no existing issue "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> null
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == String.format(MISSING_ISSUE_MESSAGE, ISSUE_ID_1)
    }

    def "should validate accept issue termination request with closed issue "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto19()
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, null, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == ISSUE_ALREADY_CLOSED_MESSAGE
    }

    def "should validate accept issue termination request with no existing operator "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(NOT_EXISTING_OPERATOR_ID) >> null
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, NOT_EXISTING_OPERATOR_ID, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_OPERATOR_MESSAGE
    }

    def "should validate accept issue termination request with wrong operator permission "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_3) >> testOperatorDto3()
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_3, null)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_PERMISSION_TO_MANAGE_TERMINATION_REQUEST_MESSAGE
    }

    def "should validate accept issue termination request with missing termination cause "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        and:
            def requestDto = acceptedTerminationRequestDto()
            requestDto.requestCause = null
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, requestDto)
        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_ISSUE_TERMINATION_CAUSE_MESSAGE
    }

    def "should validate accept issue termination request with too long termination cause "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        and:
            def requestDto = acceptedTerminationRequestDto()
            requestDto.requestCause = 'c' * 1001
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, requestDto)
        then:
            def e = thrown(AlwinValidationException)
            e.message == TOO_LONG_ISSUE_TERMINATION_CAUSE_MESSAGE
    }

    def "should validate accept issue termination request with too long exclude form stats cause"() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        and:
            def requestDto = acceptedTerminationRequestDto()
            requestDto.exclusionFromStatsCause = 'c' * 501
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, requestDto)
        then:
            def e = thrown(AlwinValidationException)
            e.message == TOO_LONG_ISSUE_EXCLUDED_FROM_STATS_CAUSE_MESSAGE
    }

    def "should validate accept issue termination request with too long comment "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        and:
            def requestDto = acceptedTerminationRequestDto()
            requestDto.comment = 'c' * 501
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, requestDto)
        then:
            def e = thrown(AlwinValidationException)
            e.message == TOO_LONG_COMMENT_MESSAGE
    }

    def "should validate accept issue termination request with no exception "() {
        given:
            issueTerminationService.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1) >> issueTerminationRequestDto1()
            issueService.findIssue(ISSUE_ID_1) >> issueDto1()
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()
        when:
            validator.validateAcceptIssueTerminateRequest(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_2, acceptedTerminationRequestDto())
        then:
            noExceptionThrown()
    }

    def "should validate pass for issue invoice exclusion"() {
        given:
            operatorService.findOperatorById(OPERATOR_ID_2) >> testOperatorDto2()

        when:
            validator.validateOperatorIsAllowedToExcludeInvoicePermission(OPERATOR_ID_2)

        then:
            noExceptionThrown()

    }

    def "should not validate for issue invoice exclusion"() {
        given:
            operatorService.findOperatorById(OPERATOR_ID_1) >> testOperatorDto1()

        when:
            validator.validateOperatorIsAllowedToExcludeInvoicePermission(OPERATOR_ID_1)

        then:
            def e = thrown(AlwinValidationException)
            e.message == MISSING_PERMISSION_TO_EXCLUDE_INVOICE_REQUEST_MESSAGE
    }
}