package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.model.InvoiceEntryDto;
import com.codersteam.aida.core.api.model.InvoiceTypeDto;
import com.codersteam.alwin.common.search.criteria.InvoiceSearchCriteria;
import com.codersteam.alwin.common.sort.InvoiceSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.invoice.InvoicesWithTotalBalanceDto;
import com.codersteam.alwin.core.api.model.issue.InvoiceDto;
import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.issue.InvoiceService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.core.service.mapper.IssueMappingContext;
import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.jpa.issue.Invoice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.common.Page.emptyPage;
import static java.util.stream.Collectors.toList;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings({"CdiInjectionPointsInspection"})
@Stateless
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceDao invoiceDao;
    private IssueService issueService;
    private AlwinMapper mapper;
    private com.codersteam.aida.core.api.service.InvoiceService invoiceService;

    @SuppressWarnings("unused")
    public InvoiceServiceImpl() {
        // For deployment
    }

    @Inject
    public InvoiceServiceImpl(final InvoiceDao invoiceDao, final IssueService issueService, final AlwinMapper mapper, final AidaService aidaService) {
        this.invoiceDao = invoiceDao;
        this.issueService = issueService;
        this.mapper = mapper;
        this.invoiceService = aidaService.getInvoiceService();
    }

    public List<AidaInvoiceWithCorrectionsDto> getContractDueInvoices(final Long extCompanyId, final String contractNo, final Date dueDate) {
        // pobieramy faktury firmy z AIDA z zaległą datą płatności
        final List<AidaInvoiceWithCorrectionsDto> allCompanyDueInvoices = invoiceService.getBalanceStartInvoicesByCompanyId(extCompanyId, dueDate, false);

        // filtrujemy tylko przez nieopłacone i dla danego kontraktu
        return allCompanyDueInvoices.stream()
                .filter(invoice -> contractNo.equals(invoice.getContractNo()))
                .filter(invoice -> invoice.getBalanceOnDocument() != null && invoice.getBalanceOnDocument().signum() < 0)
                .collect(toList());
    }

    /**
     * Pobieranie faktur po identyfikatorze zlecenia
     *
     * @param issueId        - identyfikator zlecenia
     * @param searchCriteria - kryteria filtrowania
     * @param sortCriteria   - kryteria sortowania
     * @return faktury
     */
    protected List<InvoiceDto> findInvoiceForIssueId(final long issueId, final InvoiceSearchCriteria searchCriteria,
                                                     Map<InvoiceSortField, SortOrder> sortCriteria) {
        final List<Invoice> invoices = invoiceDao.findInvoiceForIssueId(issueId, searchCriteria, sortCriteria);
        return mapper.mapAsList(invoices, InvoiceDto.class, new IssueMappingContext(issueId));
    }

    @Override
    public InvoicesWithTotalBalanceDto findInvoicesWithTotalBalance(final long issueId, final InvoiceSearchCriteria searchCriteria,
                                                                    Map<InvoiceSortField, SortOrder> sortCriteria) {
        final IssueDto issue = issueService.findIssue(issueId);
        if (issue == null) {
            return new InvoicesWithTotalBalanceDto(emptyPage(), ZERO);
        }

        return getInvoicesWithTotalBalance(issueId, searchCriteria, sortCriteria);
    }

    @Override
    public List<InvoiceTypeDto> findAllInvoiceTypes() {
        return invoiceService.findAllInvoiceTypes();
    }

    @Override
    public List<InvoiceDto> findInvoicesForIssueId(final Long issueId) {
        final List<Invoice> invoices = invoiceDao.findInvoicesByIssueId(issueId);
        return mapper.mapAsList(invoices, InvoiceDto.class, new IssueMappingContext(issueId));
    }

    @Override
    public List<InvoiceEntryDto> findInvoiceEntries(final String invoiceNo) {
        return invoiceService.findInvoiceEntries(invoiceNo);
    }

    private InvoicesWithTotalBalanceDto getInvoicesWithTotalBalance(final long issueId, final InvoiceSearchCriteria searchCriteria,
                                                                    Map<InvoiceSortField, SortOrder> sortCriteria) {
        final List<InvoiceDto> invoices = findInvoiceForIssueId(issueId, searchCriteria, sortCriteria);
        final BigDecimal balance = invoices.stream()
                .filter(invoice -> !invoice.getExcluded())
                .map(InvoiceDto::getCurrentBalance)
                .filter(Objects::nonNull)
                .reduce(ZERO, BigDecimal::add);

        fillTypeLabels(invoices);

        return new InvoicesWithTotalBalanceDto(new Page<>(invoices, invoiceDao.findInvoiceForIssueIdCount(issueId, searchCriteria)), balance);
    }

    private void fillTypeLabels(final List<InvoiceDto> invoices) {
        final Map<Long, String> allInvoicesTypes = getAllTypes();
        invoices.forEach(invoice -> {
            invoice.setTypeLabel(allInvoicesTypes.get(invoice.getTypeId()));
            invoice.getCorrections().forEach(correction -> correction.setTypeLabel(allInvoicesTypes.get(correction.getTypeId())));
        });
    }

    private Map<Long, String> getAllTypes() {
        final List<InvoiceTypeDto> allInvoiceTypes = invoiceService.findAllInvoiceTypes();
        return allInvoiceTypes.stream().collect(Collectors.toMap(InvoiceTypeDto::getId, InvoiceTypeDto::getLabel));
    }
}
