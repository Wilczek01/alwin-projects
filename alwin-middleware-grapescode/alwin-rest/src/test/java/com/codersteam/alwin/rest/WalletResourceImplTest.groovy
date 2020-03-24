package com.codersteam.alwin.rest

import com.codersteam.alwin.core.api.service.issue.WalletService
import spock.lang.Specification
import spock.lang.Subject

/**
 * @autor Piotr Naroznik
 */
class WalletResourceImplTest extends Specification {

    @Subject
    private WalletResource resource

    def walletService = Mock(WalletService)

    def "setup"() {
        resource = new WalletResource(walletService)
    }

    def "should have default public constructor"() {
        when:
            def resource = new WalletResource()
        then:
            resource
    }
}