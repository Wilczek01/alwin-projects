package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.activity.ActivityStateDto
import com.codersteam.alwin.jpa.type.ActivityState
import spock.lang.Specification

/**
 * @author Piotr Naroznik
 */
class ActivityStateDtoConverterTest extends Specification {

    def "should convert activity state to activity state dto"() {
        given:
            def converter = new ActivityStateDtoConverter()
        when:
            def activityStateDto = converter.convert(activityState, null, null)
        then:
            activityStateDto == expectedActivityStateDto
        where:
            activityState           | expectedActivityStateDto
            ActivityState.PLANNED   | ActivityStateDto.PLANNED
            ActivityState.CANCELED  | ActivityStateDto.CANCELED
            ActivityState.EXECUTED  | ActivityStateDto.EXECUTED
            ActivityState.POSTPONED | ActivityStateDto.POSTPONED
    }
}
