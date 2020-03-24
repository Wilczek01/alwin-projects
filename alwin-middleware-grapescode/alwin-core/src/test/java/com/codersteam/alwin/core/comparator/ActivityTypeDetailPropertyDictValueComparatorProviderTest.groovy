package com.codersteam.alwin.core.comparator


import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

import static com.codersteam.alwin.core.comparator.ActivityTypeDetailPropertyDictValueComparatorProvider.ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_COMPARATOR
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValue1
import static com.codersteam.alwin.testdata.ActivityTypeDetailPropertyDictValueTestData.activityTypeDetailPropertyDictValue2

/**
 * @author Tomasz Sliwinski
 */
class ActivityTypeDetailPropertyDictValueComparatorProviderTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = ActivityTypeDetailPropertyDictValueComparatorProvider.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    @Unroll
    def "should compare ActivityTypeDetailPropertyDictValue [#o1.id] with [#o2.id] with result #result"() {
        expect:
            ACTIVITY_TYPE_DETAIL_PROPERTY_DICT_VALUE_COMPARATOR.compare(o1, o2) == result
        where:
            o1                                     | o2                                     | result
            activityTypeDetailPropertyDictValue1() | activityTypeDetailPropertyDictValue1() | 0
            activityTypeDetailPropertyDictValue1() | activityTypeDetailPropertyDictValue2() | -1
            activityTypeDetailPropertyDictValue2() | activityTypeDetailPropertyDictValue1() | 1
    }
}
