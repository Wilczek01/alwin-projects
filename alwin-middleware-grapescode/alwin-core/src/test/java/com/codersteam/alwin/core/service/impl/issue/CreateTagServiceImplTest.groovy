package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.aida.core.api.service.*
import com.codersteam.alwin.core.api.service.AidaService
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.issue.TagService
import com.codersteam.alwin.db.dao.IssueDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_4
import static com.codersteam.alwin.testdata.IssueInvoiceTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.TestDateUtils.parse

class CreateTagServiceImplTest extends Specification {

    @Subject
    CreateTagService service

    private InvoiceService aidaInvoiceService = Mock(InvoiceService)
    private DateProvider dateProvider = Mock(DateProvider)
    private IssueDao issueDao = Mock(IssueDao)
    private CompanyService aidaCompanyService = Mock(CompanyService)
    private PersonService aidaPersonService = Mock(PersonService)
    private AidaService aidaService = Mock(AidaService)
    private InvolvementService aidaInvolvementService = Mock(InvolvementService)
    private SegmentService aidaSegmentService = Mock(SegmentService)
    private TagService tagService = Mock(TagService)

    def setup() {
        aidaService.getInvoiceService() >> aidaInvoiceService
        aidaService.getCompanyService() >> aidaCompanyService
        aidaService.getPersonService() >> aidaPersonService
        aidaService.getInvolvementService() >> aidaInvolvementService
        aidaService.getSegmentService() >> aidaSegmentService

        dateProvider.getCurrentDateStartOfDay() >> parse("2018-08-15")

        //noinspection GroovyAssignabilityCheck
        service = Spy(CreateTagService, constructorArgs: [dateProvider, tagService, issueDao])
    }

    def "should tag overdue issue"() {
        given:
            def today = new Date()
            dateProvider.getCurrentDate() >> today

        and:
            issueDao.findOverdueIssueIdsPerDay(today) >> [ISSUE_ID_1, ISSUE_ID_2, ISSUE_ID_4]

        when:
            service.tagOverdueIssues()

        then:
            1 * tagService.tagOverdueIssue(ISSUE_ID_1)
            1 * tagService.tagOverdueIssue(ISSUE_ID_2)
            1 * tagService.tagOverdueIssue(ISSUE_ID_4)
    }

    def "should continue tagging overdue issues even when error occurs"() {
        given:
            def today = new Date()
            dateProvider.getCurrentDate() >> today

        and:
            issueDao.findOverdueIssueIdsPerDay(today) >> [ISSUE_ID_1, ISSUE_ID_2, ISSUE_ID_4]

        and:
            tagService.tagOverdueIssue(ISSUE_ID_2) >> { throw new RuntimeException("some wild error appears") }

        when:
            service.tagOverdueIssues()

        then:
            noExceptionThrown()
            1 * tagService.tagOverdueIssue(ISSUE_ID_1)
            1 * tagService.tagOverdueIssue(ISSUE_ID_4)
    }
}
