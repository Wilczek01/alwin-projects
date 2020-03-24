package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.email.EmailSendMessageEnqueueService
import com.codersteam.alwin.core.api.service.operator.OperatorService
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.IssueDao
import com.codersteam.alwin.db.dao.LastDataSyncDao
import com.codersteam.alwin.jpa.LastDataSync
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.core.api.model.email.EmailType.UNRESOLVED_ISSUES
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER
import static com.codersteam.alwin.core.service.AlwinParameters.SYNC_DATA_DAY_START
import static com.codersteam.alwin.jpa.type.IssueState.NEW
import static com.codersteam.alwin.common.issue.IssueTypeName.DIRECT_DEBT_COLLECTION
import static com.codersteam.alwin.jpa.type.LastDataSyncType.FIND_UNRESOLVED_ISSUES
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.expectedUnresolvedIssueDto
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1
import static com.codersteam.alwin.testdata.LastDataSyncTestData.LAST_DATA_SYNC_TO_DATE_2
import static com.codersteam.alwin.testdata.LastDataSyncTestData.lastDataSync2
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_EMAIL_1
import static com.codersteam.alwin.testdata.UserTestData.TEST_USER_EMAIL_2
import static com.codersteam.alwin.testdata.assertion.LastDataSyncAssert.assertLastDataSync
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class UnresolvedIssueServiceImplTest extends Specification {

    @Subject
    private UnresolvedIssueServiceImpl service

    def issueDao = Mock(IssueDao)
    def alwinMapper = new AlwinMapper()
    def dateProvider = Mock(DateProvider)
    def lastDataSyncDao = Mock(LastDataSyncDao)
    def operatorService = Mock(OperatorService)
    private EmailSendMessageEnqueueService emailSendMessageEnqueueService = Mock(EmailSendMessageEnqueueService)


    def "setup"() {
        service = Spy(UnresolvedIssueServiceImpl,
                constructorArgs: [issueDao, dateProvider, lastDataSyncDao, operatorService, alwinMapper, emailSendMessageEnqueueService])
    }

    def "should not create information about unresolved issues when no issues was found"() {
        given:
            def toDate = parse("2016-10-14 12:23:34.000000")
        and:
            def fromDate = parse("2016-10-12 12:23:34.000000")
        and:
            lastDataSyncDao.findByType(FIND_UNRESOLVED_ISSUES) >> Optional.empty()
        and:
            dateProvider.getCurrentDate() >> toDate
        and:
            dateProvider.getDateStartOfDayMinusDays(SYNC_DATA_DAY_START) >> fromDate
        and:
            issueDao.findIssuesCreatedBySystem(DIRECT_DEBT_COLLECTION, NEW, fromDate, toDate) >> []
        when:
            service.findUnresolvedIssuesAndSendReportEmail()
        then:
            0 * emailSendMessageEnqueueService.enqueueUnresolvedIssuesEmail(_ as UnresolvedIssuesEmailMessage)
            1 * lastDataSyncDao.update(_ as LastDataSync) >> { args ->
                assertLastDataSync((LastDataSync) args[0], fromDate, toDate, FIND_UNRESOLVED_ISSUES)
            }
    }

    def "should create information about missing issues"() {
        given:
            def toDate = parse("2016-10-14 12:23:34.000000")
        and:
            def fromDate = LAST_DATA_SYNC_TO_DATE_2
        and:
            lastDataSyncDao.findByType(FIND_UNRESOLVED_ISSUES) >> Optional.of(lastDataSync2())
        and:
            dateProvider.getCurrentDate() >> toDate
        and:
            issueDao.findIssuesCreatedBySystem(DIRECT_DEBT_COLLECTION, NEW, fromDate, toDate) >> [issue1()]
        and:
            operatorService.findOperatorEmails(PHONE_DEBT_COLLECTOR_MANAGER) >> [TEST_USER_EMAIL_1, TEST_USER_EMAIL_2]
        when:
            service.findUnresolvedIssuesAndSendReportEmail()
        then:
            1 * emailSendMessageEnqueueService.enqueueUnresolvedIssuesEmail(_ as UnresolvedIssuesEmailMessage) >> { args ->
                assertUnresolvedIssuesEmailMessage((UnresolvedIssuesEmailMessage) args[0], fromDate, toDate)
            }
            1 * lastDataSyncDao.update(_ as LastDataSync) >> { args ->
                assertLastDataSync((LastDataSync) args[0], fromDate, toDate, FIND_UNRESOLVED_ISSUES)
            }
    }

    private static void assertUnresolvedIssuesEmailMessage(UnresolvedIssuesEmailMessage unresolvedIssuesEmailMessage, Date fromDate, Date toDate) {
        assert unresolvedIssuesEmailMessage.emailType == UNRESOLVED_ISSUES
        assert unresolvedIssuesEmailMessage.dateFrom == fromDate
        assert unresolvedIssuesEmailMessage.dateTo == toDate
        assert unresolvedIssuesEmailMessage.unresolvedIssues.size() == 1
        assertThat(unresolvedIssuesEmailMessage.unresolvedIssues[0]).isEqualToComparingFieldByField(expectedUnresolvedIssueDto())
        assert unresolvedIssuesEmailMessage.emailRecipients.size() == 2
        assert unresolvedIssuesEmailMessage.emailRecipients.containsAll([TEST_USER_EMAIL_1, TEST_USER_EMAIL_2])
    }
}
