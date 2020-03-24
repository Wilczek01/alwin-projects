package com.codersteam.alwin.core.util

import com.codersteam.alwin.testdata.InvoiceTestData
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

import static com.codersteam.alwin.testdata.TestDateUtils.parse

/**
 * @author Tomasz Sliwinski
 */
class InvoiceUtilsTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = InvoiceUtils.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    @SuppressWarnings(["GroovyPointlessBoolean", "GroovyAssignabilityCheck"])
    @Unroll
    def "invoice should #isIssueSubjectMessage issue subject for new issue for balance: #invoiceCurrentBalance, issue start date: #issueStartDate and invoice due date #invoiceDueDate"() {
        expect:
            InvoiceUtils.isIssueSubjectForNewIssue(invoiceCurrentBalance, parse(issueStartDate), parse(invoiceDueDate)) == isIssueSubject
        where:
            invoiceCurrentBalance | issueStartDate | invoiceDueDate || isIssueSubject
            100.00                | "2018-05-20"   | "2018-05-19"   || false
            0.00                  | "2018-05-20"   | "2018-05-19"   || false
            0.00                  | "2018-05-20"   | "2018-05-21"   || false
            -0.01                 | "2018-05-20"   | "2018-05-19"   || true
            null                  | "2018-05-20"   | "2018-05-19"   || false
            -0.01                 | "2018-05-20"   | "2018-05-20"   || false
            -0.01                 | "2018-05-20"   | "2018-05-30"   || false

            isIssueSubjectMessage = isIssueSubject ? "be" : "not be"
    }

    @SuppressWarnings(["GroovyPointlessBoolean", "GroovyAssignabilityCheck"])
    @Unroll
    def "invoice should #isIssueSubjectMessage issue subject for balance: #currentInvoiceBalance, issue start date: #issueStartDate, issue end date: #issueExpirationDate, current date: #currentDate, include due invoices as issue subject during issue: #includeDueInvoicesDuringIssue and invoice due date #invoiceDueDate"() {
        expect:
            InvoiceUtils.isIssueSubject(currentInvoiceBalance, parse(issueStartDate), parse(issueExpirationDate),
                    parse(invoiceDueDate), parse(currentDate), includeDueInvoicesDuringIssue) == isIssueSubject
        where: 'not include documents during issue'
            currentInvoiceBalance | issueStartDate | issueExpirationDate | invoiceDueDate | currentDate  | includeDueInvoicesDuringIssue || isIssueSubject
            100.00                | "2018-05-20"   | "2018-06-20"        | "2018-05-19"   | "2018-05-31" | false                         || false
            0.00                  | "2018-05-20"   | "2018-06-20"        | "2018-05-19"   | "2018-05-31" | false                         || false
            0.00                  | "2018-05-20"   | "2018-06-20"        | "2018-05-21"   | "2018-05-31" | false                         || false
            -0.01                 | "2018-05-20"   | "2018-06-20"        | "2018-05-19"   | "2018-05-31" | false                         || true
            null                  | "2018-05-20"   | "2018-06-20"        | "2018-05-19"   | "2018-05-31" | false                         || false
            -0.01                 | "2018-05-20"   | "2018-06-20"        | "2018-05-20"   | "2018-05-31" | false                         || false
            -0.01                 | "2018-05-20"   | "2018-06-20"        | "2018-05-30"   | "2018-05-31" | false                         || false
        and: 'include documents during issue'
            100.00 | "2018-05-20" | "2018-06-20" | "2018-05-19" | "2018-05-31" | true || false
            0.00   | "2018-05-20" | "2018-06-20" | "2018-05-19" | "2018-05-31" | true || false
            0.00   | "2018-05-20" | "2018-06-20" | "2018-05-21" | "2018-05-31" | true || false
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-05-19" | "2018-05-31" | true || true
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-06-20" | "2018-06-21" | true || true
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-06-21" | "2018-06-22" | true || false
            0.00   | "2018-05-20" | "2018-06-20" | "2018-06-21" | "2018-06-22" | true || false
            null   | "2018-05-20" | "2018-06-20" | "2018-06-21" | "2018-06-22" | true || false
            null   | "2018-05-20" | "2018-06-20" | "2018-05-19" | "2018-05-31" | true || false
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-05-20" | "2018-05-20" | true || false
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-05-30" | "2018-05-20" | true || false
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-05-20" | "2018-05-31" | true || true
            -0.01  | "2018-05-20" | "2018-06-20" | "2018-05-30" | "2018-05-30" | true || false

            isIssueSubjectMessage = isIssueSubject ? "be" : "not be"
    }

    @Unroll
    def "invoice should have #message balance for #balance"() {
        given:
            def invoice = InvoiceTestData.invoiceWithBalanceDto1()
            invoice.currentBalance = balance
        expect:
            InvoiceUtils.isNegativeInvoiceBalance(invoice) == isNegative
        where:
            balance   | isNegative
            null      | false
            0.00      | false
            0.01      | false
            10000.00  | false
            -0.01     | true
            -10000.00 | true

            message = isNegative ? "negative" : "not negative"
    }
}
