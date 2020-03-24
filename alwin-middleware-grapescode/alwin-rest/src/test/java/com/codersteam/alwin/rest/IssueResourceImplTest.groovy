package com.codersteam.alwin.rest

import spock.lang.Specification

/**
 * @author Tomasz Sliwinski
 */
class IssueResourceImplTest extends Specification {

    def "should have default public constructor"() {
        when:
            def resource = new IssueResource()
        then:
            resource
    }
}
