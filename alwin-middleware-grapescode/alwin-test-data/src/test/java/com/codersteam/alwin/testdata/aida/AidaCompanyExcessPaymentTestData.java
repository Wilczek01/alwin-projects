package com.codersteam.alwin.testdata.aida;


import com.codersteam.aida.core.api.model.ExcessPaymentDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings("WeakerAccess")
public final class AidaCompanyExcessPaymentTestData {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final Long ID_4 = 4L;
    private static final Long ID_5 = 5L;

    public static final Long NOT_EXISTING_COMPANY_ID = -1L;
    public static final Long COMPANY_ID = 6333L;
    private static final Long COMPANY_ID_5 = 6334L;

    private static final Date PAYMENT_DATE_1 = parse("2018-07-04");
    private static final Date PAYMENT_DATE_2 = parse("2018-07-05");
    private static final Date PAYMENT_DATE_3 = parse("2018-07-06");
    private static final Date PAYMENT_DATE_4 = parse("2018-07-06");
    private static final Date PAYMENT_DATE_5 = parse("2018-07-07");

    private static final BigDecimal TOTAL_PAYMENT_1 = new BigDecimal("1291.50");
    private static final BigDecimal TOTAL_PAYMENT_2 = new BigDecimal("5290.24");
    private static final BigDecimal TOTAL_PAYMENT_3 = new BigDecimal("5000.50");
    private static final BigDecimal TOTAL_PAYMENT_4 = new BigDecimal("3500.50");
    private static final BigDecimal TOTAL_PAYMENT_5 = new BigDecimal("3501.50");

    private static final BigDecimal EXCESS_PAYMENT_1 = new BigDecimal("1291.50");
    private static final BigDecimal EXCESS_PAYMENT_2 = new BigDecimal("5290.24");
    private static final BigDecimal EXCESS_PAYMENT_3 = new BigDecimal("8045.10");
    private static final BigDecimal EXCESS_PAYMENT_4 = new BigDecimal("3500.50");
    private static final BigDecimal EXCESS_PAYMENT_5 = new BigDecimal("3501.50");

    private static final String DOC_ID_1 = "WB 97";
    private static final String DOC_ID_2 = "WB 141";
    private static final String DOC_ID_3 = "WB 169";
    private static final String DOC_ID_4 = "WB ODSZKODOWANIA";
    private static final String DOC_ID_5 = "WB 111";

    private static final String DOC_DESCRIPTION_1 = "WN 97";
    private static final String DOC_DESCRIPTION_2 = "WN 141";
    private static final String DOC_DESCRIPTION_3 = "WN 169";
    private static final String DOC_DESCRIPTION_4 = "PRZEKSIĘGOWANIE ODSZKODOWANIA";
    private static final String DOC_DESCRIPTION_5 = "WN 111";

    private static final String TYPE_1 = "WBLP";
    private static final String TYPE_2 = "WBMP";
    private static final String TYPE_3 = "WBPO";
    private static final String TYPE_4 = "WB";
    private static final String TYPE_5 = "WBPO";

    private static final String PARENT_TYPE_1 = "WBJ";
    private static final String PARENT_TYPE_2 = "WBJ";
    private static final String PARENT_TYPE_3 = "WBJ";
    private static final String PARENT_TYPE_4 = "WB";
    private static final String PARENT_TYPE_5 = "WBJ";

    private static final String CURRENCY_1 = "PLN";
    private static final String CURRENCY_2 = "EUR";
    private static final String CURRENCY_3 = "PLN";
    private static final String CURRENCY_4 = "EUR";
    private static final String CURRENCY_5 = "PLN";

    public static ExcessPaymentDto excessPaymentDto1() {
        return excessPaymentDto(ID_1, COMPANY_ID, PAYMENT_DATE_1, TOTAL_PAYMENT_1, EXCESS_PAYMENT_1, DOC_ID_1, DOC_DESCRIPTION_1, TYPE_1, PARENT_TYPE_1, CURRENCY_1);
    }

    public static ExcessPaymentDto excessPaymentDto2() {
        return excessPaymentDto(ID_2, COMPANY_ID, PAYMENT_DATE_2, TOTAL_PAYMENT_2, EXCESS_PAYMENT_2, DOC_ID_2, DOC_DESCRIPTION_2, TYPE_2, PARENT_TYPE_2, CURRENCY_2);
    }

    public static ExcessPaymentDto excessPaymentDto3() {
        return excessPaymentDto(ID_3, COMPANY_ID, PAYMENT_DATE_3, TOTAL_PAYMENT_3, EXCESS_PAYMENT_3, DOC_ID_3, DOC_DESCRIPTION_3, TYPE_3, PARENT_TYPE_3, CURRENCY_3);
    }

    public static ExcessPaymentDto excessPaymentDto4() {
        return excessPaymentDto(ID_4, COMPANY_ID, PAYMENT_DATE_4, TOTAL_PAYMENT_4, EXCESS_PAYMENT_4, DOC_ID_4, DOC_DESCRIPTION_4, TYPE_4, PARENT_TYPE_4, CURRENCY_4);
    }

    public static ExcessPaymentDto excessPaymentDto5() {
        return excessPaymentDto(ID_5, COMPANY_ID_5, PAYMENT_DATE_5, TOTAL_PAYMENT_5, EXCESS_PAYMENT_5, DOC_ID_5, DOC_DESCRIPTION_5, TYPE_5, PARENT_TYPE_5,
                CURRENCY_5);
    }

    public static List<ExcessPaymentDto> companyExcessPaymentsDto() {
        return asList(
                excessPaymentDto1(),
                excessPaymentDto2(),
                excessPaymentDto3(),
                excessPaymentDto4()
        );
    }

    private static ExcessPaymentDto excessPaymentDto(final Long id, final Long companyId, final Date paymentDate, final BigDecimal totalPayment, final BigDecimal
            excessPaymentValue, final String docId, final String docDescription, final String type, final String parentType, final String currency) {
        final ExcessPaymentDto excessPayment = new ExcessPaymentDto();
        excessPayment.setId(id);
        excessPayment.setCompanyId(companyId);
        excessPayment.setPaymentDate(paymentDate);
        excessPayment.setTotalPayment(totalPayment);
        excessPayment.setExcessPayment(excessPaymentValue);
        excessPayment.setDocId(docId);
        excessPayment.setDocDescription(docDescription);
        excessPayment.setType(type);
        excessPayment.setParentType(parentType);
        excessPayment.setCurrency(currency);
        return excessPayment;
    }
}
