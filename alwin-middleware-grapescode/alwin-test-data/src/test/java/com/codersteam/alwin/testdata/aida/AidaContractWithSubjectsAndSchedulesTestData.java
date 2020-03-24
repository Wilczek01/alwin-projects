package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.AidaContractWithSubjectsAndSchedulesDto;
import com.codersteam.aida.core.api.model.ContractSubjectDto;
import com.codersteam.aida.core.api.model.PaymentScheduleWithInstalmentsDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ACTIVE_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ACTIVE_2;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_INVOICED_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_INVOICED_2;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_SYMBOL_1;
import static com.codersteam.alwin.testdata.aida.AidaContractStatusTestData.CONTRACT_STATUS_SYMBOL_2;
import static com.codersteam.alwin.testdata.aida.AidaContractSubjectTestData.TEST_CONTRACT_SUBJECT_DTO_1090;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_VALUE_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_VALUE_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.END_DATE_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.END_DATE_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.FINANCIAL_MODEL_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.FINANCIAL_MODEL_NO_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.FINANCING_STATE_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.FINANCING_STATE_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.SIGN_DATE_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.SIGN_DATE_2;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.fillContractData;
import static com.codersteam.alwin.testdata.aida.AidaCurrencyTableTestData.CURRENCY_TABLE_NAME_1;
import static com.codersteam.alwin.testdata.aida.AidaCurrencyTableTestData.CURRENCY_TABLE_NAME_2;
import static com.codersteam.alwin.testdata.aida.AidaFinancingTypeTestData.FINANCING_TYPE_SYMBOL_1;
import static com.codersteam.alwin.testdata.aida.AidaFinancingTypeTestData.FINANCING_TYPE_SYMBOL_2;
import static com.codersteam.alwin.testdata.aida.AidaPaymentScheduleWithInstalmentsTestData.paymentScheduleWithInstalmentsDto1;
import static com.codersteam.alwin.testdata.aida.AidaPaymentScheduleWithInstalmentsTestData.paymentScheduleWithInstalmentsDto2;
import static com.codersteam.alwin.testdata.aida.AidaPaymentScheduleWithInstalmentsTestData.paymentScheduleWithInstalmentsDto3;
import static com.codersteam.alwin.testdata.aida.AidaPaymentScheduleWithInstalmentsTestData.paymentScheduleWithInstalmentsDto4;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * @author Tomasz Sliwinski
 */
public final class AidaContractWithSubjectsAndSchedulesTestData {

    private AidaContractWithSubjectsAndSchedulesTestData() {
    }

    public static AidaContractWithSubjectsAndSchedulesDto contractWithSubjectsAndSchedulesDto1() {
        return contractWithSubjectsAndSchedulesDto(CONTRACT_ID_1, CONTRACT_NO_1, SIGN_DATE_1, END_DATE_1, FINANCIAL_MODEL_NO_1,
                CONTRACT_STATUS_ID_1, CONTRACT_STATUS_SYMBOL_1, CONTRACT_STATUS_ACTIVE_1, CONTRACT_STATUS_INVOICED_1, CURRENCY_TABLE_NAME_1,
                FINANCING_TYPE_SYMBOL_1, FINANCING_STATE_1, CONTRACT_VALUE_1, singletonList(TEST_CONTRACT_SUBJECT_DTO_1090),
                asList(paymentScheduleWithInstalmentsDto1(), paymentScheduleWithInstalmentsDto3()));
    }

    public static AidaContractWithSubjectsAndSchedulesDto contractWithSubjectsAndSchedulesDto2() {
        return contractWithSubjectsAndSchedulesDto(CONTRACT_ID_2, CONTRACT_NO_2, SIGN_DATE_2, END_DATE_2, FINANCIAL_MODEL_NO_2,
                CONTRACT_STATUS_ID_2, CONTRACT_STATUS_SYMBOL_2, CONTRACT_STATUS_ACTIVE_2, CONTRACT_STATUS_INVOICED_2, CURRENCY_TABLE_NAME_2,
                FINANCING_TYPE_SYMBOL_2, FINANCING_STATE_2, CONTRACT_VALUE_2, singletonList(TEST_CONTRACT_SUBJECT_DTO_1090),
                asList(paymentScheduleWithInstalmentsDto2(), paymentScheduleWithInstalmentsDto4()));
    }

    private static AidaContractWithSubjectsAndSchedulesDto contractWithSubjectsAndSchedulesDto(final Long id, final String contractNo, final Date signDate,
                                                                                               final Date endDate, final Long financialModelNo,
                                                                                               final Long stateSymbol, final String stateDescription,
                                                                                               final Boolean active, final Boolean invoiced,
                                                                                               final String currency, final String financingType,
                                                                                               final String financingState, final BigDecimal contractValue,
                                                                                               final List<ContractSubjectDto> subjects,
                                                                                               final List<PaymentScheduleWithInstalmentsDto> paymentSchedules) {
        final AidaContractWithSubjectsAndSchedulesDto contract = new AidaContractWithSubjectsAndSchedulesDto();
        fillContractData(id, contractNo, signDate, endDate, financialModelNo, stateSymbol, stateDescription, active, invoiced, currency, financingType,
                financingState, contractValue, contract);
        contract.setSubjects(subjects);
        contract.setPaymentSchedules(paymentSchedules);
        return contract;
    }
}
