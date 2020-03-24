package com.codersteam.alwin.core.service.mapper.custom;

import com.codersteam.alwin.core.api.model.issue.InvoiceCorrectionDto;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.codersteam.alwin.core.service.mapper.IssueMappingContext.ISSUE_ID;
import static com.codersteam.alwin.core.util.DpdUtils.calculateInvoiceDpd;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Mapowanie faktur
 *
 * @author Tomasz Sliwinski
 */
public class InvoiceMapper extends CustomMapper<Invoice, InvoiceDto> {

    private final DateProvider dateProvider;

    public InvoiceMapper(final DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public void mapAtoB(final Invoice invoice, final InvoiceDto invoiceDto, final MappingContext mappingContext) {

        invoiceDto.setDpd(calculateInvoiceDpd(invoice.getCurrentBalance(), invoice.getRealDueDate(), invoice.getLastPaymentDate(),
                dateProvider.getCurrentDateStartOfDay()));

        setInvoiceCorrectionDiffsIfPresent(invoiceDto);

        final List<IssueInvoice> issueInvoices = invoice.getIssueInvoices();
        if (issueInvoices == null) {
            invoiceDto.setExcluded(Boolean.FALSE);
            invoiceDto.setIssueSubject(Boolean.FALSE);
            return;
        }
        final Object issueId = mappingContext.getProperty(ISSUE_ID);
        for (final IssueInvoice issueInvoice : issueInvoices) {
            if (Objects.equals(issueInvoice.getIssue().getId(), issueId)) {
                invoiceDto.setExcluded(issueInvoice.isExcluded());
                invoiceDto.setIssueSubject(issueInvoice.getIssueSubject());
                return;
            }
        }
        invoiceDto.setExcluded(Boolean.FALSE);
        invoiceDto.setIssueSubject(Boolean.FALSE);
    }

    private void setInvoiceCorrectionDiffsIfPresent(final InvoiceDto invoiceDto) {
        final List<InvoiceCorrectionDto> corrections = invoiceDto.getCorrections();
        if (isEmpty(corrections)) {
            return;
        }
        final BigDecimal rootInvoiceGrossAmount = invoiceDto.getGrossAmount();
        BigDecimal previousInvoiceGrossAmount = rootInvoiceGrossAmount;
        for (final InvoiceCorrectionDto correction : corrections) {
            final BigDecimal correctionGrossAmount = correction.getGrossAmount();
            correction.setRootInvoiceGrossAmountDiff(correctionGrossAmount.subtract(rootInvoiceGrossAmount));
            correction.setPreviousCorrectionGrossAmountDiff(correctionGrossAmount.subtract(previousInvoiceGrossAmount));
            previousInvoiceGrossAmount = correctionGrossAmount;
        }
    }
}
