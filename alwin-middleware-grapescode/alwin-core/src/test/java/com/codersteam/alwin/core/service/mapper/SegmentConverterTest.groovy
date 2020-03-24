package com.codersteam.alwin.core.service.mapper

import com.codersteam.aida.core.api.model.CompanySegment
import com.codersteam.alwin.common.issue.Segment
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * @author Piotr Naroznik
 */
class SegmentConverterTest extends Specification {

    @Subject
    def converter = new SegmentConverter()

    @Unroll
    def "should convert aida segment value [#aidaSegment] to value [#expectedSegment]"() {
        expect:
            converter.convert(aidaSegment, null, null) == expectedSegment
        where:
            aidaSegment      | expectedSegment
            CompanySegment.A | Segment.A
            CompanySegment.B | Segment.B
    }

    @Unroll
    def "should throw exception with message for value [#aidaSegment]"() {
        when:
            converter.handleUnsupportedValue(aidaSegment)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.message == expectedMessage
        where:
            aidaSegment      | expectedMessage
            null             | 'company segment from aida has unsupported value = null'
            CompanySegment.A | 'company segment from aida has unsupported value = A'
    }


    def "should verify aida segment values"() {
        expect:
            CompanySegment.values().size() == 2
    }
}
