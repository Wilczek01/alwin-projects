package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.customer.ContactTypeDto
import com.codersteam.alwin.jpa.type.ContactType
import spock.lang.Specification
import spock.lang.Unroll

class ContactTypeDtoConverterTest extends Specification {

    @Unroll
    def "should convert contact type [#contactType] to contact type dto [#expectedContactTypeDto.key]"() {
        given:
            def converter = new ContactTypeDtoConverter()
        when:
            def contactTypeDto = converter.convert(contactType, null, null)
        then:
            contactTypeDto == expectedContactTypeDto
        where:
            contactType                  | expectedContactTypeDto
            ContactType.E_MAIL           | ContactTypeDto.E_MAIL
            ContactType.PHONE_NUMBER     | ContactTypeDto.PHONE_NUMBER
            ContactType.CONTACT_PERSON   | ContactTypeDto.CONTACT_PERSON
            ContactType.OFFICE_E_EMAIL   | ContactTypeDto.OFFICE_E_EMAIL
            ContactType.DOCUMENT_E_MAIL  | ContactTypeDto.DOCUMENT_E_MAIL
            ContactType.INTERNET_ADDRESS | ContactTypeDto.INTERNET_ADDRESS
    }
}
