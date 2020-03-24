package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.contract.ContractFinancialSummaryDto;
import com.codersteam.alwin.core.api.model.contract.FinancialSummaryDto;
import com.codersteam.alwin.core.api.model.currency.Currency;

import java.util.HashMap;
import java.util.Map;

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.testdata.FinancialSummaryTestData.financialSummaryDto1;
import static com.codersteam.alwin.testdata.FinancialSummaryTestData.financialSummaryDto2;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_2;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_3;

/**
 * @author Tomasz Sliwinski
 */
public class ContractFinancialSummaryTestData {

    public static ContractFinancialSummaryDto contractFinancialSummaryDto1() {
        return contractFinancialSummaryDto(CONTRACT_NUMBER_INVOICE_3, 12L, currencyToSummary1());
    }

    public static ContractFinancialSummaryDto contractFinancialSummaryDto2() {
        return contractFinancialSummaryDto(CONTRACT_NUMBER_INVOICE_2, 0L, currencyToSummary2());
    }

    private static Map<Currency, FinancialSummaryDto> currencyToSummary1() {
        final Map<Currency, FinancialSummaryDto> currencyToSummary = new HashMap<>();
        currencyToSummary.put(PLN, financialSummaryDto1());
        currencyToSummary.put(EUR, financialSummaryDto2());
        return currencyToSummary;
    }

    private static Map<Currency, FinancialSummaryDto> currencyToSummary2() {
        final Map<Currency, FinancialSummaryDto> currencyToSummary = new HashMap<>();
        currencyToSummary.put(EUR, financialSummaryDto2());
        return currencyToSummary;
    }

    private static ContractFinancialSummaryDto contractFinancialSummaryDto(final String contractNo, final Long dpd,
                                                                           final Map<Currency, FinancialSummaryDto> currencyToSummary) {
        final ContractFinancialSummaryDto contractFinancialSummaryDto = new ContractFinancialSummaryDto();
        contractFinancialSummaryDto.setContractNo(contractNo);
        contractFinancialSummaryDto.setDpd(dpd);
        contractFinancialSummaryDto.setCurrencyToSummary(currencyToSummary);
        return contractFinancialSummaryDto;
    }
}
