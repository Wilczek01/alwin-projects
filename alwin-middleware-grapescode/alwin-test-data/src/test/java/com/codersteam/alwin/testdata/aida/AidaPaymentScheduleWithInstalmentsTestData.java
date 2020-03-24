package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.InstalmentDto;
import com.codersteam.aida.core.api.model.PaymentScheduleWithInstalmentsDto;

import java.util.ArrayList;
import java.util.List;

import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_CALC_TYPE_1;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_CALC_TYPE_2;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_CALC_TYPE_3;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_CALC_TYPE_4;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_1;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_2;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_3;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_ID_4;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_NAME_1;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_NAME_2;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_NAME_3;
import static com.codersteam.alwin.testdata.aida.AidaCalculatorTestData.CALCULATOR_NAME_4;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.FINANCIAL_MODEL_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.FINANCIAL_MODEL_NO_2;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.INSTALMENT_DTO_4;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.INSTALMENT_DTO_5;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.INSTALMENT_DTO_6;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.INSTALMENT_DTO_7;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.INSTALMENT_DTO_8;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.PARTIALY_PAID_INSTALMENT_DTO;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.REGULAR_INSTALMENT_DTO;
import static com.codersteam.alwin.testdata.aida.AidaInstalmentTestData.REPURCHASE_INSTALMENT_DTO;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("ALL")
public final class AidaPaymentScheduleWithInstalmentsTestData {

    private AidaPaymentScheduleWithInstalmentsTestData() {
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithInstalmentsDto1() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_1, CALCULATOR_CALC_TYPE_1, FINANCIAL_MODEL_NO_1, CALCULATOR_NAME_1, false, asList(INSTALMENT_DTO_4,
                INSTALMENT_DTO_5));
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithInstalmentsDto2() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_2, CALCULATOR_CALC_TYPE_2, FINANCIAL_MODEL_NO_2, CALCULATOR_NAME_2, true, asList(INSTALMENT_DTO_6,
                INSTALMENT_DTO_7));
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithInstalmentsDto3() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_3, CALCULATOR_CALC_TYPE_3, FINANCIAL_MODEL_NO_1, CALCULATOR_NAME_3, false, emptyList());
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithInstalmentsDto4() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_4, CALCULATOR_CALC_TYPE_4, FINANCIAL_MODEL_NO_2, CALCULATOR_NAME_4, false, asList(INSTALMENT_DTO_8));
    }

    public static PaymentScheduleWithInstalmentsDto inactivePaymentScheduleWithInstalmentsDto() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_4, CALCULATOR_CALC_TYPE_4, FINANCIAL_MODEL_NO_2, CALCULATOR_NAME_4, false,
                asList(INSTALMENT_DTO_8));
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithAllInstalmentsDto() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_2, CALCULATOR_CALC_TYPE_2, FINANCIAL_MODEL_NO_2, CALCULATOR_NAME_2, true,
                asList(REGULAR_INSTALMENT_DTO, INSTALMENT_DTO_4, INSTALMENT_DTO_5, INSTALMENT_DTO_6, INSTALMENT_DTO_7, INSTALMENT_DTO_8,
                        REPURCHASE_INSTALMENT_DTO));
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithNoRegularInstallmentsDto() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_1, CALCULATOR_CALC_TYPE_1, FINANCIAL_MODEL_NO_1, CALCULATOR_NAME_1, true,
                asList(REPURCHASE_INSTALMENT_DTO,
                        INSTALMENT_DTO_5));
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithNoInstallmentsDto() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_1, CALCULATOR_CALC_TYPE_1, FINANCIAL_MODEL_NO_1, CALCULATOR_NAME_1, true, emptyList());
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithInstallmentsPartialyPaidDto() {
        return paymentScheduleWithInstalments(CALCULATOR_ID_1, CALCULATOR_CALC_TYPE_1, FINANCIAL_MODEL_NO_1, CALCULATOR_NAME_1, true,
                asList(REPURCHASE_INSTALMENT_DTO, REGULAR_INSTALMENT_DTO, INSTALMENT_DTO_5, PARTIALY_PAID_INSTALMENT_DTO));
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWith37InstallmentsButOnePartiallyPaidDto() {
        return buildScheduleWithNumberOfInstallmentsAndOnePaid(36);
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWith36InstallmentsButOnePartiallyPaidDto() {
        return buildScheduleWithNumberOfInstallmentsAndOnePaid(35);
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWith26InstallmentsButOnePartiallyPaidDto() {
        return buildScheduleWithNumberOfInstallmentsAndOnePaid(25);
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWith25InstallmentsButOnePartiallyPaidDto() {
        return buildScheduleWithNumberOfInstallmentsAndOnePaid(24);
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWith13InstallmentsButOnePartiallyPaidDto() {
        return buildScheduleWithNumberOfInstallmentsAndOnePaid(12);
    }

    private static PaymentScheduleWithInstalmentsDto buildScheduleWithNumberOfInstallmentsAndOnePaid(final int installmentsNumber) {
        final List<InstalmentDto> installments = new ArrayList<>();
        installments.add(PARTIALY_PAID_INSTALMENT_DTO);
        for (int i = 0; i < installmentsNumber; i++) {
            installments.add(REGULAR_INSTALMENT_DTO);
        }
        return paymentScheduleWithInstalments(CALCULATOR_ID_1, CALCULATOR_CALC_TYPE_1, FINANCIAL_MODEL_NO_1, CALCULATOR_NAME_1, true, installments);
    }

    public static PaymentScheduleWithInstalmentsDto paymentScheduleWithInstalments(final Long calculatorId, final Long calculationType,
                                                                                   final Long calculatorFinanceModel, final String name,
                                                                                   final boolean active, final List<InstalmentDto> instalments) {
        final PaymentScheduleWithInstalmentsDto calculator = new PaymentScheduleWithInstalmentsDto();
        calculator.setId(calculatorId);
        calculator.setCalculationType(calculationType);
        calculator.setFinanceModel(calculatorFinanceModel);
        calculator.setName(name);
        calculator.setActive(active);
        calculator.setInstalments(instalments);
        return calculator;
    }
}
