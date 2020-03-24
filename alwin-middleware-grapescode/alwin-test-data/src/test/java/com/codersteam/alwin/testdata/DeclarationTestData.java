package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.declaration.DeclarationDto;
import com.codersteam.alwin.jpa.activity.Declaration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Michal Horowic
 */
public class DeclarationTestData {

    public static final String DATE_1 = "2017-09-10";
    public static final String DATE_2 = "2017-09-20";
    public static final BigDecimal AMOUNT_1 = new BigDecimal("100.00");
    public static final BigDecimal AMOUNT_2 = new BigDecimal("200.00");
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;

    public static final Date DECLARED_PAYMENT_DATE_1 = parse("2017-09-30 00:00:00.000000");
    private static final Long DECLARATION_ID_1 = 1L;
    private static final BigDecimal CASH_PAID_PLN_1 = BigDecimal.TEN;
    private static final BigDecimal CASH_PAID_EUR_1 = BigDecimal.ZERO;
    private static final Date DECLARATION_DATE_1 = parse("2017-09-21 00:00:00.000000");
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_PLN_1 = new BigDecimal("53432.21");
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_EUR_1 = BigDecimal.ZERO;
    private static final Boolean MONITORED_1 = true;
    public static final DeclarationDto DECLARATION_1_DTO = declarationDto1();
    private static final Boolean PAID_1 = null;
    public static final Declaration DECLARATION_1 = declaration(DECLARATION_ID_1, CASH_PAID_PLN_1, CASH_PAID_EUR_1, DECLARATION_DATE_1,
            DECLARED_PAYMENT_AMOUNT_PLN_1, DECLARED_PAYMENT_AMOUNT_EUR_1, DECLARED_PAYMENT_DATE_1, MONITORED_1, PAID_1);
    public static final Declaration DECLARATION_1_WITHOUT_ID = declaration(null, CASH_PAID_PLN_1, CASH_PAID_EUR_1, DECLARATION_DATE_1,
            DECLARED_PAYMENT_AMOUNT_PLN_1, DECLARED_PAYMENT_AMOUNT_EUR_1, DECLARED_PAYMENT_DATE_1, MONITORED_1, PAID_1);

    public static final Declaration UNPAID_DECLARATION_1 = declaration(DECLARATION_ID_1, BigDecimal.ZERO, BigDecimal.ZERO, DECLARATION_DATE_1,
            DECLARED_PAYMENT_AMOUNT_PLN_1, DECLARED_PAYMENT_AMOUNT_EUR_1, TestDateUtils.parse(LocalDate.now().minusDays(1L).toString()), MONITORED_1, PAID_1);

    public static final Declaration PAID_DECLARATION_1 = declaration(DECLARATION_ID_1, DECLARED_PAYMENT_AMOUNT_PLN_1, DECLARED_PAYMENT_AMOUNT_EUR_1,
            DECLARATION_DATE_1, DECLARED_PAYMENT_AMOUNT_PLN_1, DECLARED_PAYMENT_AMOUNT_EUR_1, TestDateUtils.parse(LocalDate.now().minusDays(1L).toString()),
            MONITORED_1, PAID_1);

    private static final Long DECLARATION_ID_2 = 2L;
    private static final BigDecimal CASH_PAID_PLN_2 = new BigDecimal("1000.50");
    private static final BigDecimal CASH_PAID_EUR_2 = BigDecimal.ZERO;
    private static final Date DECLARATION_DATE_2 = parse("2017-08-05 00:00:00.000000");
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_PLN_2 = new BigDecimal("2000");
    private static final Date DECLARED_PAYMENT_DATE_2 = parse("2017-10-14 00:00:00.000000");
    private static final Boolean MONITORED_2 = false;
    private static final Boolean PAID_2 = false;
    public static final Declaration DECLARATION_2 = declaration(DECLARATION_ID_2, CASH_PAID_PLN_2, CASH_PAID_EUR_2, DECLARATION_DATE_2,
            DECLARED_PAYMENT_AMOUNT_PLN_2, DECLARED_PAYMENT_AMOUNT_EUR_1, DECLARED_PAYMENT_DATE_2, MONITORED_2, PAID_2);
    public static final Declaration DECLARATION_2_WITHOUT_ID = declaration(null, CASH_PAID_PLN_2, CASH_PAID_EUR_2, DECLARATION_DATE_2,
            DECLARED_PAYMENT_AMOUNT_PLN_2, DECLARED_PAYMENT_AMOUNT_EUR_1, DECLARED_PAYMENT_DATE_2, MONITORED_2, PAID_2);

    private static final Long DECLARATION_ID_123 = 123L;
    private static final BigDecimal CASH_PAID_PLN_123 = new BigDecimal("2345.34");
    private static final BigDecimal CASH_PAID_EUR_123 = ZERO;
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_PLN_123 = new BigDecimal("43323.12");
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_EUR_123 = ZERO;
    private static final Date DECLARATION_DATE_123 = parse("2017-10-01 00:00:00.000000");
    private static final Date DECLARED_PAYMENT_DATE_123 = parse("2017-08-01 00:00:00.000000");
    private static final Boolean MONITORED_123 = true;
    private static final Boolean PAID_123 = false;

    public static final Declaration DECLARATION_123 = declaration(DECLARATION_ID_123, CASH_PAID_PLN_123, CASH_PAID_EUR_123, DECLARATION_DATE_123,
            DECLARED_PAYMENT_AMOUNT_PLN_123, DECLARED_PAYMENT_AMOUNT_EUR_123, DECLARED_PAYMENT_DATE_123, MONITORED_123, PAID_123);

    private static final Long DECLARATION_ID_124 = 124L;
    private static final BigDecimal CASH_PAID_PLN_124 = new BigDecimal("121243.12");
    private static final BigDecimal CASH_PAID_EUR_124 = ZERO;
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_PLN_124 = new BigDecimal("121243.12");
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_EUR_124 = ZERO;
    private static final Date DECLARATION_DATE_124 = parse("2017-08-01 00:00:00.000000");
    private static final Date DECLARED_PAYMENT_DATE_124 = parse("2017-07-01 00:00:00.000000");
    private static final Boolean MONITORED_124 = false;
    private static final Boolean PAID_124 = true;
    public static final Declaration DECLARATION_124 = declaration(DECLARATION_ID_124, CASH_PAID_PLN_124, CASH_PAID_EUR_124, DECLARATION_DATE_124,
            DECLARED_PAYMENT_AMOUNT_PLN_124, DECLARED_PAYMENT_AMOUNT_EUR_124, DECLARED_PAYMENT_DATE_124, MONITORED_124, PAID_124);
    private static final Long DECLARATION_ID_128 = 128L;
    private static final BigDecimal CASH_PAID_PLN_128 = new BigDecimal("121243.12");
    private static final BigDecimal CASH_PAID_EUR_128 = ZERO;
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_PLN_128 = new BigDecimal("121243.12");
    private static final BigDecimal DECLARED_PAYMENT_AMOUNT_EUR_128 = ZERO;
    private static final Date DECLARATION_DATE_128 = parse("2017-08-02 00:00:00.000000");
    private static final Date DECLARED_PAYMENT_DATE_128 = parse("2017-07-03 00:00:00.000000");
    private static final Boolean MONITORED_128 = false;
    private static final Boolean PAID_128 = false;
    public static final Declaration DECLARATION_128 = declaration(DECLARATION_ID_128, CASH_PAID_PLN_128, CASH_PAID_EUR_128, DECLARATION_DATE_128,
            DECLARED_PAYMENT_AMOUNT_PLN_128, DECLARED_PAYMENT_AMOUNT_EUR_128, DECLARED_PAYMENT_DATE_128, MONITORED_128, PAID_128);


    private DeclarationTestData() {
    }

    public static DeclarationDto declarationDto1() {
        return declarationDto(DECLARATION_ID_1, CASH_PAID_PLN_1, CASH_PAID_EUR_1, DECLARATION_DATE_1, DECLARED_PAYMENT_AMOUNT_PLN_1,
                DECLARED_PAYMENT_AMOUNT_EUR_1, DECLARED_PAYMENT_DATE_1, MONITORED_1);
    }

    public static Declaration declaration(final Long id, final BigDecimal cashPaidPLN, final BigDecimal cashPaidEUR, final Date declarationDate, final
    BigDecimal declaredPaymentAmountPLN, final BigDecimal declaredPaymentAmountEUR, final Date declaredPaymentDate, final Boolean monitored,
                                          final Boolean paid) {
        final Declaration declaration = new Declaration();
        declaration.setId(id);
        declaration.setCashPaidPLN(cashPaidPLN);
        declaration.setCashPaidEUR(cashPaidEUR);
        declaration.setDeclarationDate(declarationDate);
        declaration.setDeclaredPaymentAmountPLN(declaredPaymentAmountPLN);
        declaration.setDeclaredPaymentAmountEUR(declaredPaymentAmountEUR);
        declaration.setDeclaredPaymentDate(declaredPaymentDate);
        declaration.setMonitored(monitored);
        declaration.setPaid(paid);
        return declaration;
    }

    public static DeclarationDto declarationDto(final Long id, final BigDecimal cashPaidPLN, final BigDecimal cashPaidEUR, final Date declarationDate,
                                                final BigDecimal declaredPaymentAmountPLN, final BigDecimal declaredPaymentAmountEUR,
                                                final Date declaredPaymentDate, final Boolean monitored) {
        final DeclarationDto declarationDto = new DeclarationDto();
        declarationDto.setId(id);
        declarationDto.setCashPaidPLN(cashPaidPLN);
        declarationDto.setCashPaidEUR(cashPaidEUR);
        declarationDto.setDeclarationDate(declarationDate);
        declarationDto.setDeclaredPaymentAmountPLN(declaredPaymentAmountPLN);
        declarationDto.setDeclaredPaymentAmountEUR(declaredPaymentAmountEUR);
        declarationDto.setDeclaredPaymentDate(declaredPaymentDate);
        declarationDto.setMonitored(monitored);
        return declarationDto;
    }

    public static DeclarationDto declarationDto(final String declaredPaymentDate) {
        final DeclarationDto declarationDto = declarationDto1();
        if (isBlank(declaredPaymentDate)) {
            declarationDto.setDeclaredPaymentDate(null);
        } else {
            declarationDto.setDeclaredPaymentDate(TestDateUtils.parse(declaredPaymentDate));
        }
        return declarationDto;
    }

    public static Declaration declarationWithPaymentDate(String date) {
        final Declaration declaration = new Declaration();
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(date));
        return declaration;
    }

    public static Declaration declarationWithDeclarationDate(String date) {
        final Declaration declaration = new Declaration();
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(DATE_1));
        declaration.setDeclarationDate(TestDateUtils.parse(date));
        return declaration;
    }

    public static Declaration declarationWithAmountPLN(BigDecimal amount) {
        final Declaration declaration = new Declaration();
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(DATE_1));
        declaration.setDeclarationDate(TestDateUtils.parse(DATE_2));
        declaration.setDeclaredPaymentAmountPLN(amount);
        declaration.setDeclaredPaymentAmountEUR(AMOUNT_1);
        return declaration;
    }

    public static Declaration declarationWithAmountEUR(BigDecimal amount) {
        final Declaration declaration = new Declaration();
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(DATE_1));
        declaration.setDeclarationDate(TestDateUtils.parse(DATE_2));
        declaration.setDeclaredPaymentAmountPLN(AMOUNT_1);
        declaration.setDeclaredPaymentAmountEUR(amount);
        return declaration;
    }

    public static Declaration declarationWithId(Long id) {
        final Declaration declaration = new Declaration();
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(DATE_1));
        declaration.setDeclarationDate(TestDateUtils.parse(DATE_2));
        declaration.setDeclaredPaymentAmountPLN(AMOUNT_1);
        declaration.setDeclaredPaymentAmountEUR(AMOUNT_2);
        declaration.setId(id);
        return declaration;
    }

    public static Declaration declaration(String date) {
        final Declaration declaration = new Declaration();
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(date));
        declaration.setDeclarationDate(TestDateUtils.parse(date));
        return declaration;
    }

    public static Declaration declaration(Long id) {
        final Declaration declaration = new Declaration();
        declaration.setDeclarationDate(TestDateUtils.parse(DATE_1));
        declaration.setDeclaredPaymentDate(TestDateUtils.parse(DATE_2));
        declaration.setDeclaredPaymentAmountPLN(AMOUNT_1);
        declaration.setDeclaredPaymentAmountEUR(ZERO);
        declaration.setCashPaidPLN(ZERO);
        declaration.setCashPaidEUR(ZERO);
        declaration.setId(id);
        return declaration;
    }
}
