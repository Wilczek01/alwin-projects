package com.codersteam.alwin.util

import com.codersteam.alwin.parameters.DateParam
import spock.lang.Specification

import static com.codersteam.alwin.testdata.CSVExportTestData.*
import static com.codersteam.alwin.testdata.TestDateUtils.parse
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.allTypes

/**
 * @author Adam Stepnowski
 */
class CSVUtilsTest extends Specification {

    def "should get format string from date"() {
        given:
            def date = parse("2018-07-19 12:00:12.000000")
            def pattern = "yyyy-MM-dd"
        expect:
            CSVUtils.getFormattedStringFromDate(date, pattern) == '2018-07-19'
    }

    def "should write csv line"() {
        given:
            def sb = new StringBuilder()
        when:
            CSVUtils.writeLine(sb, Arrays.asList("2", null, true, false, new DateParam("2018-07-19"), "invalidCSV\"format", new BigDecimal("10.12")))
        then:
            sb.toString() == EXAMPLE_ROW.toString()
    }

    def "should write csv header row"() {
        given:
            def sb = new StringBuilder()
        when:
            CSVUtils.writeHeader(sb)
        then:
            sb.toString() == EXAMPLE_HEADER.toString()
    }

    def "should write csv filter description footer"() {
        given:
            def sb = new StringBuilder()
        when:
            CSVUtils.writeFilterDescriptionFooter(1L, new DateParam("2018-07-19"), new DateParam("2018-07-20"), true, false, sb, allTypes())
        then:
            sb.toString() == EXAMPLE_FOOTER.toString()

    }
}