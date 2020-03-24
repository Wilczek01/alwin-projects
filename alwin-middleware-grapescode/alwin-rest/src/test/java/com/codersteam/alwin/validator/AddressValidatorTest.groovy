package com.codersteam.alwin.validator

import com.codersteam.alwin.core.api.model.customer.AddressDto
import com.codersteam.alwin.core.api.service.customer.AddressService
import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.testdata.AddressTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AddressTestData.ADDRESS_ID_1
import static com.codersteam.alwin.testdata.AddressTestData.ADDRESS_ID_3

/**
 * @author Dariusz Rackowski
 */
class AddressValidatorTest extends Specification {

    private AddressService addressService
    @Subject
    private AddressValidator validator

    def setup() {
        addressService = Mock(AddressService)
        validator = new AddressValidator(addressService)
    }

    def "address fetched from aida should not be updatable"() {
        given:
            def addressToUpdate = new AddressDto()
            addressToUpdate.id = ADDRESS_ID_1

        and:
            addressService.findAddress(ADDRESS_ID_1) >> AddressTestData.addressDto1()

        when:
            validator.validate(addressToUpdate)

        then:
            def exception = thrown(AlwinValidationException)
            exception.message == AddressValidator.ADDRESS_FETCHED_FROM_AIDA_COULD_NOT_BE_UPDATED

    }

    def "address not fetched from aida should be updatable"() {
        given:
            def addressToUpdate = new AddressDto()
            addressToUpdate.id = ADDRESS_ID_3

        and:
            addressService.findAddress(ADDRESS_ID_3) >> AddressTestData.addressDto3()

        when:
            validator.validate(addressToUpdate)

        then:
            notThrown(AlwinValidationException)
    }

}
