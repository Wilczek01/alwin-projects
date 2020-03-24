package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.service.CurrencyExchangeRateService;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Naroznik
 */
public class CurrencyExchangeRateServiceMock implements CurrencyExchangeRateService {

    /**
     * Kurs wymiany waluty po dacie
     */
    public static Map<Date, BigDecimal> EUR_EXCHANGE_RATE_BY_DATE = new HashMap<>();

    @Override
    public BigDecimal findCurrencyExchangeRateByDate(final Date date) {
        return EUR_EXCHANGE_RATE_BY_DATE.get(date);
    }

    public static void reset() {
        EUR_EXCHANGE_RATE_BY_DATE = new HashMap<>();
    }
}
