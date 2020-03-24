package com.codersteam.alwin.rest

import spock.lang.Specification

/**
 * @author Piotr Naroznik
 */
class PersonResourceImplTest extends Specification {

    def "should have default public constructor"() {
        when:
            def resource = new PersonResource()
        then:
            resource
    }
}