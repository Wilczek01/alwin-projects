package com.codersteam.alwin.validator.activity

import com.codersteam.alwin.core.api.model.activity.ActivityDetailDto
import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto
import spock.lang.Specification

/**
 * @author Michal Horowic
 */
class CreateActivityBooleanDetailPropertyTest extends Specification {

    def "should create new instance of type boolean for activity detail"(givenValue, expectedValue) {
        given:
            def detailWithDate = new ActivityDetailDto()
            detailWithDate.value = givenValue
            detailWithDate.detailProperty = new ActivityDetailPropertyDto()
            detailWithDate.detailProperty.type = Boolean.canonicalName

        when:
            def propertyValue = new CreateActivityBooleanDetailProperty().parseDetailProperty(detailWithDate)

        then:
            propertyValue == expectedValue

        where:
            givenValue | expectedValue
            "true"     | true
            "TruE"     | true
            "false"    | false
            "FAlSe"    | false
            null       | null
    }

    def "should throw an exception if value is not appropriate to type boolean for activity detail"() {
        given:
            def detailWithDate = new ActivityDetailDto()
            detailWithDate.value = "test"
            detailWithDate.detailProperty = new ActivityDetailPropertyDto()
            detailWithDate.detailProperty.type = Boolean.canonicalName

        when:
            new CreateActivityBooleanDetailProperty().parseDetailProperty(detailWithDate)

        then:
            thrown(IllegalArgumentException)
    }
}
