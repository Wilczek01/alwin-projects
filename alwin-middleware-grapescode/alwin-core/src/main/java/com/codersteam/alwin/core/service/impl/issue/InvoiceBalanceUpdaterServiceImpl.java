package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.InvoiceBalanceUpdaterService;
import com.codersteam.alwin.core.local.LocalInvoiceService;
import com.codersteam.alwin.core.util.InvoiceUtils;
import com.codersteam.alwin.db.dao.InvoiceDao;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.IssueInvoiceDao;
import com.codersteam.alwin.jpa.issue.Invoice;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.codersteam.alwin.core.util.InvoiceUtils.isIssueSubject;
import static com.codersteam.alwin.core.util.IssueUtils.getExtCompanyId;
import static java.util.stream.Collectors.toSet;

@Stateless
@LocalBean
public class InvoiceBalanceUpdaterServiceImpl implements InvoiceBalanceUpdaterService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private InvoiceDao invoiceDao;
    private IssueDao issueDao;
    private InvoiceService invoiceService;
    private IssueInvoiceDao issueInvoiceDao;
    private LocalInvoiceService localInvoiceService;
    private DateProvider dateProvider;

    public InvoiceBalanceUpdaterServiceImpl() {
    }

    @Inject
    public InvoiceBalanceUpdaterServiceImpl(InvoiceDao invoiceDao,
                                            IssueDao issueDao,
                                            AidaService aidaService,
                                            IssueInvoiceDao issueInvoiceDao,
                                            LocalInvoiceService localInvoiceService,
                                            DateProvider dateProvider) {
        this.invoiceDao = invoiceDao;
        this.issueDao = issueDao;
        this.invoiceService = aidaService.getInvoiceService();
        this.issueInvoiceDao = issueInvoiceDao;
        this.localInvoiceService = localInvoiceService;
        this.dateProvider = dateProvider;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateIssueInvoicesBalance(final Long issueId) {
        LOG.info("Updating issue {} invoices balance", issueId);
        final Issue issue = issueDao.get(issueId).orElseThrow(() -> new EntityNotFoundException(issueId));
        updateInvoicesBalance(issue);
        issueInvoiceDao.updateIssueInvoicesFinalBalance(issueId);
        LOG.info("Updating issue {} invoices balance finished", issueId);
    }

    protected void updateInvoicesBalance(Issue issue) {
        Long extCompanyId = getExtCompanyId(issue);
        Long issueId = issue.getId();

        List<AidaInvoiceWithCorrectionsDto> allIssueInvoices = invoiceService.findInvoicesWithActiveContractByCompanyId(extCompanyId);

        LOG.info("Updating invoices for issue {}, total number of invoices to check {}", issueId, allIssueInvoices.size());
        boolean isIncludeDueInvoicesDuringIssue = localInvoiceService.isIncludeDueInvoicesDuringIssue(issue);
        allIssueInvoices.forEach(invoice -> updateBalanceForIssueIdAndInvoiceId(issue, invoice, isIncludeDueInvoicesDuringIssue));
    }

    public void updateBalanceForIssueIdAndInvoiceId(final Issue issue, final AidaInvoiceWithCorrectionsDto aidaInvoiceDto,
                                                    final boolean isIncludeDueInvoicesDuringIssue) {
        final Optional<Invoice> storedInvoice = invoiceDao.findInvoiceForIssueIdAndInvoiceNo(issue.getId(), aidaInvoiceDto.getNumber());
        if (storedInvoice.isPresent()) {
            updateExistingInvoice(issue, aidaInvoiceDto, isIncludeDueInvoicesDuringIssue, storedInvoice.get());
        } else {
            createNewInvoice(issue, aidaInvoiceDto, isIncludeDueInvoicesDuringIssue);
        }
    }

    private void createNewInvoice(final Issue issue, final AidaInvoiceWithCorrectionsDto aidaInvoiceDto, final boolean isIncludeDueInvoicesDuringIssue) {
        final IssueInvoice issueInvoice = localInvoiceService.prepareIssueInvoice(issue, aidaInvoiceDto, isIncludeDueInvoicesDuringIssue);
        invoiceDao.create(issueInvoice.getInvoice());
    }

    private void updateExistingInvoice(final Issue issue, final AidaInvoiceWithCorrectionsDto aidaInvoiceDto, final boolean isIncludeDueInvoicesDuringIssue,
                                       final Invoice invoice) {
        final Set<String> invoicesOutOfService = issueInvoiceDao.findInvoicesOutOfService(issue.getId()).stream().map(Invoice::getNumber).collect(toSet());

        if (invoicesOutOfService.contains(invoice.getNumber())) {
            if (shouldSetIssueDate(invoice)) {
                updateIssueDate(aidaInvoiceDto, invoice);
                InvoiceUtils.setExchangeRateIfNotSet(invoice, aidaInvoiceDto.getExchangeRate());
                invoiceDao.update(invoice);
            }
            // FV wyłączona z obsługi -> nie robimy update salda
            return;
        }
        invoice.setCurrentBalance(aidaInvoiceDto.getBalanceOnDocument());
        invoice.setPaid(aidaInvoiceDto.getPaid());
        invoice.setLastPaymentDate(aidaInvoiceDto.getLastPaymentDate());
        invoice.setRealDueDate(aidaInvoiceDto.getRealDueDate());
        setIssueSubjectIfNeeded(invoice, issue, isIncludeDueInvoicesDuringIssue);
        updateIssueDate(aidaInvoiceDto, invoice);
        InvoiceUtils.setExchangeRateIfNotSet(invoice, aidaInvoiceDto.getExchangeRate());
        invoiceDao.update(invoice);
    }

    protected void setIssueSubjectIfNeeded(final Invoice invoice, final Issue issue, final boolean isIncludeDueInvoicesDuringIssue) {
        if (!isIncludeDueInvoicesDuringIssue) {
            return;
        }

        final Optional<IssueInvoice> issueInvoiceForGivenIssue = invoice.getIssueInvoices().stream()
                .filter(issueInvoice -> issueInvoice.getIssue().getId().equals(issue.getId()))
                .findFirst();
        final boolean issueSubject = isIssueSubject(invoice.getCurrentBalance(), issue.getStartDate(), issue.getExpirationDate(), invoice.getRealDueDate(),
                dateProvider.getCurrentDateStartOfDay(), true);

        if (issueSubject) {
            issueInvoiceForGivenIssue.ifPresent(issueInvoice -> issueInvoice.setIssueSubject(true));
        }
    }

    private void updateIssueDate(final AidaInvoiceWithCorrectionsDto aidaInvoiceDto, final Invoice invoice) {
        if (shouldSetIssueDate(invoice)) {
            invoice.setIssueDate(aidaInvoiceDto.getIssueDate());
        }
    }

    private boolean shouldSetIssueDate(final Invoice invoice) {
        return invoice.getIssueDate() == null;
    }
}
