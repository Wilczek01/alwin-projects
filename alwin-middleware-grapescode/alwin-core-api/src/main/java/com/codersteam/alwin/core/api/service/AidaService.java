package com.codersteam.alwin.core.api.service;

import com.codersteam.aida.core.api.service.*;

import javax.ejb.Local;

/**
 * Dostęp do zdalnych serwisów z systemu AIDA
 *
 * @author Michal Horowic
 */
@Local
public interface AidaService {

    InvoiceService getInvoiceService();

    ContractService getContractService();

    ContractSubjectService getContractSubjectService();

    CompanyService getCompanyService();

    PersonService getPersonService();

    SettlementService getSettlementService();

    /**
     * Pobranie serwisu AIDA do pobierania nadpłat klienta
     *
     * @return serwis AIDA do pobierania nadpłat klienta
     */
    ExcessPaymentService getExcessPaymentService();

    /**
     * Pobranie serwisu AIDA do obsługi zaangażowania firmy
     *
     * @return serwis AIDA do obsługi zaangażowania firmy
     */
    InvolvementService getInvolvementService();

    /**
     * Pobranie serwisu AIDA do obsługi segmentu firmy
     *
     * @return serwis AIDA do obsługi segmentu firmy
     */
    SegmentService getSegmentService();

    /**
     * Pobieranie serwisu AIDA do obsługi kursu wymiany walut
     *
     * @return serwis AIDA do obsługi kursu wymiany walut
     */
    CurrencyExchangeRateService getCurrencyExchangeRateService();
}
