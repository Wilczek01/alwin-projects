package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.DeclarationService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.issue.UnresolvedIssueService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.service.impl.activity.OutgoingCallActivityService;
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesInvolvementService;
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesService;
import com.codersteam.alwin.core.service.impl.issue.ActiveIssuesBalanceUpdaterService;
import com.codersteam.alwin.core.service.impl.issue.CloseExpiredIssuesService;
import com.codersteam.alwin.core.service.impl.issue.CreateIssueService;
import com.codersteam.alwin.core.service.impl.issue.CreateTagService;
import com.codersteam.alwin.core.service.impl.notice.CreateDemandForPaymentService;
import com.codersteam.alwin.core.service.impl.termination.CreateContractTerminationService;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Fabryka obiektów do uruchamiania zadań cyklicznych
 */
@Stateless
public class SchedulerExecutionFactory {

    private DateProvider dateProvider;
    private IssueService issueService;
    private ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService;
    private CloseExpiredIssuesService closeExpiredIssuesService;
    private UpdateCompaniesService syncDataService;
    private UnresolvedIssueService unresolvedIssueService;
    private UpdateCompaniesInvolvementService involvementService;
    private OutgoingCallActivityService outgoingCallActivityService;
    private SchedulerExecutionInfoService schedulerExecutionService;
    private DeclarationService declarationService;
    private CreateDemandForPaymentService createDemandForPaymentService;
    private CreateContractTerminationService createContractTerminationService;
    private CreateIssueService createIssueService;
    private CreateTagService createTagService;

    public SchedulerExecutionFactory() {
        // for deployment
    }

    @Inject
    public SchedulerExecutionFactory(DateProvider dateProvider,
                                     IssueService issueService,
                                     ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService,
                                     CloseExpiredIssuesService closeExpiredIssuesService,
                                     UpdateCompaniesService syncDataService,
                                     UnresolvedIssueService unresolvedIssueService,
                                     UpdateCompaniesInvolvementService involvementService,
                                     OutgoingCallActivityService outgoingCallActivityService,
                                     SchedulerExecutionInfoService schedulerExecutionService,
                                     DeclarationService declarationService,
                                     CreateDemandForPaymentService createDemandForPaymentService,
                                     CreateContractTerminationService createContractTerminationService,
                                     CreateIssueService createIssueService,
                                     CreateTagService createTagService) {
        this.dateProvider = dateProvider;
        this.issueService = issueService;
        this.activeIssuesBalanceUpdaterService = activeIssuesBalanceUpdaterService;
        this.closeExpiredIssuesService = closeExpiredIssuesService;
        this.syncDataService = syncDataService;
        this.unresolvedIssueService = unresolvedIssueService;
        this.involvementService = involvementService;
        this.outgoingCallActivityService = outgoingCallActivityService;
        this.schedulerExecutionService = schedulerExecutionService;
        this.declarationService = declarationService;
        this.createDemandForPaymentService = createDemandForPaymentService;
        this.createContractTerminationService = createContractTerminationService;
        this.createIssueService = createIssueService;
        this.createTagService = createTagService;
    }

    /**
     * Metoda do pobierania obiektów do uruchamiania zadań cyklicznych
     *
     * @param schedulerTaskType - typ zadania cyklicznego
     * @return Logiką dla zadania cyklicznego
     */
    public SchedulerExecution getSchedulerExecution(final SchedulerTaskType schedulerTaskType) {
        switch (schedulerTaskType) {
            case UPDATE_BALANCE_AND_CLOSE_EXPIRED_AND_CREATE_CHILD_ISSUES:
                return new UpdateBalanceCloseAndCreateIssuesSchedulerExecution(schedulerExecutionService, activeIssuesBalanceUpdaterService, closeExpiredIssuesService);
            case GENERATE_DEMAND_FOR_PAYMENT_AND_CONTRACT_TERMINATION:
                return new GenerateDemandForPaymentAndContractTerminationSchedulerExecution(schedulerExecutionService, createDemandForPaymentService, createContractTerminationService);
            case UPDATE_DECLARATIONS_BALANCE:
                return new UpdateDeclarationsBalanceSchedulerExecution(schedulerExecutionService, issueService, declarationService, dateProvider);
            case UPDATE_ISSUES_BALANCE:
                return new UpdateIssueBalanceSchedulerExecution(schedulerExecutionService, activeIssuesBalanceUpdaterService);
            case GENERATE_ISSUES:
                return new GenerateIssuesSchedulerExecution(schedulerExecutionService, createIssueService);
            case CLOSE_EXPIRED_ISSUES_AND_CREATE_CHILD_ISSUES_IF_NEEDED:
                return new RecreateExpiredIssuesSchedulerExecution(schedulerExecutionService, closeExpiredIssuesService);
            case UPDATE_COMPANY_DATA:
                return new UpdateCompanyDataSchedulerExecution(schedulerExecutionService, syncDataService);
            case UPDATE_COMPANIES_INVOLVEMENT:
                return new UpdateCompaniesInvolvementSchedulerExecution(schedulerExecutionService, involvementService);
            case FIND_UNRESOLVED_ISSUES_AND_SEND_REPORT_EMAIL:
                return new ReportUnresolvedIssuesSchedulerExecution(schedulerExecutionService, unresolvedIssueService);
            case CREATE_OUTGOING_CALL_ACTIVITY:
                return new CreateOutgoingCallSchedulerExecution(schedulerExecutionService, outgoingCallActivityService);
            case TAG_OVERDUE_ISSUES:
                return new TagOverdueIssuesSchedulerExecution(schedulerExecutionService, createTagService);
        }
        throw new UnsupportedOperationException("Couldn't find scheduler execution for type: " + schedulerTaskType);
    }

}
