package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.DefaultIssueActivityDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.DefaultIssueActivityTestData.*
import static com.codersteam.alwin.testdata.IssueTypeTestData.ISSUE_TYPE_ID_1
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class DefaultIssueActivityServiceImplTest extends Specification {

    @Subject
    private DefaultIssueActivityServiceImpl service

    private DefaultIssueActivityDao defaultIssueActivityDao = Mock(DefaultIssueActivityDao)
    private AlwinMapper mapper = new AlwinMapper()
    private DateProvider dateProvider = Mock(DateProvider)

    def "setup"() {
        service = new DefaultIssueActivityServiceImpl(defaultIssueActivityDao, mapper, dateProvider)
    }

    def "should return default activities for issue type"() {
        given:
            defaultIssueActivityDao.findDefaultActivities(ISSUE_TYPE_ID_1) >> DEFAULT_ISSUE_ACTIVITIES_WT1
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2017-10-01")
        when:
            def defaultActivities = service.findDefaultIssueActivities(ISSUE_TYPE_ID_1)
        then:
            assertThat(defaultActivities).isEqualToComparingFieldByFieldRecursively(DEFAULT_ISSUE_ACTIVITY_WT1_DTOS)
    }

    def "should return empty collection when no default activities found"() {
        given:
            defaultIssueActivityDao.findDefaultActivities(ISSUE_TYPE_ID_1) >> []
        when:
            def defaultActivities = service.findDefaultIssueActivities(ISSUE_TYPE_ID_1)
        then:
            defaultActivities == []
    }

    def "should return all default activities"() {
        given:
            defaultIssueActivityDao.findAllDefaultActivities() >> ALL_DEFAULT_ISSUE_ACTIVITIES
        and:
            dateProvider.getCurrentDateStartOfDay() >> parse("2017-10-01")
        when:
            def defaultActivities = service.findAllDefaultIssueActivities()
        then:
            assertThat(defaultActivities).isEqualToComparingFieldByFieldRecursively(ALL_DEFAULT_ISSUE_ACTIVITIES_DTOS)
    }
}
