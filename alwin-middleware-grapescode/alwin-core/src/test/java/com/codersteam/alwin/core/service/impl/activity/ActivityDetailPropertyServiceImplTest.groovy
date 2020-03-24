package com.codersteam.alwin.core.service.impl.activity

import com.codersteam.alwin.core.service.mapper.AlwinMapper
import com.codersteam.alwin.db.dao.ActivityDetailPropertyDao
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ALL_ACTIVITY_DETAIL_PROPERTIES
import static com.codersteam.alwin.testdata.ActivityDetailPropertyTestData.ALL_ACTIVITY_DETAIL_PROPERTIES_DTOS
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * @author Adam Stepnowski
 */
class ActivityDetailPropertyServiceImplTest extends Specification {

    @Subject
    private ActivityDetailPropertyServiceImpl service

    private ActivityDetailPropertyDao activityDetailPropertyDao
    private AlwinMapper alwinMapper = new AlwinMapper()

    def "setup"() {
        activityDetailPropertyDao = Mock(ActivityDetailPropertyDao)
        service = new ActivityDetailPropertyServiceImpl(activityDetailPropertyDao, alwinMapper)
    }

    def "should return all activity detail properties"() {
        given:
            activityDetailPropertyDao.findAllActivityDetailProperties() >> ALL_ACTIVITY_DETAIL_PROPERTIES
        when:
            def allActivityDetailProperties = service.findAllActivityDetailProperties()
        then:
            assertThat(allActivityDetailProperties).isEqualToComparingFieldByFieldRecursively(ALL_ACTIVITY_DETAIL_PROPERTIES_DTOS)
    }

    def "should check if detail property exists"(Long id, boolean exists) {
        given:
            activityDetailPropertyDao.exists(id) >> exists
        when:
            def result = service.checkIfDetailPropertyExists(id)
        then:
            result == exists
        where:
            id | exists
            2L | false
            1L | true
    }

    def "should check that detail property does not exist"() {
        when:
            def result = service.checkIfDetailPropertyExists(null)
        then:
            !result
    }
}