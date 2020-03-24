package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService
import com.codersteam.alwin.db.dao.IssueDao
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.issue1

/**
 * @author Tomasz Sliwinski
 */
class ActiveIssuesBalanceUpdaterServiceImplTest extends Specification {

    @Subject
    ActiveIssuesBalanceUpdaterService service

    private IssueDao issueDao = Mock(IssueDao)
    private IssueBalanceUpdaterService issueBalanceUpdaterService = Mock(IssueBalanceUpdaterService)

    def "setup"() {
        service = new ActiveIssuesBalanceUpdaterService(issueDao, issueBalanceUpdaterService)
    }

    def "should have default constructor for EJB"() {
        when:
            def service = new ActiveIssuesBalanceUpdaterService()
        then:
            service
    }

    @Ignore
    def "should continue processing even when exception occurs"() {
        given:
            issueDao.findAllActiveIssues() >> [issue1()]
            issueBalanceUpdaterService.updateIssueBalance(issue1().getId()) >> { throw new RuntimeException("error") }
        when:
            service.updateActiveIssuesBalance()
        then:
            noExceptionThrown()
    }
}
