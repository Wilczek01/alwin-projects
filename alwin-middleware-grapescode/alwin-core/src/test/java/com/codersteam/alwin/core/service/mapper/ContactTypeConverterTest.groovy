package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.customer.ContactTypeDto
import com.codersteam.alwin.jpa.type.ContactType
import spock.lang.Specification
import spock.lang.Unroll

class ContactTypeConverterTest extends Specification {

    @Unroll
    def "should convert contact type dto [#contactTypeDto.key] to contact type [#expectedContactType]"() {
        given:
            def converter = new ContactTypeConverter()
        when:
            def contactType = converter.convert(contactTypeDto, null, null)
        then:
            contactType == expectedContactType
        where:
            contactTypeDto                  | expectedContactType
            ContactTypeDto.E_MAIL           | ContactType.E_MAIL
            ContactTypeDto.PHONE_NUMBER     | ContactType.PHONE_NUMBER
            ContactTypeDto.CONTACT_PERSON   | ContactType.CONTACT_PERSON
            ContactTypeDto.OFFICE_E_EMAIL   | ContactType.OFFICE_E_EMAIL
            ContactTypeDto.DOCUMENT_E_MAIL  | ContactType.DOCUMENT_E_MAIL
            ContactTypeDto.INTERNET_ADDRESS | ContactType.INTERNET_ADDRESS
    }
}
