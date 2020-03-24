package com.codersteam.alwin.core.service.impl.scheduler.strategy;

import com.codersteam.alwin.common.scheduler.SchedulerTaskType;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.activity.DeclarationService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

/**
 * Zadanie cykliczne uaktualnienia sald deklaracji spłat do zleceń windykacyjnych
 *
 * @author Michal Horowic
 */
public class UpdateDeclarationsBalanceSchedulerExecution extends SchedulerExecution {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DESCRIPTION = "Update declarations balance";
    private static final int DECLARATION_PAYMENT_DATE_NUMBER_OF_DAYS = 7;
    private final IssueService issueService;
    private final DeclarationService declarationService;
    private final DateProvider dateProvider;

    public UpdateDeclarationsBalanceSchedulerExecution(final SchedulerExecutionInfoService schedulerExecutionService,
                                                       final IssueService issueService,
                                                       final DeclarationService declarationService,
                                                       final DateProvider dateProvider) {
        super(schedulerExecutionService, SchedulerTaskType.UPDATE_DECLARATIONS_BALANCE);
        this.issueService = issueService;
        this.declarationService = declarationService;
        this.dateProvider = dateProvider;
    }

    @Override
    protected void executeScheduler() {
        final Date declarationPaymentDateStart = dateProvider.getDateStartOfDayMinusDays(DECLARATION_PAYMENT_DATE_NUMBER_OF_DAYS);
        final Date declarationPaymentDateEnd = dateProvider.getCurrentDate();
        final List<Long> issueIds = issueService.findIssueIdsWithUnpaidDeclarations(declarationPaymentDateStart, declarationPaymentDateEnd);
        LOG.info("Updating balance of declarations for {} issues", issueIds.size());
        issueIds.forEach(issueId -> {
            try {
                declarationService.updateIssueDeclarations(issueId);
            } catch (final Exception e) {
                LOG.error("Update declarations balance for issue with id={} failed", issueId, e);
            }
        });
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected String getDescription() {
        return DESCRIPTION;
    }
}
