package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.contract.FinancialSummaryDto;

import java.math.BigDecimal;

/**
 * @author Tomasz Sliwinski
 */
public class FinancialSummaryTestData {

    private static final BigDecimal REQUIRED_PAYMENT_1 = new BigDecimal("1012.23");
    private static final BigDecimal NOT_REQUIRED_PAYMENT_1 = new BigDecimal("132.34");
    private static final BigDecimal OVERPAYMENT_1 = new BigDecimal("23.23");

    private static final BigDecimal REQUIRED_PAYMENT_2 = new BigDecimal("1012.23");
    private static final BigDecimal NOT_REQUIRED_PAYMENT_2 = new BigDecimal("132.34");
    private static final BigDecimal OVERPAYMENT_2 = new BigDecimal("1144.57");

    public static FinancialSummaryDto financialSummaryDto1() {
        return new FinancialSummaryDto(REQUIRED_PAYMENT_1, NOT_REQUIRED_PAYMENT_1, OVERPAYMENT_1);
    }

    public static FinancialSummaryDto financialSummaryDto2() {
        return new FinancialSummaryDto(REQUIRED_PAYMENT_2, NOT_REQUIRED_PAYMENT_2, OVERPAYMENT_2);
    }
}
