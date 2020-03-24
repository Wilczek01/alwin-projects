package com.codersteam.alwin.core.service.impl

import spock.lang.Specification
import spock.lang.Subject

/**
 * @author Tomasz Sliwinski
 */
class UUIDGeneratorServiceImplTest extends Specification {

    @Subject
    private UUIDGeneratorServiceImpl service

    def "setup"() {
        service = new UUIDGeneratorServiceImpl()
    }

    def "should generate random UUID"() {
        when:
            def randomUUID = service.generateRandomUUID()
        then:
            randomUUID
    }
}
