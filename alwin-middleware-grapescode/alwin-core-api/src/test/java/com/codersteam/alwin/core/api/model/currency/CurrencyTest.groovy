package com.codersteam.alwin.core.api.model.currency

import spock.lang.Specification
import spock.lang.Unroll

import static com.codersteam.alwin.core.api.model.currency.Currency.*

/**
 * @author Tomasz Sliwinski
 */
class CurrencyTest extends Specification {

    @Unroll
    def "currency for '#currencyAsString' should be #currency"() {
        expect:
            forStringValue(currencyAsString, 'IN/VOICE/NUMBER') == currency
        where:
            currencyAsString | currency
            "EUR"            | EUR
            "PLN"            | PLN
            "JPY"            | UNSUPPORTED
    }
}
