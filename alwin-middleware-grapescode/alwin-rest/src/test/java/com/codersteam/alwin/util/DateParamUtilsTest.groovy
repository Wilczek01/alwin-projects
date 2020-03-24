package com.codersteam.alwin.util

import com.codersteam.alwin.parameters.DateParam
import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class DateParamUtilsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = DateParamUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "date should be null for null DateParam"() {
        when:
            def date = DateParamUtils.getDateNullSafe(null)
        then:
            date == null
    }

    def "should return date from DateParam"() {
        given:
            def dateAsString = "2017-09-09"
        when:
            def date = DateParamUtils.getDateNullSafe(new DateParam(dateAsString))
        then:
            date == parse(dateAsString)
    }
}
