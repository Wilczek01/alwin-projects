package com.codersteam.alwin.core.service

import spock.lang.Specification

import java.lang.reflect.Modifier

/**
 * @author Tomasz Sliwinski
 */
class AlwinParametersTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = AlwinParameters.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }
}
