package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.ActivityService
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService
import com.codersteam.alwin.core.api.service.activity.CreateActivityService
import com.codersteam.alwin.core.service.impl.DateProviderImpl
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.*
import com.codersteam.alwin.jpa.activity.Activity
import com.codersteam.alwin.jpa.issue.Issue
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.common.issue.IssueTypeName.DIRECT_DEBT_COLLECTION
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.*

/**
 * @author Adam Stepnowski
 */
class OutgoingCallActivityServiceImplTest extends Specification {

    @Subject
    private OutgoingCallActivityService service

    private IssueDao issueDao = Mock(IssueDao)
    private TagDao tagDao = Mock(TagDao)
    private ActivityDao activityDao = Mock(ActivityDao)
    private ActivityTypeService activityTypeService = Mock(ActivityTypeService)
    private AlwinMapper alwinMapper = new AlwinMapper()
    private DateProvider dateProvider
    private ActivityService activityService
    private CreateActivityService createActivityService

    private ActivityTypeDao activityTypeDao = Mock(ActivityTypeDao)
    private OperatorDao operatorDao = Mock(OperatorDao)
    private DefaultIssueActivityDao defaultIssueActivityDao = Mock(DefaultIssueActivityDao)
    private ActivityDetailPropertyDao activityDetailPropertyDao = Mock(ActivityDetailPropertyDao)

    def "setup"() {
        dateProvider = new DateProviderImpl()
        activityService = new ActivityServiceImpl(issueDao, activityDao, activityTypeService, activityTypeDao, operatorDao, alwinMapper, dateProvider, defaultIssueActivityDao
                , tagDao, activityDetailPropertyDao)
        createActivityService = new CreateActivityServiceImpl(issueDao, alwinMapper, dateProvider, activityService)
        service = new OutgoingCallActivityService(issueDao, createActivityService)
    }

    def "should not create outgoing call activity"() {
        given:
            issueDao.findActiveIssuesIds(PHONE_DEBT_COLLECTION) >> [ISSUE_ID_2, ISSUE_ID_3]
        and:
            issueDao.findActiveIssuesIds([DIRECT_DEBT_COLLECTION]) >> []
        and:
            issueDao.get(ISSUE_ID_2) >> Optional.of(issue2())
        and:
            issueDao.get(ISSUE_ID_3) >> Optional.of(issue3())
        when:
            service.createOutgoingCallActivity()
        then:
            0 * activityDao.createNewActivityForIssue(_)
    }

    def "should create outgoing call activity"() {
        given:
            issueDao.findActiveIssuesIds(PHONE_DEBT_COLLECTION) >> [ISSUE_ID_4]
        and:
            issueDao.findActiveIssuesIds([DIRECT_DEBT_COLLECTION]) >> [ISSUE_ID_4]
        and:
            issueDao.get(ISSUE_ID_4) >> Optional.of(issueWithIncomingCallActivityAndOutstandingSmsActivity())
        when:
            service.createOutgoingCallActivity()
        then:
            2 * activityDao.createNewActivityForIssue(_ as Activity)
            2 * issueDao.update(_ as Issue)
    }

    def "should create outgoing call activity without assignee"() {
        given:
            issueDao.findActiveIssuesIds(PHONE_DEBT_COLLECTION) >> [ISSUE_ID_4]
        and:
            issueDao.findActiveIssuesIds([DIRECT_DEBT_COLLECTION]) >> [ISSUE_ID_4]
        and:
            issueDao.get(ISSUE_ID_4) >> Optional.of(issueWithIncomingCallActivityWithDeclarationWithoutAssignee())
        when:
            service.createOutgoingCallActivity()
        then:
            1 * activityDao.createNewActivityForIssue(_ as Activity)
            1 * issueDao.update(_ as Issue)
    }
}
