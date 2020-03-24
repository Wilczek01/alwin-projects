package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.activity.ActivityStateDto
import com.codersteam.alwin.jpa.type.ActivityState
import spock.lang.Specification

/**
 * @author Piotr Naroznik
 */
class ActivityStateConverterTest extends Specification {

    def "should convert activity state dto to activity state "() {
        given:
            def converter = new ActivityStateConverter()
        when:
            def activityState = converter.convert(activityStateDto, null, null)
        then:
            activityState == expectedActivityState
        where:
            activityStateDto           | expectedActivityState
            ActivityStateDto.PLANNED   | ActivityState.PLANNED
            ActivityStateDto.CANCELED  | ActivityState.CANCELED
            ActivityStateDto.EXECUTED  | ActivityState.EXECUTED
            ActivityStateDto.POSTPONED | ActivityState.POSTPONED
    }
}
