package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.aida.core.api.model.AidaSimpleInvoiceDto;
import com.codersteam.aida.core.api.service.InvoiceService;
import com.codersteam.alwin.core.api.model.issue.IssueTypeConfigurationDto;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.customer.CustomerVerifierService;
import com.codersteam.alwin.core.api.service.issue.IssueConfigurationService;
import com.codersteam.alwin.core.api.service.issue.IssueCreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateIssueService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateIssueService.class.getName());

    private final InvoiceService aidaInvoiceService;
    private final DateProvider dateProvider;
    private final CustomerVerifierService customerVerifierService;
    private final IssueCreatorService issueCreatorService;
    private final IssueConfigurationService issueConfigurationService;

    @Inject
    public CreateIssueService(DateProvider dateProvider,
                              CustomerVerifierService customerVerifierService,
                              AidaService aidaService,
                              IssueCreatorService issueCreatorService,
                              IssueConfigurationService issueConfigurationService) {
        this.dateProvider = dateProvider;
        this.customerVerifierService = customerVerifierService;
        this.aidaInvoiceService = aidaService.getInvoiceService();
        this.issueCreatorService = issueCreatorService;
        this.issueConfigurationService = issueConfigurationService;
    }

    public void createIssues() {
        issueConfigurationService.findAllCreateAutomaticallyIssueTypeConfigurations()
                .forEach(this::createIssues);
    }

    protected void createIssues(IssueTypeConfigurationDto issueTypeConfiguration) {
        Date dueDateTo = dateProvider.getDateStartOfDayMinusDays(issueTypeConfiguration.getDpdStart());
        int maxDpd = issueTypeConfiguration.getDpdStart() + issueTypeConfiguration.getDuration() - 1;
        Date dueDateFrom = dateProvider.getDateStartOfDayMinusDays(maxDpd);
        LOG.info("Issue creation for issueTypeName={}, segment={}, dpdStart={}, duration={}, dueDateFrom={}, dueDateTo={} started",
                issueTypeConfiguration.getIssueType().getName(), issueTypeConfiguration.getSegment().getKey(),
                issueTypeConfiguration.getDpdStart(), issueTypeConfiguration.getDuration(), dueDateFrom, dueDateTo);

        aidaInvoiceService.getSimpleInvoicesWithActiveContractByDueDate(dueDateFrom, dueDateTo, issueTypeConfiguration.getMinBalanceStart()).stream()
                .peek(invoice -> LOG.info("Found invoice; id: {}, invoiceNumber {}, contractNo: {}, companyId: {}",invoice.getId(), invoice.getNumber(), invoice.getContractNo(), invoice.getCompanyId()))
                .collect(Collectors.groupingBy(AidaSimpleInvoiceDto::getCompanyId))
                .entrySet().stream()
                .peek(entry -> LOG.info("Potential customer for issue creation found. ExtCompanyId: {}, ", entry.getKey()))
                .filter(e -> customerVerifierService.filterCustomersForIssueCreation(e.getKey(), e.getValue()))
                .peek(entry -> LOG.info("Will attempt to create issue for customer. ExtCompanyId: {}, ", entry.getKey()))
                .map(Map.Entry::getKey)
                .forEach(companyId -> {
                    try {
                        issueCreatorService.createIssue(companyId, issueTypeConfiguration);
                    } catch (final Exception e) {
                        LOG.error("Create issue for extCompanyId={} failed", companyId, e);
                    }
                });
        LOG.info("Issue creation for DPD dueDateFrom={}, dueDateTo={}, issueTypeName={}, segment={} finished", dueDateFrom, dueDateTo, issueTypeConfiguration.getIssueType().getName(),
                issueTypeConfiguration.getSegment().getKey());
    }
}
