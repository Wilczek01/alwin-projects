package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.model.*;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.core.util.DateUtils;

import java.math.BigDecimal;
import java.util.*;

import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.allInvoiceTypeDtos;
import static java.util.Collections.emptyList;

public class InvoiceServiceMock implements InvoiceService {

    /**
     * Przeterminowane faktury dla klientów
     */
    public static Map<Long, Collection<AidaInvoiceDto>> OUTSTANDING_INVOICES = new HashMap<>();

    /**
     * Faktury z aktualnym saldem
     */
    public static Map<Long, AidaInvoiceDto> INVOICES_WITH_BALANCES_BY_INVOICE_ID = new HashMap<>();

    /**
     * Faktury dla podanej daty na potrzeby testów
     */
    public static Map<String, List<AidaSimpleInvoiceDto>> SIMPLE_INVOICES_BY_DUE_DATE = new HashMap<>();

    /**
     * Faktury do wyliczeń portfela klienta na potrzeby testów - do czasu otwarcia zlecenia
     */
    public static Map<Long, List<AidaInvoiceWithCorrectionsDto>> INVOICES_FOR_BALANCE_CALCULATION_START = new HashMap<>();

    /**
     * Faktury do wyliczeń portfela klienta na potrzeby testów - w trakcie trawania zlecenia
     */
    public static Map<List, List<AidaInvoiceWithCorrectionsDto>> INVOICES_FOR_BALANCE_CALCULATION_ADDITIONAL = new HashMap<>();

    /**
     * Mapa: numer faktury -> lista pozycji faktury
     */
    public static Map<String, List<InvoiceEntryDto>> INVOICE_ENTRIES = new HashMap<>();

    public static void reset() {
        OUTSTANDING_INVOICES = new HashMap<>();
        SIMPLE_INVOICES_BY_DUE_DATE = new HashMap<>();
        INVOICES_FOR_BALANCE_CALCULATION_START = new HashMap<>();
        INVOICES_WITH_BALANCES_BY_INVOICE_ID = new HashMap<>();
        INVOICE_ENTRIES = new HashMap<>();
    }

    @Override
    public List<AidaSimpleInvoiceDto> getSimpleInvoicesWithActiveContractByDueDate(Date dueDateFrom, Date dueDateTo, BigDecimal maximumBalance) {
        final String dueDateString = DateUtils.dateToLocalDate(dueDateTo).format(DateProviderMock.FORMATTER);
        return SIMPLE_INVOICES_BY_DUE_DATE.get(dueDateString);
    }

    @Override
    public AidaInvoiceDto getInvoiceWithBalance(final Long invoiceId) {
        return INVOICES_WITH_BALANCES_BY_INVOICE_ID.get(invoiceId);
    }

    @Override
    public List<AidaInvoiceWithCorrectionsDto> getBalanceStartInvoicesByCompanyId(Long comapnyId, Date issueStartDate, boolean onlyActiveContracts) {
        final List<AidaInvoiceWithCorrectionsDto> invoices = INVOICES_FOR_BALANCE_CALCULATION_START.get(comapnyId);
        return invoices == null ? emptyList() : invoices;
    }

    @Override
    public List<AidaInvoiceWithCorrectionsDto> findInvoicesWithActiveContractByCompanyId(Long comapnyId) {
        final List<AidaInvoiceWithCorrectionsDto> invoices = INVOICES_FOR_BALANCE_CALCULATION_ADDITIONAL.get(comapnyId);
        return invoices == null ? emptyList() : invoices;
    }

    @Override
    public List<AidaInvoiceWithCorrectionsDto> getBalanceAdditionalInvoicesWithActiveContractByCompanyId(Long companyId, Date issueStartDate, Date issueExpirationDate) {
        final List<AidaInvoiceWithCorrectionsDto> invoices = INVOICES_FOR_BALANCE_CALCULATION_ADDITIONAL.get(companyId);
        return invoices == null ? emptyList() : invoices;
    }

    @Override
    public List<InvoiceTypeDto> findAllInvoiceTypes() {
        return allInvoiceTypeDtos();
    }

    @Override
    public List<InvoiceEntryDto> findInvoiceEntries(String invoiceNo) {
        final List<InvoiceEntryDto> invoiceEntryDtos = INVOICE_ENTRIES.get(invoiceNo);
        return invoiceEntryDtos != null ? invoiceEntryDtos : emptyList();
    }
}
