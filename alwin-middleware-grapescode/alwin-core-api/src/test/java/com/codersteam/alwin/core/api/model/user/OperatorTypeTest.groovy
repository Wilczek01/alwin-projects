package com.codersteam.alwin.core.api.model.user

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Tomasz Sliwinski
 */
class OperatorTypeTest extends Specification {

    @Unroll
    def "should get role #roles by name"() {
        expect:
            OperatorType.forName(roles.toString()) == roles
        where:
            roles << OperatorType.values()
    }
}
