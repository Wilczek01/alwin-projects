package com.codersteam.alwin.core.api.model.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomasz Sliwinski
 */
public enum Currency {

    PLN("PLN"),
    EUR("EUR"),
    UNSUPPORTED("UNSUPPORTED");

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String stringValue;

    private static final Map<String, Currency> STRING_VALUE_TO_CURRENCY = new HashMap<>();

    Currency(final String stringValue) {
        this.stringValue = stringValue;
    }

    static {
        for (final Currency currency : Currency.values()) {
            STRING_VALUE_TO_CURRENCY.put(currency.stringValue, currency);
        }
    }

    public static Currency forStringValue(final String currencyAsString, final String invoiceNumber) {
        final Currency currency = STRING_VALUE_TO_CURRENCY.get(currencyAsString);
        if (currency == null) {
            LOG.error("Unsupported currency type: %s for invoice with number %s", currencyAsString, invoiceNumber);
            return UNSUPPORTED;
        }
        return currency;
    }

    public String getStringValue() {
        return stringValue;
    }
}
