package com.codersteam.alwin.core.service.impl.issue.preparator

import spock.lang.Specification

import java.lang.reflect.Modifier

/**
 * @author Tomasz Sliwinski
 */
class AddressPreparatorTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = AddressPreparator.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }
}
