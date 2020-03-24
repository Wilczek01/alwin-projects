package com.codersteam.alwin.rest

import spock.lang.Specification

/**
 * @author Tomasz Sliwinski
 */
class AidaCompanyResourceImplTest extends Specification {

    def "should have default public constructor"() {
        when:
            def resource = new AidaCompanyResource()
        then:
            resource
    }
}
