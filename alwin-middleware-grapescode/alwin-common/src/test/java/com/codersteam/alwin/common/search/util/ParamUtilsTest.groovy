package com.codersteam.alwin.common.search.util

import spock.lang.Specification

/**
 * @author Michal Horowic
 */
class ParamUtilsTest extends Specification {

    def "should get first part of param"(String param, String expectedFirstPart) {
        when:
            def firstPart = ParamUtils.getFirstPartSeparatedBySpace(param)

        then:
            firstPart == expectedFirstPart

        where:
            param                 | expectedFirstPart
            "Jan Kowalski"        | "Jan"
            "Jan Janusz Kowalski" | "Jan Janusz"
            "Kowalski"            | "Kowalski"
            ""                    | ""
            null                  | null
    }

    def "should get last part of param"(String param, String expectedLastPart) {
        when:
            def lastPart = ParamUtils.getLastPartSeparatedBySpace(param)

        then:
            lastPart == expectedLastPart

        where:
            param                 | expectedLastPart
            "Jan Kowalski"        | "Kowalski"
            "Jan Janusz Kowalski" | "Kowalski"
            "Kowalski"            | "Kowalski"
            ""                    | ""
            null                  | null
    }

    def "should revert param value"(String param, String expectedRevertedPart) {
        when:
            def revertedParam = ParamUtils.revertValueSeparatedByLastSpace(param)

        then:
            revertedParam == expectedRevertedPart

        where:
            param                 | expectedRevertedPart
            "Jan Kowalski"        | "Kowalski Jan"
            "Jan Janusz Kowalski" | "Kowalski Jan Janusz"
            "Kowalski"            | "Kowalski"
            ""                    | ""
            null                  | null
    }
}
