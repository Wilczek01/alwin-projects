package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.aida.core.api.model.AidaInvoiceWithCorrectionsDto;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.model.issue.Balance;
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.issue.SegmentDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService;
import com.codersteam.alwin.core.api.service.issue.IssueCreateResult;
import com.codersteam.alwin.core.api.service.issue.IssueCreatorService;
import com.codersteam.alwin.core.local.DPDService;
import com.codersteam.alwin.core.local.LocalInvoiceService;
import com.codersteam.alwin.core.service.impl.customer.CustomerServiceImpl;
import com.codersteam.alwin.core.service.impl.issue.preparator.BalancePreparator;
import com.codersteam.alwin.core.util.DateUtils;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.IssueTypeConfigurationDao;
import com.codersteam.alwin.db.dao.IssueTypeDao;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.issue.Issue;
import com.codersteam.alwin.jpa.issue.IssueInvoice;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.operator.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

import static com.codersteam.alwin.common.AlwinConstants.ZERO;
import static com.codersteam.alwin.core.api.model.currency.Currency.forStringValue;
import static com.codersteam.alwin.core.service.AlwinParameters.ISSUE_DEFAULT_PRIORITY;
import static com.codersteam.alwin.core.service.impl.issue.preparator.DatePreparator.fillDates;
import static com.codersteam.alwin.jpa.type.IssueState.NEW;
import static java.lang.String.format;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Stateless
public class IssueCreatorServiceImpl implements IssueCreatorService {

    private static final Logger LOG = LoggerFactory.getLogger(IssueCreatorServiceImpl.class.getName());

    private IssueTypeDao issueTypeDao;
    private OperatorDao operatorDao;
    private InvoiceService aidaInvoiceService;
    private DateProvider dateProvider;
    private ActivityService activityService;
    private BalanceCalculatorService balanceCalculatorService;
    private LocalInvoiceService localInvoiceService;
    private CustomerServiceImpl customerService;
    private IssueTypeConfigurationDao issueTypeConfigurationDao;
    private CustomerVerifierService customerVerifierService;
    private DPDService dpdService;
    private IssueDao issueDao;
    private IssueServiceImpl issueService;

    public IssueCreatorServiceImpl() {
    }

    @Inject
    public IssueCreatorServiceImpl(IssueTypeDao issueTypeDao,
                                   OperatorDao operatorDao,
                                   DateProvider dateProvider,
                                   ActivityService activityService,
                                   BalanceCalculatorService balanceCalculatorService,
                                   AidaService aidaService,
                                   CustomerServiceImpl customerService,
                                   LocalInvoiceService localInvoiceService,
                                   IssueTypeConfigurationDao issueTypeConfigurationDao,
                                   CustomerVerifierService customerVerifierService,
                                   DPDService dpdService,
                                   IssueDao issueDao,
                                   IssueServiceImpl issueService) {
        this.issueTypeDao = issueTypeDao;
        this.operatorDao = operatorDao;
        this.dateProvider = dateProvider;
        this.activityService = activityService;
        this.balanceCalculatorService = balanceCalculatorService;
        this.aidaInvoiceService = aidaService.getInvoiceService();
        this.localInvoiceService = localInvoiceService;
        this.customerService = customerService;
        this.issueTypeConfigurationDao = issueTypeConfigurationDao;
        this.customerVerifierService = customerVerifierService;
        this.dpdService = dpdService;
        this.issueDao = issueDao;
        this.issueService = issueService;
    }

    @Override
    @Transactional
    public IssueCreateResult createIssueManually(final Long extCompanyId, final Long issueTypeId, final Date expirationDate, final Long assigneeId) {
        LOG.info("Creating issue for extCompanyId={}, issueTypeId={}, expirationDate={}, operatorId={}", extCompanyId, issueTypeId, expirationDate,
                assigneeId);

        final Customer customer = customerService.getCustomerWithAdditionalData(extCompanyId);
        final IssueType issueType = getIssueType(issueTypeId);
        final Date issueExpirationDate = getExpirationDate(expirationDate, issueType, customer.getCompany().getSegment());

        final CompanyInvoices companyInvoices = findCompanyInvoices(extCompanyId, issueExpirationDate);

        final Operator assignee = getAssignee(assigneeId);

        final Issue issue = manuallyCreateIssueWithInvoicesAndActivities(extCompanyId, customer, issueTypeId, issueExpirationDate, companyInvoices, assignee);

        issueService.assignOperatorByPostalCodeForDirectDebtCollection(issue);

        LOG.info("Issue {} for company (AIDA id) {} successfully created.", issue.getId(), extCompanyId);
        return new IssueCreateResult(issue.getId());
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void createIssue(Long extCompanyId, IssueTypeConfigurationDto issueConfiguration) {
        LOG.info("Creating issue for extCompanyId={}, issueTypeName={}, issueTypeSegment={}", extCompanyId,
                issueConfiguration.getIssueType().getName(), issueConfiguration.getSegment().getKey());

        Customer customer = customerService.getCustomerWithAdditionalData(extCompanyId);
        Segment customerSegment = customer.getCompany().getSegment();
        Segment issueTypeSegment = SegmentDto.segmentByKey(issueConfiguration.getSegment().getKey());
        if (customerSegment != issueTypeSegment) {
            // w przypadku niewłaściwego segmentu zlecenie nie jest zakładane
            LOG.info("Issue for extCompanyId={} not created due improper segment: companySegment={}, issueTypeSegment={}",
                    extCompanyId, customerSegment, issueTypeSegment);
            return;
        }

        List<AidaInvoiceWithCorrectionsDto> startInvoices = aidaInvoiceService.getBalanceStartInvoicesByCompanyId(extCompanyId, dateProvider.getCurrentDateStartOfDay(), true);
        Date dpdStartDate = dpdService.calculateDPDStartDateForNewIssue(startInvoices, extCompanyId, issueConfiguration.getMinBalanceStart());
        if (dpdStartDate == null) {
            LOG.info("dpdStartDate is null, aborting");
            return;
        }
        Long dpd = DateUtils.daysBetween(dpdStartDate, dateProvider.getCurrentDate());
        LOG.info("Current dpd: {}, max dpd: {}", dpd, issueConfiguration.getDuration() + issueConfiguration.getDpdStart());
        if (dpd > issueConfiguration.getDuration() + issueConfiguration.getDpdStart()) {
            LOG.info("Issue with type: {}, for company (AIDA id) {} not created because dpdStartDate: {} would make dpd too high",
                    issueConfiguration.getIssueType().getName(), extCompanyId, dpdStartDate);
            return;
        }

        Date issueExpirationDate = DateUtils.datePlusDays(dpdStartDate, issueConfiguration.getDuration() + issueConfiguration.getDpdStart());

        CompanyInvoices companyInvoices = findCompanyInvoices(extCompanyId, issueExpirationDate);
        LOG.info("Ready to create issue, extCompanyId: {}, company: {}, issueType: {}, issueExpirationDate: {}, dpdStartDate: {}",
                extCompanyId, customer.getCompany().getCompanyName(), issueConfiguration.getIssueType().getName(), issueExpirationDate, dpdStartDate);
        Issue issue = automaticallyCreateIssueWithInvoicesAndActivities(
                extCompanyId,
                customer,
                issueConfiguration.getIssueType().getId(),
                issueExpirationDate,
                companyInvoices,
                dpdStartDate);

        LOG.info("Issue id={} type={} for company (AIDA id) {} successfully created automatically.",
                issue.getId(),
                issue.getIssueType().getName(),
                extCompanyId);
    }

    protected boolean isBalanceEnoughToCreateIssue(final AidaInvoiceWithCorrectionsDto invoice, final BigDecimal minBalanceStart) {
        final BigDecimal balance = invoice.getBalanceOnDocument() == null ? ZERO : invoice.getBalanceOnDocument();
        final Currency currency = forStringValue(invoice.getCurrency(), invoice.getNumber());
        return customerVerifierService.isBalanceEnoughToCreateIssue(balance, currency, invoice.getExchangeRate(), minBalanceStart);
    }

    private Date calculateExpirationDate(final IssueTypeName issueTypeName, final Segment segment) {
        final Integer duration = issueTypeConfigurationDao.findDurationByTypeAndSegment(issueTypeName, segment);
        return dateProvider.getDateStartOfDayPlusDays(duration);
    }

    private Date getExpirationDate(final Date expirationDate, final IssueType issueType, final Segment segment) {
        if (expirationDate != null) {
            return expirationDate;
        }
        return calculateExpirationDate(issueType.getName(), segment);
    }

    private CompanyInvoices findCompanyInvoices(Long extCompanyId, Date issueExpirationDate) {
        Date issueStartDate = dateProvider.getCurrentDateStartOfDay();
        List<AidaInvoiceWithCorrectionsDto> startInvoices = aidaInvoiceService.getBalanceStartInvoicesByCompanyId(extCompanyId, issueStartDate, true);
        List<AidaInvoiceWithCorrectionsDto> additionalInvoices = aidaInvoiceService.getBalanceAdditionalInvoicesWithActiveContractByCompanyId(extCompanyId, issueStartDate,
                issueExpirationDate);
        return new CompanyInvoices(extCompanyId, startInvoices, additionalInvoices);
    }

    private Issue manuallyCreateIssueWithInvoicesAndActivities(Long extCompanyId, Customer customer, Long issueTypeId,
                                                               Date issueExpirationDate, CompanyInvoices companyInvoices, Operator assignee) {
        Date dpdStartDate = dpdService.calculateDPDStartDateForNewIssue(companyInvoices.getStartInvoices(), extCompanyId, BigDecimal.ZERO);
        Issue issue = createIssue(companyInvoices, issueTypeId, issueExpirationDate, customer, true, assignee, dpdStartDate);
        updateIssueWithInvoices(issue, companyInvoices.getAllInvoices(), extCompanyId);
        saveIssueActivities(issue);
        return issue;
    }

    private Issue automaticallyCreateIssueWithInvoicesAndActivities(Long extCompanyId, Customer customer, Long issueTypeId,
                                                                    Date issueExpirationDate, CompanyInvoices companyInvoices, Date dpdStartDate) {
        Issue issue = createIssue(companyInvoices, issueTypeId, issueExpirationDate, customer, false, null, dpdStartDate);
        updateIssueWithInvoices(issue, companyInvoices.getAllInvoices(), extCompanyId);
        saveIssueActivities(issue);
        return issue;
    }

    private Issue createIssue(CompanyInvoices companyInvoices, Long issueTypeId, Date expirationDate, Customer customer,
                              boolean createdManually, Operator assignee, Date dpdStartDate) {
        Issue issue = new Issue();
        issue.setAssignee(assignee);
        issue.setCustomer(customer);
        issue = fillDates(issue, dateProvider.getCurrentDateStartOfDay(), expirationDate, dpdStartDate);
        issue.setTerminationCause(null);
        issue.setIssueType(prepareIssueType(issueTypeId));
        issue.setIssueState(NEW);
        issue = fillBalance(issue, companyInvoices);
        issue.setExcludedFromStats(false);
        issue.setPriority(ISSUE_DEFAULT_PRIORITY);
        issue.setCreatedManually(createdManually);
        issueDao.createIssue(issue);
        return issue;
    }

    private IssueType prepareIssueType(final Long issueTypeId) {
        final IssueType issueType = new IssueType();
        issueType.setId(issueTypeId);
        return issueType;
    }

    private Issue fillBalance(final Issue issue, final CompanyInvoices companyInvoices) {
        final Map<Currency, Balance> balances = balanceCalculatorService.calculateBalancesFromIssueSubjectInvoices(null, companyInvoices.getExtCompanyId(),
                companyInvoices.getStartInvoices(), dateProvider.getCurrentDateStartOfDay());
        return BalancePreparator.fillBalance(issue, balances);
    }

    private void updateIssueWithInvoices(final Issue entity, final Collection<AidaInvoiceWithCorrectionsDto> customerInvoicesForIssuesCreation,
                                         final Long extCompanyId) {
        final Set<String> contractsOutOfService = customerService.findActiveContractOutOfServiceNumbers(extCompanyId);
        final List<IssueInvoice> issueInvoices = localInvoiceService.prepareInvoices(customerInvoicesForIssuesCreation, entity, contractsOutOfService);
        entity.setIssueInvoices(issueInvoices);
    }

    private void saveIssueActivities(final Issue issue) {
        if (issue.getAssignee() != null) {
            activityService.createAndAssignDefaultActivitiesForIssue(issue.getId(), issue.getIssueType().getId(), issue.getAssignee().getId());
        } else {
            activityService.createDefaultActivitiesForIssue(issue.getId(), issue.getIssueType().getId());
        }
    }

    protected IssueType getIssueType(final Long issueTypeId) {
        final Optional<IssueType> issueType = issueTypeDao.findIssueTypeById(issueTypeId);
        return issueType.orElseThrow(() -> new IllegalArgumentException(format("Not existing issue type id [%s]", issueTypeId)));
    }

    private Operator getAssignee(final Long assigneeId) {
        if (assigneeId != null) {
            return operatorDao.get(assigneeId).orElse(null);
        }
        return null;
    }
}
