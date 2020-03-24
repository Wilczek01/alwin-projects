package com.codersteam.alwin.validator.activity

import spock.lang.Specification

import static com.codersteam.alwin.testdata.ActivityDetailTestData.ACTIVITY_DETAIL_PARSED_VALUE_102
import static com.codersteam.alwin.testdata.ActivityDetailTestData.activityDetailDtoWithIntegerValue

class CreateActivityIntegerDetailPropertyTest extends Specification {

    def "should parse detail property value"() {
        given:
            def activityDetailDto = activityDetailDtoWithIntegerValue()
        when:
            def property = new CreateActivityIntegerDetailProperty().parseDetailProperty(activityDetailDto)
        then:
            property == ACTIVITY_DETAIL_PARSED_VALUE_102
    }
}
