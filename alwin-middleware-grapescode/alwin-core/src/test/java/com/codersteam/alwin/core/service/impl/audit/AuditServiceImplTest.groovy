package com.codersteam.alwin.core.service.impl.audit

import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.db.dao.OperatorDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2
import static com.codersteam.alwin.testdata.AuditLogEntryTestData.*
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2

/**
 * @author Adam Stepnowski
 */
class AuditServiceImplTest extends Specification {

    @Subject
    private AuditServiceImpl auditService

    private issueDao = Mock(IssueDao)
    private operatorDao = Mock(OperatorDao)

    def "setup"() {
        auditService = new AuditServiceImpl(issueDao, operatorDao)
    }


    def "should return null if entity to initialize and unproxy is null"() {
        when:
            def entity = auditService.initializeAndUnproxy(null)
        then:
            entity == null
    }

    def "should find changes for invoices in issue entity"() {
        when:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator2())
        and:
            issueDao.findChangesByEntityId(ISSUE_ID_2) >> [firstIssueAuditLogEntry(), firstIssueAuditLogEntry()]
        and:
            def changes = auditService.findIssueChanges(ISSUE_ID_2)
        then:
            changes.size() == 2
            changes[0].objectName == 'IssueInvoice'
            changes[1].objectName == 'IssueInvoice'
    }

    def "should find two changes for issue id"() {
        when:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator2())
        and:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator2())
        and:
            issueDao.findChangesByEntityId(ISSUE_ID_2) >> [secondIssueAuditLogEntry(), secondIssueAuditLogEntryWithUpdatedBalance(), secondIssueAuditLogEntry()]
        and:
            def changes = auditService.findIssueChanges(ISSUE_ID_2)
        then:
            changes.size() == 2
            changes[0].objectName == 'Issue'
            changes[1].objectName == 'Issue'

    }

    def "should find one change for issue id"() {
        when:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator2())
        and:
            issueDao.findChangesByEntityId(ISSUE_ID_2) >> [secondIssueAuditLogEntry(), secondIssueAuditLogEntryWithUpdatedBalance()]
        and:
            def changes = auditService.findIssueChanges(ISSUE_ID_2)
        then:
            changes.size() == 1
            changes[0].objectName == 'Issue'

    }

    def "should find one change for issue with activities"() {
        when:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator2())
        and:
            issueDao.findChangesByEntityId(ISSUE_ID_2) >> [fourIssueWithActivityAuditLogEntry(), fourIssueWithActivityAuditLogEntryWithUpdatedBalance()]
        and:
            def changes = auditService.findIssueChanges(ISSUE_ID_2)
        then:
            changes.size() == 1
            changes[0].objectName == 'Issue'

    }

    def "should not find changes for issue id"() {
        when:
            issueDao.findChangesByEntityId(ISSUE_ID_2) >> []
        and:
            def changes = auditService.findIssueChanges(ISSUE_ID_2)
        then:
            changes.size() == 0
    }

    def "should find one change for issue with removed balance"() {
        when:
            operatorDao.get(OPERATOR_ID_1) >> Optional.of(testOperator2())
        and:
            issueDao.findChangesByEntityId(ISSUE_ID_2) >> [secondIssueAuditLogEntry(), secondIssueAuditLogEntryWithRemovedContract()]
        and:
            def changes = auditService.findIssueChanges(ISSUE_ID_2)
        then:
            changes.size() == 1
            changes[0].objectName == 'Contract'
    }
}