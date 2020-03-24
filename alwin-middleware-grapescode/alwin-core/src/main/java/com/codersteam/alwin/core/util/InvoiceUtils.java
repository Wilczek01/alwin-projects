package com.codersteam.alwin.core.util;

import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.jpa.issue.Invoice;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Klasa pomocnicza do operacji na fakturach
 *
 * @author Tomasz Sliwinski
 */
public final class InvoiceUtils {

    private InvoiceUtils() {
    }

    /**
     * Wyznaczenie czy dany dokument jest przedmiotem zlecenia w momencie tworzenia zlecenia
     *
     * @param invoiceCurrentBalance - bieżące saldo dokumentu
     * @param issueStartDate        - data rozpoczęcia zlecenia
     * @param invoiceDueDate        - termin płatności faktury
     * @return true jeśli faktura jest przedmiotem zlecenia
     */
    public static boolean isIssueSubjectForNewIssue(final BigDecimal invoiceCurrentBalance, final Date issueStartDate, final Date invoiceDueDate) {
        return invoiceCurrentBalance != null && invoiceCurrentBalance.signum() < 0 && invoiceDueDate.before(issueStartDate);
    }

    /**
     * Wyznaczenie czy dany dokument jest przedmiotem zlecenia w trakcie jego trwania
     *
     * @param currentInvoiceBalance         - bieżące saldo dokumentu
     * @param issueStartDate                - data rozpoczęcia zlecenia
     * @param issueExpirationDate           - data zakończenia zlecenia
     * @param invoiceDueDate                - termin płatności faktury
     * @param currentDate                   - bieżąca data
     * @param includeDueInvoicesDuringIssue - czy dołączać zaległe dokumenty w trakcie trwania zlecenia
     * @return <code>true</code> jeśli faktura jest przedmiotem zlecenia
     */
    public static boolean isIssueSubject(final BigDecimal currentInvoiceBalance, final Date issueStartDate, final Date issueExpirationDate,
                                         final Date invoiceDueDate, final Date currentDate, final boolean includeDueInvoicesDuringIssue) {
        if (currentInvoiceBalance == null || currentInvoiceBalance.signum() >= 0 || invoiceDueDate.after(issueExpirationDate)) {
            return false;
        }
        return includeDueInvoicesDuringIssue ? invoiceDueDate.before(currentDate) : invoiceDueDate.before(issueStartDate);
    }

    /**
     * Ustawia kurs waluty jeśli nie jest ustawiony
     *
     * @param invoice      - faktura do modyfikacji
     * @param exchangeRate - kurs waluty do ustawienia
     */
    public static void setExchangeRateIfNotSet(final Invoice invoice, final BigDecimal exchangeRate) {
        if (invoice.getExchangeRate() == null) {
            invoice.setExchangeRate(exchangeRate);
        }
    }

    /**
     * Sprawdzenie, czy bieżące saldo faktury jest ujemne
     *
     * @param invoice - faktura
     * @return <code>true</code> jeśli faktura ma zaległości
     */
    public static boolean isNegativeInvoiceBalance(final InvoiceDto invoice) {
        return invoice.getCurrentBalance() != null && invoice.getCurrentBalance().signum() < 0;
    }
}
