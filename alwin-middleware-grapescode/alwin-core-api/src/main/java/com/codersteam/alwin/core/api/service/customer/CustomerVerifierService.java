package com.codersteam.alwin.core.api.service.customer;


import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto;
import com.codersteam.alwin.core.api.model.currency.Currency;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

/**
 * Serwis do weryfikacji klientów-dłużników
 */
@Local
// FIXME zmiana nazwy na bardziej odpowiadającą temu, co robi serwis albo rozdzielenie funkcjonalności
public interface CustomerVerifierService {

    /**
     * Zwraca klientów, dla których są spełnione wymagania biznesowe dotyczące możliwości założenia zlecenia
     *
     * @param customerInvoices - wejściowa lista id (leo) klientów do weryfikacji wraz z dokumentami
     * @return lista id (leo) klientów, dla których należy założyć zlecenie windykacyjne
     */
    boolean filterCustomersForIssueCreation(Long customerId, List<AidaSimpleInvoiceDto> customerInvoices);

    /**
     * Metoda zwracająca informację, czy dana firma jest wyłączona z obsługi
     *
     * @param extCustomerId - identyfikator firmy AIDA
     * @return czy klient wyłączony z obsługi
     */
    boolean isCompanyExcludedFromDebtCollection(Long extCustomerId);

    /**
     * Metoda zwraca informację, czy zadana kwota przekracza limit do tworzenia zlecenia
     *
     * @param balanceOnDocument - saldo na dokumencie
     * @param currency          - waluta faktury
     * @param exchangeRate      - kurs wymiany waluty
     * @param minBalanceStart   - minimalna wartość zadłużenia kwalifikująca dokument do rozpoczęcia zlecenia
     * @return <code>true</code> jeśli dokument kwalifikuje się do utworzenia zlecenia
     */
    boolean isBalanceEnoughToCreateIssue(BigDecimal balanceOnDocument, Currency currency, BigDecimal exchangeRate, BigDecimal minBalanceStart);

    /**
     * Metoda zwraca informację, czy saldo na dokumencie (pobierane są bieżące dane z AIDA) jest poniżej podanej wartości
     *
     * @param invoice         - dane faktury z systemu AIDA
     * @param minBalanceStart - wartość
     * @return <code>true</code> jeśli saldo na dokumencie jest poniżej minBalanceStart
     */
    boolean isInvoiceBalanceBelowMinBalanceStart(final AidaSimpleInvoiceDto invoice, final BigDecimal minBalanceStart);

    /**
     * Metoda zwraca informację, czy saldo na dokumencie (pobierane są bieżące dane z AIDA) jest poniżej podanej wartości
     *
     * @param invoice         - dane faktury z systemu AIDA
     * @param minBalanceStart - wartość
     * @return <code>true</code> jeśli saldo na dokumencie jest poniżej minBalanceStart
     */
    boolean isInvoiceBalanceBelowMinBalanceStart(final AidaInvoiceDto invoice, final BigDecimal minBalanceStart);
}
