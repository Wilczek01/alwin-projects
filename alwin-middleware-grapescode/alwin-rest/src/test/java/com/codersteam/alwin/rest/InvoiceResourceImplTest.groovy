package com.codersteam.alwin.rest

import spock.lang.Specification

/**
 * @author Tomasz Sliwinski
 */
class InvoiceResourceImplTest extends Specification {

    def "should have default public constructor"() {
        when:
            def resource = new InvoiceResource()
        then:
            resource
    }
}
