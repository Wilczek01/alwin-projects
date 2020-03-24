package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueInvoiceDao
import com.codersteam.alwin.db.dao.IssueTerminationRequestDao
import com.codersteam.alwin.db.dao.OperatorDao
import com.codersteam.alwin.jpa.issue.IssueTerminationRequest
import com.codersteam.alwin.testdata.assertion.IssueTerminationRequestAssert
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.IssueTerminationRequestTestData.*
import static com.codersteam.alwin.testdata.IssueTestData.NOT_EXISTING_ISSUE_ID
import static com.codersteam.alwin.testdata.OperatorTestData.*
import static com.codersteam.alwin.testdata.assertion.IssueTerminationRequestDtoAssert.assertEquals

/**
 * @author Piotr Naroznik
 */
class IssueTerminationServiceImplTest extends Specification {

    @Subject
    private IssueTerminationServiceImpl service

    private IssueTerminationRequestDao issueTerminationRequestDao = Mock(IssueTerminationRequestDao)
    private DateProvider dateProvider = Mock(DateProvider)
    private OperatorDao operatorDao = Mock(OperatorDao)
    private AlwinMapper mapper = new AlwinMapper()
    private IssueInvoiceDao issueInvoiceDao = Mock(IssueInvoiceDao)

    def "setup"() {
        service = new IssueTerminationServiceImpl(issueTerminationRequestDao, operatorDao, dateProvider, mapper, issueInvoiceDao)
    }

    def "should return issue termination request by issue id"() {
        given:
            issueTerminationRequestDao.findIssueTerminationRequestByIssueId(ISSUE_ID_1) >> Optional.of(issueTerminationRequest1())
        when:
            def request = service.findTerminationRequestByIssueId(ISSUE_ID_1)
        then:
            assertEquals(request, issueTerminationRequestDto1())
    }

    def "should return issue termination request by id"() {
        given:
            issueTerminationRequestDao.get(ISSUE_TERMINATION_REQUEST_ID_1) >> Optional.of(issueTerminationRequest1())
        when:
            def request = service.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1)
        then:
            assertEquals(request, issueTerminationRequestDto1())
    }

    def "should not return issue termination request by id when request not exists"() {
        given:
            issueTerminationRequestDao.get(ISSUE_TERMINATION_REQUEST_ID_1) >> Optional.empty()
        when:
            def request = service.findTerminationRequestById(ISSUE_TERMINATION_REQUEST_ID_1)
        then:
            request == null
    }

    def "should throw exception if issue termination request not exists for issue id"() {
        given:
            issueTerminationRequestDao.findIssueTerminationRequestByIssueId(NOT_EXISTING_ISSUE_ID) >> Optional.empty()
        when:
            service.findTerminationRequestByIssueId(NOT_EXISTING_ISSUE_ID)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NOT_EXISTING_ISSUE_ID
    }

    def "should throw exception if issue termination request not exists for id accept issue termination request operation"() {
        given:
            issueTerminationRequestDao.get(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID) >> Optional.empty()
        when:
            service.accept(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID, OPERATOR_ID_2, null)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID
            0 * issueTerminationRequestDao.update(_ as IssueTerminationRequest)
    }

    def "should throw exception if operator not exists for id in accept issue termination request operation"() {
        given:
            issueTerminationRequestDao.get(ISSUE_TERMINATION_REQUEST_ID_1) >> Optional.of(issueTerminationRequest1())
            operatorDao.get(NOT_EXISTING_OPERATOR_ID) >> Optional.empty()
        when:
            service.accept(ISSUE_TERMINATION_REQUEST_ID_1, NOT_EXISTING_OPERATOR_ID, null)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NOT_EXISTING_OPERATOR_ID
            0 * issueTerminationRequestDao.update(_ as IssueTerminationRequest)
    }

    def "should accept issue termination request"() {
        given:
            issueTerminationRequestDao.get(ISSUE_TERMINATION_REQUEST_ID_1) >> Optional.of(issueTerminationRequest1())
            operatorDao.get(OPERATOR_ID_3) >> Optional.of(testOperator3())
            dateProvider.getCurrentDate() >> ACCEPT_DATE
        when:
            service.accept(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_3, acceptedTerminationRequestDto())
        then:
            1 * issueTerminationRequestDao.update(_ as IssueTerminationRequest) >> { List arguments ->
                IssueTerminationRequest request = (IssueTerminationRequest) arguments[0]
                IssueTerminationRequestAssert.assertEquals(request, expectedAcceptedTerminationRequest())
                request
            }
    }

    def "should throw exception if issue termination request not exists for id reject issue termination request operation"() {
        given:
            issueTerminationRequestDao.get(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID) >> Optional.empty()
        when:
            service.reject(NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID, OPERATOR_ID_2, null)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NO_EXISTING_ISSUE_TERMINATION_REQUEST_ID
            0 * issueTerminationRequestDao.update(_ as IssueTerminationRequest)
    }

    def "should throw exception if operator not exists for id in reject issue termination request operation"() {
        given:
            issueTerminationRequestDao.get(ISSUE_TERMINATION_REQUEST_ID_1) >> Optional.of(issueTerminationRequest1())
            operatorDao.get(NOT_EXISTING_OPERATOR_ID) >> Optional.empty()
        when:
            service.reject(ISSUE_TERMINATION_REQUEST_ID_1, NOT_EXISTING_OPERATOR_ID, null)
        then:
            def exception = thrown(EntityNotFoundException)
            exception.entityId == NOT_EXISTING_OPERATOR_ID
            0 * issueTerminationRequestDao.update(_ as IssueTerminationRequest)
    }

    def "should reject issue termination request"() {
        given:
            issueTerminationRequestDao.get(ISSUE_TERMINATION_REQUEST_ID_1) >> Optional.of(issueTerminationRequest1())
            operatorDao.get(OPERATOR_ID_3) >> Optional.of(testOperator3())
            dateProvider.getCurrentDate() >> REJECT_DATE
        when:
            service.reject(ISSUE_TERMINATION_REQUEST_ID_1, OPERATOR_ID_3, rejectedTerminationRequestDto())
        then:
            1 * issueTerminationRequestDao.update(_ as IssueTerminationRequest) >> { List arguments ->
                IssueTerminationRequest request = (IssueTerminationRequest) arguments[0]
                IssueTerminationRequestAssert.assertEquals(request, expectedRejectedTerminationRequest())
                request
            }
    }
}
