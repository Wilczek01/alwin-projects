package com.codersteam.alwin.core.service.impl.issue.preparator

import com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData
import spock.lang.Specification

import java.lang.reflect.Modifier

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.testdata.BalanceTestData.balancePLN6
import static com.codersteam.alwin.testdata.BalanceTestData.balanceEUR7
import static com.codersteam.alwin.testdata.BalanceTestData.balancePLN1
import static com.codersteam.alwin.testdata.BalanceTestData.balancePLN2

/**
 * @author Tomasz Sliwinski
 */
class BalancePreparatorTest extends Specification {

    def "should have private default constructor"() {
        when:
            def constructor = BalancePreparator.class.getDeclaredConstructor()
        then:
            Modifier.isPrivate(constructor.getModifiers())
            constructor.setAccessible(true)
            def instance = constructor.newInstance()
            instance
    }

    def "should fill PLN balance"() {
        given:
            def issue = AssignedIssuesWithHighPriorityTestData.issue1()
        and:
            def balances = [(PLN): balancePLN1()]
        when:
            BalancePreparator.fillBalance(issue, balances)
        then:
            issue.rpbPLN == balancePLN1().rpb
            issue.balanceStartPLN == balancePLN1().balanceStart
            issue.currentBalancePLN == balancePLN1().currentBalance
            issue.paymentsPLN == balancePLN1().payments
        and:
            issue.rpbEUR == 0
            issue.balanceStartEUR == 0
            issue.currentBalanceEUR == 0
            issue.paymentsEUR == 0
    }

    def "should fill EUR balance"() {
        given:
            def issue = AssignedIssuesWithHighPriorityTestData.issue1()
        and:
            def balances = [(EUR): balancePLN1()]
        when:
            BalancePreparator.fillBalance(issue, balances)
        then:
            issue.rpbPLN == 0
            issue.balanceStartPLN == 0
            issue.currentBalancePLN == 0
            issue.paymentsPLN == 0
        and:
            issue.rpbEUR == balancePLN1().rpb
            issue.balanceStartEUR == balancePLN1().balanceStart
            issue.currentBalanceEUR == balancePLN1().currentBalance
            issue.paymentsEUR == balancePLN1().payments
    }

    def "should fill PLN, EUR and total balance"() {
        given:
            def issue = AssignedIssuesWithHighPriorityTestData.issue1()
        and:
            def balances = [(EUR): balancePLN1(), (PLN): balancePLN2()]
        when:
            BalancePreparator.fillBalance(issue, balances)
        then:
            issue.rpbPLN == balancePLN2().rpb
            issue.balanceStartPLN == balancePLN2().balanceStart
            issue.currentBalancePLN == balancePLN2().currentBalance
            issue.paymentsPLN == balancePLN2().payments
        and:
            issue.rpbEUR == balancePLN1().rpb
            issue.balanceStartEUR == balancePLN1().balanceStart
            issue.currentBalanceEUR == balancePLN1().currentBalance
            issue.paymentsEUR == balancePLN1().payments
        and:
            issue.totalBalanceStartPLN == balancePLN1().balanceStartPln + balancePLN2().balanceStartPln
            issue.totalCurrentBalancePLN == balancePLN1().currentBalancePln + balancePLN2().currentBalancePln
            issue.totalPaymentsPLN == balancePLN1().paymentsPln + balancePLN2().paymentsPln
    }

    def "should return total prices null when at least one of given total price is null (ex. there was no exchange rate for euro)"() {
        given:
            def issue = AssignedIssuesWithHighPriorityTestData.issue1()
        and:
            def balances = [(EUR): balanceEUR7(), (PLN): balancePLN6()]
        when:
            BalancePreparator.fillBalance(issue, balances)
        then:
            issue.rpbPLN == balancePLN6().rpb
            issue.balanceStartPLN == balancePLN6().balanceStart
            issue.currentBalancePLN == balancePLN6().currentBalance
            issue.paymentsPLN == balancePLN6().payments
        and:
            issue.rpbEUR == balanceEUR7().rpb
            issue.balanceStartEUR == balanceEUR7().balanceStart
            issue.currentBalanceEUR == balanceEUR7().currentBalance
            issue.paymentsEUR == balanceEUR7().payments
        and:
            issue.totalBalanceStartPLN == null
            issue.totalCurrentBalancePLN == null
            issue.totalPaymentsPLN == null
    }
}
