package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.customer.ContactStateDto
import com.codersteam.alwin.jpa.type.ContactState
import spock.lang.Specification

class ContactStateDtoConverterTest extends Specification {

    def "should convert contact state to contact state dto "() {
        given:
            def converter = new ContactStateDtoConverter()
        when:
            def contactStateDto = converter.convert(contactState, null, null)
        then:
            contactStateDto == expectedContactStateDto
        where:
            contactState           | expectedContactStateDto
            ContactState.ACTIVE    | ContactStateDto.ACTIVE
            ContactState.INACTIVE  | ContactStateDto.INACTIVE
            ContactState.PREFERRED | ContactStateDto.PREFERRED
    }
}
