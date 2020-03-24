package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.customer.AddressTypeDto
import com.codersteam.alwin.jpa.type.AddressType
import spock.lang.Specification

/**
 * @author Piotr Naroznik
 */
class AddressTypeConverterTest extends Specification {

    def "should convert address type dto to address type"() {
        given:
            def converter = new AddressTypeConverter()
        when:
            def addressType = converter.convert(addressTypeDto, null, null)
        then:
            addressType == expectedAddressType
        where:
            addressTypeDto                | expectedAddressType
            AddressTypeDto.OTHER          | AddressType.OTHER
            AddressTypeDto.BUSINESS       | AddressType.BUSINESS
            AddressTypeDto.RESIDENCE      | AddressType.RESIDENCE
            AddressTypeDto.CORRESPONDENCE | AddressType.CORRESPONDENCE
    }
}
