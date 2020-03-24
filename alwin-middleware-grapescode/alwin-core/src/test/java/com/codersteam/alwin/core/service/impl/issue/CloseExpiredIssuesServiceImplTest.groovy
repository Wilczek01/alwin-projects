package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.issue.IssueService
import com.codersteam.alwin.db.dao.IssueDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.DUE_DATE

class CloseExpiredIssuesServiceImplTest extends Specification {

    @Subject
    CloseExpiredIssuesService service

    private DateProvider dateProvider = Mock(DateProvider)
    private IssueDao issueDao = Mock(IssueDao)
    private IssueService issueService = Mock(IssueService)

    def setup() {
        service = new CloseExpiredIssuesService(dateProvider, issueService, issueDao)
    }

    def "should have public constructor "() {
        expect:
            new CloseExpiredIssuesService() != null
    }

    def "should not find issues to close"() {
        given:
            def dueDate = parse(DUE_DATE)
            dateProvider.getCurrentDateStartOfDay() >> dueDate
        and:
            issueDao.findIssuesToClose(dueDate) >> []
        when:
            service.closeExpiredIssuesAndCreateChildIssuesIfNeeded()
        then:
            0 * issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(_ as Long)
    }

    def "should close two issues"() {
        given:
            def dueDate = parse(DUE_DATE)
            dateProvider.getCurrentDateStartOfDay() >> dueDate
        and:
            issueDao.findIssuesToClose(dueDate) >> [issue1(), issue2()]
        when:
            service.closeExpiredIssuesAndCreateChildIssuesIfNeeded()
        then:
            1 * issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(ISSUE_ID_1)
            1 * issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(ISSUE_ID_2)
    }

    def "should continue closing issues when error occurred for one issue"() {
        given:
            def dueDate = parse(DUE_DATE)
            dateProvider.getCurrentDateStartOfDay() >> dueDate
        and:
            issueDao.findIssuesToClose(dueDate) >> [issue1(), issue2()]
        when:
            service.closeExpiredIssuesAndCreateChildIssuesIfNeeded()
        then:
            1 * issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(ISSUE_ID_1) >> { throw new RuntimeException("some exception") }
            1 * issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(ISSUE_ID_2)
    }
}
