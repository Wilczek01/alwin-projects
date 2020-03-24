package com.codersteam.alwin.validator.activity

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto
import spock.lang.Specification

import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * Class description
 *
 * @author Michal Horowic
 */
class CreateActivityDateDetailPropertyTest extends Specification {

    def "should create new instance of type date for activity detail"() {
        given:
            def detailWithDate = new ActivityDetailDto()
            detailWithDate.value = "2017-09-28T22:15:43.120Z"
            detailWithDate.detailProperty = new ActivityDetailPropertyDto()
            detailWithDate.detailProperty.type = Date.canonicalName

        when:
            def propertyValue = new CreateActivityDateDetailProperty().parseDetailProperty(detailWithDate)

        then:
            def expectedDate = parse("2017-09-28 22:15:43.120000")
            ((Date) propertyValue).time == expectedDate.time
    }
}
