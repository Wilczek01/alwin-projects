package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.customer.ContactStateDto
import com.codersteam.alwin.jpa.type.ContactState
import spock.lang.Specification

/**
 * @author Piotr Naroznik
 */
class ContactStateConverterTest extends Specification {

    def "should convert contact state dto to contact state "() {
        given:
            def converter = new ContactStateConverter()
        when:
            def contactState = converter.convert(contactStateDto, null, null)
        then:
            contactState == expectedContactState
        where:
            contactStateDto           | expectedContactState
            ContactStateDto.ACTIVE    | ContactState.ACTIVE
            ContactStateDto.INACTIVE  | ContactState.INACTIVE
            ContactStateDto.PREFERRED | ContactState.PREFERRED
    }
}
