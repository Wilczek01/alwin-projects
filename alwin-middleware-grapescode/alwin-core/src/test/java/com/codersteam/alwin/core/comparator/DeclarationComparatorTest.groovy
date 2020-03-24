package com.codersteam.alwin.core.comparator

import spock.lang.Specification
import spock.lang.Unroll

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN
import static com.codersteam.alwin.testdata.DeclarationTestData.*

class DeclarationComparatorTest extends Specification {

    @Unroll
    def "should compare declarations with PLN currency and id #id"() {
        given:
            DeclarationComparator comparator = new DeclarationComparator(PLN)
        expect:
            comparator.compare(declaration1, declaration2) == result
        where:
            id | declaration1                           | declaration2                           | result
            1  | declarationWithPaymentDate(DATE_2)     | declarationWithPaymentDate(DATE_1)     | 1
            2  | declarationWithPaymentDate(DATE_1)     | declarationWithPaymentDate(DATE_2)     | -1
            3  | declarationWithDeclarationDate(DATE_2) | declarationWithDeclarationDate(DATE_1) | 1
            4  | declarationWithDeclarationDate(DATE_1) | declarationWithDeclarationDate(DATE_2) | -1
            5  | declarationWithAmountPLN(AMOUNT_2)     | declarationWithAmountPLN(AMOUNT_1)     | 1
            6  | declarationWithAmountPLN(AMOUNT_1)     | declarationWithAmountPLN(AMOUNT_2)     | -1
            7  | declarationWithId(ID_2)                | declarationWithId(ID_1)                | 1
            8  | declarationWithId(ID_1)                | declarationWithId(ID_2)                | -1
            9  | declarationWithId(ID_1)                | declarationWithId(ID_1)                | 0
    }

    @Unroll
    def "should compare declarations with EUR currency with id #id"() {
        given:
            DeclarationComparator comparator = new DeclarationComparator(EUR)
        expect:
            comparator.compare(declaration1, declaration2) == result
        where:
            id | declaration1                           | declaration2                           | result
            1  | declarationWithPaymentDate(DATE_2)     | declarationWithPaymentDate(DATE_1)     | 1
            2  | declarationWithPaymentDate(DATE_1)     | declarationWithPaymentDate(DATE_2)     | -1
            3  | declarationWithDeclarationDate(DATE_2) | declarationWithDeclarationDate(DATE_1) | 1
            4  | declarationWithDeclarationDate(DATE_1) | declarationWithDeclarationDate(DATE_2) | -1
            5  | declarationWithAmountEUR(AMOUNT_2)     | declarationWithAmountEUR(AMOUNT_1)     | 1
            6  | declarationWithAmountEUR(AMOUNT_1)     | declarationWithAmountEUR(AMOUNT_2)     | -1
            7  | declarationWithId(ID_2)                | declarationWithId(ID_1)                | 1
            8  | declarationWithId(ID_1)                | declarationWithId(ID_2)                | -1
            9  | declarationWithId(ID_1)                | declarationWithId(ID_1)                | 0
    }
}
