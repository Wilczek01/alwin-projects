package com.codersteam.alwin.validator;

import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Logika walidująca poprawność danych dla faktur zlecenia
 *
 * @author Michal Horowic
 */
@Stateless
public class InvoiceValidator {

    protected static final String MISSING_INVOICE_NUMBER = "Brak wymaganego parametru: numer faktury";

    /**
     * Sprawdzenie, czy został podany numer faktury
     *
     * @param invoiceNo - numer faktury z requestu
     */
    public void validateInvoiceNo(final String invoiceNo) {
        if (isNotBlank(invoiceNo)) {
            return;
        }
        throw new AlwinValidationException(MISSING_INVOICE_NUMBER);
    }
}
