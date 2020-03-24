package com.codersteam.alwin.rest

import spock.lang.Specification


/**
 * @author Adam Stepnowski
 */
class AuditResourceImplTest extends Specification {

    def "should have default public constructor"() {
        when:
            def resource = new AuditResourceImplTest()
        then:
            resource
    }
}