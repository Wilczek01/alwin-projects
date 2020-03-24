package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.AidaContractDto;

import java.math.BigDecimal;
import java.util.Date;

import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ACTIVE_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ACTIVE_2;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_INVOICED_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_INVOICED_2;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_SYMBOL_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_SYMBOL_2;
import static com.codersteam.alwin.testdata.aida.AidaCurrencyTableTestData.CURRENCY_TABLE_NAME_1;
import static com.codersteam.alwin.testdata.aida.AidaCurrencyTableTestData.CURRENCY_TABLE_NAME_2;
import static com.codersteam.alwin.testdata.aida.AidaFinancingTypeTestData.FINANCING_TYPE_SYMBOL_1;
import static com.codersteam.alwin.testdata.aida.AidaFinancingTypeTestData.FINANCING_TYPE_SYMBOL_2;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings("WeakerAccess")
public class AidaContractTestData {

    private static final long ID = 1L;
    public static final String CONTRACT_NO = "TEST 12/34/56";
    private static final long FINANCIAL_MODEL_NO = 123456789L;

    public static final String NOT_EXISTING_CONTRACT_NO = "i'm not there";

    public static final Long CONTRACT_ID_1 = 1L;
    public static final String CONTRACT_NO_1 = "10";
    public static final Date SIGN_DATE_1 = parse("2016-08-23");
    public static final Date END_DATE_1 = parse("2017-08-30");
    public static final Long FINANCIAL_MODEL_NO_1 = 1L;
    public static final String FINANCING_STATE_1 = "Wykup z regresem";
    public static final BigDecimal CONTRACT_VALUE_1 = BigDecimal.valueOf(1000.23);

    public static final Long CONTRACT_ID_2 = 2L;
    public static final String CONTRACT_NO_2 = "100";
    public static final Date SIGN_DATE_2 = parse("2016-08-22");
    public static final Date END_DATE_2 = parse("2016-08-29");
    public static final Long FINANCIAL_MODEL_NO_2 = 2L;
    public static final String FINANCING_STATE_2 = "Finansowanie własne";
    public static final BigDecimal CONTRACT_VALUE_2 = BigDecimal.valueOf(435000.15);

    public static final AidaContractDto TEST_EXT_CONTRACT = aidaContract();

    public static AidaContractDto aidaContract(final String contractNo) {
        final AidaContractDto contract = aidaContract();
        contract.setContractNo(contractNo);
        return contract;
    }

    private static AidaContractDto aidaContract() {
        final AidaContractDto contract = new AidaContractDto();
        contract.setId(ID);
        contract.setFinancialModelNo(FINANCIAL_MODEL_NO);
        contract.setContractNo(CONTRACT_NO);
        return contract;
    }

    public static AidaContractDto simpleAidaContractDto1() {
        final AidaContractDto contract = new AidaContractDto();
        fillContractData(CONTRACT_ID_1, CONTRACT_NO_1, SIGN_DATE_1, END_DATE_1, FINANCIAL_MODEL_NO_1,
                CONTRACT_STATUS_ID_1, CONTRACT_STATUS_SYMBOL_1, CONTRACT_STATUS_ACTIVE_1, CONTRACT_STATUS_INVOICED_1, CURRENCY_TABLE_NAME_1,
                FINANCING_TYPE_SYMBOL_1, FINANCING_STATE_1, CONTRACT_VALUE_1, contract);
        return contract;
    }

    public static AidaContractDto simpleAidaContractDto2() {
        final AidaContractDto contract = new AidaContractDto();
        fillContractData(CONTRACT_ID_2, CONTRACT_NO_2, SIGN_DATE_2, END_DATE_2, FINANCIAL_MODEL_NO_2,
                CONTRACT_STATUS_ID_2, CONTRACT_STATUS_SYMBOL_2, CONTRACT_STATUS_ACTIVE_2, CONTRACT_STATUS_INVOICED_2, CURRENCY_TABLE_NAME_2,
                FINANCING_TYPE_SYMBOL_2, FINANCING_STATE_2, CONTRACT_VALUE_2, contract);
        return contract;
    }


    public static void fillContractData(final Long id, final String contractNo, final Date signDate, final Date endDate, final Long financialModelNo,
                                        final Long stateSymbol, final String stateDescription, final Boolean active, final Boolean invoiced,
                                        final String currency, final String financingType, final String financingState, final BigDecimal contractValue,
                                        final AidaContractDto contract) {
        contract.setId(id);
        contract.setContractNo(contractNo);
        contract.setSignDate(signDate);
        contract.setEndDate(endDate);
        contract.setFinancialModelNo(financialModelNo);
        contract.setStateSymbol(stateSymbol);
        contract.setStateDescription(stateDescription);
        contract.setActive(active);
        contract.setInvoiced(invoiced);
        contract.setCurrency(currency);
        contract.setFinancingType(financingType);
        contract.setFinancingState(financingState);
        contract.setContractValue(contractValue);
    }
}
