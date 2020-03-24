package com.codersteam.alwin.core.service.mapper

import com.codersteam.alwin.core.api.model.customer.AddressTypeDto
import com.codersteam.alwin.jpa.type.AddressType
import spock.lang.Specification

/**
 * @author Piotr Naroznik
 */
class AddressTypeDtoConverterTest extends Specification {

    def "should convert address type to address type dto"() {
        given:
            def converter = new AddressTypeDtoConverter()
        when:
            def addressTypeDto = converter.convert(addressType, null, null)
        then:
            addressTypeDto == expectedAddressTypeDto
        where:
            addressType                | expectedAddressTypeDto
            AddressType.OTHER          | AddressTypeDto.OTHER
            AddressType.BUSINESS       | AddressTypeDto.BUSINESS
            AddressType.RESIDENCE      | AddressTypeDto.RESIDENCE
            AddressType.CORRESPONDENCE | AddressTypeDto.CORRESPONDENCE
    }
}
