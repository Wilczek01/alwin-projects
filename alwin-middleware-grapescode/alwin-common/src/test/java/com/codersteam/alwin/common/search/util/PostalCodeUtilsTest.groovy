package com.codersteam.alwin.common.search.util

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Michal Horowic
 */
class PostalCodeUtilsTest extends Specification {

    @Unroll
    def "should return constant part of postal code mask"(String mask, String expectedConstant) {
        when:
            def constant = PostalCodeUtils.extractPostalCodeStart(mask)
        then:
            constant == expectedConstant
        where:
            mask     | expectedConstant
            "12-345" | "12-345"
            "12-34x" | "12-34"
            "12-3xx" | "12-3"
            "12-3Xx" | "12-3"
            "12-xxx" | "12-"
            "1x-xxx" | "1"
            "1x-xXX" | "1"
            "xx-xxx" | ""
            "x1-xxx" | ""
            "1x-123" | ""
            "12-x12" | ""
            "12-1x2" | ""
            "000000" | ""
            ""       | ""
            null     | ""
    }

    @Unroll
    def "should return all possible masks for given mask"(String mask, Set<String> expectedMasks) {
        when:
            def possibleMasks = PostalCodeUtils.buildAllPossibleMasksGivenMask(mask)
        then:
            possibleMasks == expectedMasks
        where:
            mask     | expectedMasks
            "12-345" | ["12-345", "12-34x", "12-3xx", "12-xxx", "1x-xxx"]
            "12-34x" | ["12-34x", "12-3xx", "12-xxx", "1x-xxx"]
            "12-3xx" | ["12-3xx", "12-xxx", "1x-xxx"]
            "12-3Xx" | ["12-3xx", "12-xxx", "1x-xxx"]
            "12-xxx" | ["12-xxx", "1x-xxx"]
            "1x-xxx" | ["1x-xxx"]
            "1x-xXX" | ["1x-xxx"]
    }

    def "should return correct postal code for given string"(String inputPostalCode, Optional<String> expectedPostalCode) {
        when:
            def postalCode = PostalCodeUtils.toCorrectPostalCode(inputPostalCode)
        then:
            postalCode == expectedPostalCode
        where:
            inputPostalCode | expectedPostalCode
            "01-234"        | Optional.of("01-234")
            "87654"         | Optional.of("87-654")
            "543"           | Optional.of("54-300")
            "0"             | Optional.of("00-000")
            ""              | Optional.empty()
            "poczta 01"     | Optional.of("01-000")
            "poczta"        | Optional.empty()
            "x 01756 yz"    | Optional.of("01-756")
            "10-233-56"     | Optional.empty()

    }

}
