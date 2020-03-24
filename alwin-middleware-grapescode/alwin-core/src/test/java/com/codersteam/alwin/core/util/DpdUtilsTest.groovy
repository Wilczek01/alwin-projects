package com.codersteam.alwin.core.util

import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class DpdUtilsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = DpdUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    @Unroll
    def "should DPD for balance #balanceOnDocument, due date #dueDate, last payment date #lastPaymentDate and for date 2019-10-12 be #expectedDPD"() {
        given:
            def forDate = parse("2019-10-12")
        expect:
            DpdUtils.calculateInvoiceDpd(balanceOnDocument, parse(dueDate), parse(lastPaymentDate), forDate) == expectedDPD
        where: "not paid documents"
            balanceOnDocument | dueDate      | lastPaymentDate | expectedDPD
            -10.00            | "2019-10-05" | null            | 7
            -10.00            | "2019-10-05" | "2019-10-02"    | 7
            -10.00            | "2019-10-12" | null            | 0
            -10.00            | "2019-10-12" | "2019-10-02"    | 0
            -10.00            | "2019-12-11" | null            | -60
            -10.00            | "2019-12-11" | "2019-10-02"    | -60
        and: "paid documents"
            null   | "2019-10-11" | null         | 0
            0.00   | "2019-10-11" | null         | 0
            null   | "2019-10-11" | "2019-10-02" | -9
            0.00   | "2019-10-11" | "2019-10-02" | -9
            0.00   | "2019-12-11" | "2019-12-21" | 10
            123.00 | "2019-10-11" | "2019-10-02" | -9
            123.00 | "2019-12-11" | "2019-12-21" | 10
    }
}
