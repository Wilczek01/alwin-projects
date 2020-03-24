package com.codersteam.alwin.common

import spock.lang.Specification

import java.lang.reflect.Modifier

/**
 * @author Tomasz Sliwinski
 */
class AlwinConstantsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = AlwinConstants.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }
}
