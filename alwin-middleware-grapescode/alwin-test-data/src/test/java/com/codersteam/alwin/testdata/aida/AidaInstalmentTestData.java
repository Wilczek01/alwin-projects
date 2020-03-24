package com.codersteam.alwin.testdata.aida;


import com.codersteam.aida.core.api.model.FinancingElementDto;
import com.codersteam.aida.core.api.model.FinancingPaymentDto;
import com.codersteam.aida.core.api.model.InstalmentDto;

import java.math.BigDecimal;
import java.util.Date;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_3;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_4;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_5;
import static com.codersteam.alwin.testdata.aida.AidaFinancingElementTestData.FINANCING_ELEMENT_DTO_1;
import static com.codersteam.alwin.testdata.aida.AidaFinancingElementTestData.FINANCING_ELEMENT_DTO_2;
import static com.codersteam.alwin.testdata.aida.AidaFinancingElementTestData.REPURCHASE_FINANCING_ELEMENT_DTO;
import static com.codersteam.alwin.testdata.aida.AidaFinancingPaymentTestData.FINANCING_PAYMENT_DTO_4;
import static com.codersteam.alwin.testdata.aida.AidaFinancingPaymentTestData.FINANCING_PAYMENT_DTO_5;
import static com.codersteam.alwin.testdata.aida.AidaFinancingPaymentTestData.FINANCING_PAYMENT_DTO_6;
import static com.codersteam.alwin.testdata.aida.AidaFinancingPaymentTestData.FINANCING_PAYMENT_DTO_7;
import static com.codersteam.alwin.testdata.aida.AidaFinancingPaymentTestData.FINANCING_PAYMENT_DTO_8;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings("WeakerAccess")
public class AidaInstalmentTestData {

    private static final long FINANCIAL_CALCULATION_NUMBER = 987654321L;
    private static final long FINANCIAL_MODEL_NO = 123456789L;
    public static final BigDecimal REMAINING_PAYMENT = new BigDecimal("10.0");

    public static final Long INSTALMENT_ID_4 = 4L;
    public static final Integer INSTALMENT_INDEX_4 = 1;
    public static final BigDecimal INSTALMENT_RPB_4 = new BigDecimal("20.00");
    public static final BigDecimal INSTALMENT_CAPITAL_4 = new BigDecimal("100.12");
    public static final BigDecimal INSTALMENT_INTEREST_4 = new BigDecimal("53.23");
    public static final BigDecimal INSTALMENT_PAYMENT_4 = new BigDecimal("15.11");
    public static final Date INSTALMENT_PAYMENT_DATE_4 = parse("2017-01-01");

    public static final Long INSTALMENT_ID_5 = 5L;
    public static final Integer INSTALMENT_INDEX_5 = 2;
    public static final BigDecimal INSTALMENT_RPB_5 = new BigDecimal("21.12");
    public static final BigDecimal INSTALMENT_CAPITAL_5 = new BigDecimal("16.15");
    public static final BigDecimal INSTALMENT_INTEREST_5 = new BigDecimal("11.33");
    public static final BigDecimal INSTALMENT_PAYMENT_5 = new BigDecimal("13.11");
    public static final Date INSTALMENT_PAYMENT_DATE_5 = parse("2017-02-01");

    public static final Long INSTALMENT_ID_6 = 6L;
    public static final Integer INSTALMENT_INDEX_6 = 1;
    public static final BigDecimal INSTALMENT_RPB_6 = new BigDecimal("121.12");
    public static final BigDecimal INSTALMENT_CAPITAL_6 = new BigDecimal("116.15");
    public static final BigDecimal INSTALMENT_INTEREST_6 = new BigDecimal("111.33");
    public static final BigDecimal INSTALMENT_PAYMENT_6 = new BigDecimal("113.11");
    public static final Date INSTALMENT_PAYMENT_DATE_6 = parse("2017-06-01");

    public static final Long INSTALMENT_ID_7 = 7L;
    public static final Integer INSTALMENT_INDEX_7 = 2;
    public static final BigDecimal INSTALMENT_RPB_7 = new BigDecimal("221.12");
    public static final BigDecimal INSTALMENT_CAPITAL_7 = new BigDecimal("216.15");
    public static final BigDecimal INSTALMENT_INTEREST_7 = new BigDecimal("211.33");
    public static final BigDecimal INSTALMENT_PAYMENT_7 = new BigDecimal("213.11");
    public static final Date INSTALMENT_PAYMENT_DATE_7 = parse("2017-06-21");

    public static final Long INSTALMENT_ID_8 = 8L;
    public static final Integer INSTALMENT_INDEX_8 = 2;
    public static final BigDecimal INSTALMENT_RPB_8 = new BigDecimal("321.12");
    public static final BigDecimal INSTALMENT_CAPITAL_8 = new BigDecimal("316.15");
    public static final BigDecimal INSTALMENT_INTEREST_8 = new BigDecimal("311.33");
    public static final BigDecimal INSTALMENT_PAYMENT_8 = new BigDecimal("313.11");
    public static final Date INSTALMENT_PAYMENT_DATE_8 = parse("2017-04-11");

    public static final InstalmentDto TEST_EXT_INSTALMENT = aidaInstalment();

    public static final InstalmentDto INSTALMENT_DTO_4 = instalmentDto(INSTALMENT_ID_4, INSTALMENT_RPB_4, CALCULATOR_ID_4, INSTALMENT_INDEX_4,
            INSTALMENT_CAPITAL_4, INSTALMENT_INTEREST_4, INSTALMENT_PAYMENT_4, FINANCING_ELEMENT_DTO_1, FINANCING_PAYMENT_DTO_4, INSTALMENT_PAYMENT_DATE_4);

    public static final InstalmentDto INSTALMENT_DTO_5 = instalmentDto(INSTALMENT_ID_5, INSTALMENT_RPB_5, CALCULATOR_ID_5, INSTALMENT_INDEX_5,
            INSTALMENT_CAPITAL_5, INSTALMENT_INTEREST_5, INSTALMENT_PAYMENT_5, FINANCING_ELEMENT_DTO_2, FINANCING_PAYMENT_DTO_5, INSTALMENT_PAYMENT_DATE_5);

    public static final InstalmentDto INSTALMENT_DTO_6 = instalmentDto(INSTALMENT_ID_6, INSTALMENT_RPB_6, CALCULATOR_ID_1, INSTALMENT_INDEX_6,
            INSTALMENT_CAPITAL_6, INSTALMENT_INTEREST_6, INSTALMENT_PAYMENT_6, FINANCING_ELEMENT_DTO_2, FINANCING_PAYMENT_DTO_6, INSTALMENT_PAYMENT_DATE_6);

    public static final InstalmentDto INSTALMENT_DTO_7 = instalmentDto(INSTALMENT_ID_7, INSTALMENT_RPB_7, CALCULATOR_ID_2, INSTALMENT_INDEX_7,
            INSTALMENT_CAPITAL_7, INSTALMENT_INTEREST_7, INSTALMENT_PAYMENT_7, FINANCING_ELEMENT_DTO_2, FINANCING_PAYMENT_DTO_7, INSTALMENT_PAYMENT_DATE_7);

    public static final InstalmentDto INSTALMENT_DTO_8 = instalmentDto(INSTALMENT_ID_8, INSTALMENT_RPB_8, CALCULATOR_ID_3, INSTALMENT_INDEX_8,
            INSTALMENT_CAPITAL_8, INSTALMENT_INTEREST_8, INSTALMENT_PAYMENT_8, FINANCING_ELEMENT_DTO_2, FINANCING_PAYMENT_DTO_8, INSTALMENT_PAYMENT_DATE_8);

    public static final InstalmentDto REPURCHASE_INSTALMENT_DTO = instalmentDto(INSTALMENT_ID_8, INSTALMENT_RPB_8, CALCULATOR_ID_3, INSTALMENT_INDEX_8,
            INSTALMENT_CAPITAL_8, INSTALMENT_INTEREST_8, INSTALMENT_PAYMENT_8, REPURCHASE_FINANCING_ELEMENT_DTO, FINANCING_PAYMENT_DTO_8, INSTALMENT_PAYMENT_DATE_8);

    public static final InstalmentDto REGULAR_INSTALMENT_DTO = instalmentDto(INSTALMENT_ID_8, INSTALMENT_RPB_8, CALCULATOR_ID_3, INSTALMENT_INDEX_8,
            INSTALMENT_CAPITAL_8, INSTALMENT_INTEREST_8, INSTALMENT_PAYMENT_8, FINANCING_ELEMENT_DTO_1, FINANCING_PAYMENT_DTO_8, INSTALMENT_PAYMENT_DATE_8);

    public static final InstalmentDto PARTIALY_PAID_INSTALMENT_DTO = instalmentDto(INSTALMENT_ID_8, BigDecimal.ZERO, CALCULATOR_ID_3, INSTALMENT_INDEX_8,
            INSTALMENT_CAPITAL_8, INSTALMENT_INTEREST_8, INSTALMENT_PAYMENT_8, FINANCING_ELEMENT_DTO_1, FINANCING_PAYMENT_DTO_8, INSTALMENT_PAYMENT_DATE_8);

    private static InstalmentDto aidaInstalment() {
        final InstalmentDto aidaInstalment = new InstalmentDto();
        aidaInstalment.setId(FINANCIAL_MODEL_NO);
        aidaInstalment.setRemainingPayment(REMAINING_PAYMENT);
        aidaInstalment.setFinancialCalculationNumber(FINANCIAL_CALCULATION_NUMBER);
        return aidaInstalment;
    }

    private static InstalmentDto instalmentDto(final Long id, final BigDecimal rpb, final Long calcNumber, final Integer index, final BigDecimal capital,
                                               final BigDecimal interest, final BigDecimal payment, final FinancingElementDto financingElement,
                                               final FinancingPaymentDto financingPayment, final Date paymentDate) {
        final InstalmentDto instalment = new InstalmentDto();
        instalment.setId(id);
        instalment.setRemainingPayment(rpb);
        instalment.setFinancialCalculationNumber(calcNumber);
        instalment.setIndex(index);
        instalment.setCapital(capital);
        instalment.setInterest(interest);
        instalment.setPayment(payment);
        instalment.setFinancingElement(financingElement);
        instalment.setFinancingPayment(financingPayment);
        instalment.setPaymentDate(paymentDate);
        return instalment;
    }
}
