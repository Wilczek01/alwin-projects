package com.codersteam.alwin.core.local;

import com.codersteam.aida.core.api.model.AidaInvoiceDto;
import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.service.SegmentService;
import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.core.util.InvoiceUtils;
import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import com.codersteam.alwin.jpa.issue.IssueType;
import org.apache.commons.collections.CollectionUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.util.InvoiceUtils.isIssueSubject;
import static com.codersteam.alwin.core.util.IssueUtils.getCompanySegmentFromIssue;
import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Stateless
public class LocalInvoiceServiceImpl implements LocalInvoiceService {

    private InvoiceDao invoiceDao;
    private IssueTypeConfigurationDao issueTypeConfigurationDao;
    private SegmentService segmentService;
    private AlwinMapper mapper;
    private DateProvider dateProvider;

    @SuppressWarnings("unused")
    public LocalInvoiceServiceImpl() {
    }

    @SuppressWarnings({"unused", "CdiInjectionPointsInspection"})
    @Inject
    public LocalInvoiceServiceImpl(final InvoiceDao invoiceDao, final IssueTypeConfigurationDao issueTypeConfigurationDao,
                                   final AidaService aidaService, final AlwinMapper mapper, final DateProvider dateProvider) {
        this.invoiceDao = invoiceDao;
        this.issueTypeConfigurationDao = issueTypeConfigurationDao;
        this.segmentService = aidaService.getSegmentService();
        this.mapper = mapper;
        this.dateProvider = dateProvider;
    }

    @Override
    public List<IssueInvoice> prepareInvoices(final Collection<AidaInvoiceWithCorrectionsDto> aidaInvoiceDtos, final Issue issue,
                                              final Set<String> contractsOutOfService) {
        boolean isIncludeDebtInvoicesDuringIssue = isIncludeDueInvoicesDuringIssue(issue);
        return aidaInvoiceDtos.stream()
                .filter(aidaInvoiceDto -> !contractsOutOfService.contains(aidaInvoiceDto.getContractNo()))
                .map(aidaInvoiceDto -> prepareIssueInvoice(issue, aidaInvoiceDto, isIncludeDebtInvoicesDuringIssue))
                .collect(Collectors.toList());
    }

    @Override
    public IssueInvoice prepareIssueInvoice(final Issue issue, final AidaInvoiceWithCorrectionsDto invoiceDto, final boolean includeDueInvoicesDuringIssue) {
        final Invoice invoice = prepareInvoiceWithCorrections(invoiceDto);
        return prepareIssueInvoice(issue, invoice, includeDueInvoicesDuringIssue);
    }

    @Override
    public boolean isIncludeDueInvoicesDuringIssue(final Issue issue) {
        final IssueType issueType = issue.getIssueType();
        final IssueTypeName issueTypeName = issueType.getName();
        return issueTypeConfigurationDao.findIncludeDebtInvoicesDuringIssueByTypeNameAndSegment(issueTypeName, getCompanySegment(issue));
    }

    private Segment getCompanySegment(final Issue issue) {
        final Segment segment = getCompanySegmentFromIssue(issue);
        if (segment != null) {
            return segment;
        }
        return mapper.map(segmentService.findCompanySegment(getExtCompanyId(issue)), Segment.class);
    }

    protected IssueInvoice prepareIssueInvoice(final Issue issue, final Invoice invoice, final boolean includeDueInvoicesDuringIssue) {
        final IssueInvoice issueInvoice = new IssueInvoice();
        issueInvoice.setInvoice(invoice);
        issueInvoice.setIssue(issue);
        issueInvoice.setAdditionDate(new Date());
        issueInvoice.setExcluded(false);
        issueInvoice.setIssueSubject(isIssueSubject(invoice.getCurrentBalance(), issue.getStartDate(), issue.getExpirationDate(), invoice.getRealDueDate(),
                dateProvider.getCurrentDateStartOfDay(), includeDueInvoicesDuringIssue));
        issueInvoice.setInclusionBalance(invoice.getCurrentBalance());
        issueInvoice.setFinalBalance(invoice.getCurrentBalance());
        invoice.setIssueInvoices(singletonList(issueInvoice));
        return issueInvoice;
    }

    private List<Invoice> prepareInvoiceCorrections(final List<AidaInvoiceDto> corrections) {
        if (CollectionUtils.isEmpty(corrections)) {
            return emptyList();
        }

        final List<Invoice> invoices = new ArrayList<>(corrections.size());

        int order = 0;
        for (final AidaInvoiceDto invoiceDto : corrections) {
            invoices.add(prepareInvoiceCorrection(invoiceDto, ++order));
        }

        return invoices;
    }

    private Invoice prepareInvoiceWithCorrections(final AidaInvoiceWithCorrectionsDto invoiceDto) {
        final Optional<Invoice> entity = invoiceDao.findInvoiceByNumber(invoiceDto.getNumber());
        final List<Invoice> corrections = prepareInvoiceCorrections(invoiceDto.getInvoiceCorrections());
        if (entity.isPresent()) {
            return updateInvoice(entity.get(), corrections, invoiceDto);
        }
        return createInvoiceWithCorrections(invoiceDto, corrections);
    }

    private Invoice updateInvoice(final Invoice invoice, final List<Invoice> corrections, final AidaInvoiceWithCorrectionsDto invoiceDto) {
        invoice.setCurrentBalance(invoiceDto.getBalanceOnDocument());
        invoice.setPaid(invoiceDto.getPaid());
        invoice.getCorrections().clear();
        invoice.getCorrections().addAll(corrections);
        invoice.setIssueDate(invoiceDto.getIssueDate());
        InvoiceUtils.setExchangeRateIfNotSet(invoice, invoiceDto.getExchangeRate());
        return invoiceDao.update(invoice);
    }

    private Invoice prepareInvoiceCorrection(final AidaInvoiceDto invoiceDto, final Integer correctionOrder) {
        final Optional<Invoice> entity = invoiceDao.findInvoiceByNumber(invoiceDto.getNumber());
        return entity.orElseGet(() -> createInvoiceCorrection(invoiceDto, correctionOrder));
    }

    private Invoice createInvoiceWithCorrections(final AidaInvoiceDto invoiceDto, final List<Invoice> corrections) {
        final Invoice invoice = mapInvoiceDto(invoiceDto, false, null);
        invoice.setCorrections(corrections);
        invoiceDao.create(invoice);
        return invoice;
    }

    private Invoice createInvoiceCorrection(final AidaInvoiceDto invoiceDto, final Integer correctionOrder) {
        final Invoice invoice = mapInvoiceDto(invoiceDto, true, correctionOrder);
        invoiceDao.create(invoice);
        return invoice;
    }

    private Invoice mapInvoiceDto(final AidaInvoiceDto invoiceDto, final boolean correction, final Integer correctionOrder) {
        final Invoice invoice = new Invoice();
        invoice.setLastPaymentDate(invoiceDto.getLastPaymentDate());
        invoice.setNetAmount(invoiceDto.getNetAmount());
        invoice.setGrossAmount(invoiceDto.getGrossAmount());
        invoice.setCurrency(invoiceDto.getCurrency());
        invoice.setNumber(invoiceDto.getNumber());
        invoice.setCurrentBalance(invoiceDto.getBalanceOnDocument());
        invoice.setPaid(invoiceDto.getPaid());
        invoice.setContractNumber(invoiceDto.getContractNo());
        invoice.setTypeId(invoiceDto.getInvoiceType().getId());
        invoice.setRealDueDate(invoiceDto.getRealDueDate());
        invoice.setCorrection(correction);
        invoice.setCorrectionOrder(correctionOrder);
        invoice.setIssueDate(invoiceDto.getIssueDate());
        invoice.setCorrections(emptyList());
        invoice.setExchangeRate(invoiceDto.getExchangeRate());
        return invoice;
    }
}
