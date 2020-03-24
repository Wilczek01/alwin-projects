package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.alwin.common.issue.IssueTypeName
import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.ActivityTypeDao
import com.codersteam.alwin.testdata.ActivityTypeTestData
import com.codersteam.alwin.testdata.IssueTypeTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.ActivityTypeTestData.ALL_ACTIVITY_TYPES
import static com.codersteam.alwin.testdata.ActivityTypeTestData.ALL_ACTIVITY_TYPE_DTOS
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Tomasz Sliwinski
 */
class ActivityTypeServiceImplTest extends Specification {

    @Subject
    private ActivityTypeServiceImpl service

    private ActivityTypeDao activityTypeDao
    private AlwinMapper alwinMapper = new AlwinMapper()

    def "setup"() {
        activityTypeDao = Mock(ActivityTypeDao)
        service = new ActivityTypeServiceImpl(activityTypeDao, alwinMapper)
    }

    def "should return all activity types"() {
        given:
            activityTypeDao.findActivityTypes(null, null) >> ALL_ACTIVITY_TYPES
        when:
            def activityTypes = service.findActivityTypes(null, null)
        then:
            assertThat(activityTypes).isEqualToComparingFieldByFieldRecursively(ALL_ACTIVITY_TYPE_DTOS)
    }

    def "should return activity types for issue type"() {
        given:
            def issueType = IssueTypeName.PHONE_DEBT_COLLECTION_1
        and:
            activityTypeDao.findActivityTypes(issueType, null) >> ALL_ACTIVITY_TYPES
        when:
            def activityTypes = service.findActivityTypes(issueType, null)
        then:
            assertThat(activityTypes).isEqualToComparingFieldByFieldRecursively(ALL_ACTIVITY_TYPE_DTOS)
    }

    def "should check if activity type exists for given issue type"(def dbResult, def expectedResult) {
        given:
            def issueTypeId = IssueTypeTestData.ID_2
            def activityTypeId = ActivityTypeTestData.ACTIVITY_TYPE_ID_1
        and:
            activityTypeDao.checkIfActivityTypeIsAllowedForIssueType(issueTypeId, activityTypeId) >> dbResult
        when:
            def result = service.checkIfActivityTypeIsAllowedForIssueType(issueTypeId, activityTypeId)
        then:
            result == expectedResult
        where:
            dbResult | expectedResult
            true     | true
            false    | false
    }
}
