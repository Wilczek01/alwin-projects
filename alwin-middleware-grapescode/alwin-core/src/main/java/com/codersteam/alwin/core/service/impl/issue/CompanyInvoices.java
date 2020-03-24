package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Holder dla faktur danej firmy
 *
 * @author Tomasz Sliwinski
 */
public class CompanyInvoices {

    /**
     * Idnetyfikator firmy
     */
    private final Long extCompanyId;

    /**
     * Faktury do dnia utworzenia zlecenia
     */
    private final List<AidaInvoiceWithCorrectionsDto> startInvoices;

    /**
     * Wszystkie faktury (startInvoices+additionalInvoices)
     */
    private final List<AidaInvoiceWithCorrectionsDto> allInvoices;

    CompanyInvoices(final Long extCompanyId, final List<AidaInvoiceWithCorrectionsDto> startInvoices, final List<AidaInvoiceWithCorrectionsDto> additionalInvoices) {
        this.extCompanyId = extCompanyId;
        this.startInvoices = startInvoices;
        allInvoices = new ArrayList<>(startInvoices);
        allInvoices.addAll(additionalInvoices);
    }

    public List<AidaInvoiceWithCorrectionsDto> getStartInvoices() {
        return startInvoices;
    }

    public List<AidaInvoiceWithCorrectionsDto> getAllInvoices() {
        return allInvoices;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }
}
