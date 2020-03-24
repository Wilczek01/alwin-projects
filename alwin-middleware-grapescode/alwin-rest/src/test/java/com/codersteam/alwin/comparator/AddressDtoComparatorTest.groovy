package com.codersteam.alwin.comparator

import spock.lang.Specification
import spock.lang.Unroll

import static com.codersteam.alwin.testdata.AddressTestData.*

class AddressDtoComparatorTest extends Specification {

    @Unroll
    def "should compare addresses with ids [#address1.id, #address2.id]"() {
        given:
            def addressComparator = new AddressDtoComparator()
        expect:
            addressComparator.compare(address1, address2) == result
        where:
            address1      | address2      | result
            addressDto1() | addressDto1() | 0
            addressDto1() | addressDto2() | 1
            addressDto2() | addressDto1() | -1
            addressDto2() | addressDto3() | -1
            addressDto3() | addressDto2() | 1
    }


    def "should sort addresses"() {
        given:
            def addresses = [addressDto3(), addressDto1(), addressDto2()]
        when:
            Collections.sort(addresses, new AddressDtoComparator())
        then:
            addresses.get(0).id == ADDRESS_ID_2
            addresses.get(1).id == ADDRESS_ID_3
            addresses.get(2).id == ADDRESS_ID_1
    }
}
