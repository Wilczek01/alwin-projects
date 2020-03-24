package com.codersteam.alwin.core.service.impl.issue

import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType
import com.codersteam.alwin.common.scheduler.SchedulerTaskType
import com.codersteam.alwin.core.api.service.DateProvider
import com.codersteam.alwin.core.api.service.activity.DeclarationService
import com.codersteam.alwin.core.api.service.issue.*
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService
import com.codersteam.alwin.core.api.service.scheduler.SchedulerProcessExecutor
import com.codersteam.alwin.core.service.impl.activity.OutgoingCallActivityService
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesInvolvementService
import com.codersteam.alwin.core.service.impl.customer.UpdateCompaniesService
import com.codersteam.alwin.core.service.impl.notice.CreateDemandForPaymentService
import com.codersteam.alwin.core.service.impl.scheduler.BatchProcessScheduler
import com.codersteam.alwin.core.service.impl.scheduler.SchedulerProcessExecutorImpl
import com.codersteam.alwin.core.service.impl.scheduler.strategy.SchedulerExecutionFactory
import com.codersteam.alwin.core.service.impl.termination.CreateContractTerminationService
import com.codersteam.alwin.testdata.SchedulerExecutionTestData
import spock.lang.Specification
import spock.lang.Subject

import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_1
import static com.codersteam.alwin.testdata.AssignedIssuesWithHighPriorityTestData.ISSUE_ID_2
import static java.util.Arrays.asList

class SchedulerProcessExecutorTest extends Specification {

    @Subject
    private SchedulerProcessExecutor schedulerProcessExecutor

    private SchedulerExecutionFactory schedulerExecutionFactory
    private IssueService issueService = Mock(IssueService)
    private ActiveIssuesBalanceUpdaterService activeIssuesBalanceUpdaterService = Mock(ActiveIssuesBalanceUpdaterService)
    private DateProvider dateProvider = Mock(DateProvider)
    private UpdateCompaniesService syncDataService = Mock(UpdateCompaniesService)
    private UnresolvedIssueService unresolvedIssueService = Mock(UnresolvedIssueService)
    private UpdateCompaniesInvolvementService involvementService = Mock(UpdateCompaniesInvolvementService)
    private OutgoingCallActivityService outgoingCallActivityService = Mock(OutgoingCallActivityService)
    private SchedulerExecutionInfoService schedulerExecutionService = Mock(SchedulerExecutionInfoService)
    private CloseExpiredIssuesService closeExpiredIssuesService = Mock(CloseExpiredIssuesService)
    private DeclarationService declarationService = Mock(DeclarationService)
    private CreateDemandForPaymentService createDemandForPaymentService = Mock(CreateDemandForPaymentService)
    private CreateContractTerminationService createContractTerminationService = Mock(CreateContractTerminationService)
    private CreateIssueService createIssueService = Mock(CreateIssueService)
    private CreateTagService createTagService = Mock(CreateTagService)

    def "setup"() {
        schedulerExecutionFactory = new SchedulerExecutionFactory(
                dateProvider, issueService, activeIssuesBalanceUpdaterService, closeExpiredIssuesService, syncDataService,
                unresolvedIssueService, involvementService, outgoingCallActivityService, schedulerExecutionService, declarationService,
                createDemandForPaymentService, createContractTerminationService, createIssueService, createTagService
        )
        schedulerProcessExecutor = new SchedulerProcessExecutorImpl(schedulerExecutionFactory, schedulerExecutionService)

        schedulerExecutionService.schedulerExecutionStarted(_ as SchedulerTaskType, false) >> {
            List arg ->
                def type = (SchedulerTaskType) arg[0]
                SchedulerExecutionTestData.startedSchedulerExecution(type.ordinal(), type)
        }

        issueService.findIssueIdsWithUnpaidDeclarations(_, _) >> asList(ISSUE_ID_1, ISSUE_ID_2)
    }

    def "should have default constructor"() {
        when:
            def scheduler = new BatchProcessScheduler()
        then:
            scheduler
    }

    def "should fire scheduled tasks in specific order"() {
        when:
            schedulerProcessExecutor.processSchedulerTasks(SchedulerBatchProcessType.BASE_SCHEDULE_TASKS, false)
        then:
            1 * activeIssuesBalanceUpdaterService.updateActiveIssuesBalance()
        then:
            1 * closeExpiredIssuesService.closeExpiredIssuesAndCreateChildIssuesIfNeeded()
        then:
            1 * createIssueService.createIssues()
        then:
            1 * outgoingCallActivityService.createOutgoingCallActivity()
        then:
            1 * syncDataService.syncCompanyData()
        then:
            1 * involvementService.updateCompaniesInvolvement()
        then:
            1 * unresolvedIssueService.findUnresolvedIssuesAndSendReportEmail()
        then:
            1 * createTagService.tagOverdueIssues()
        then:
            1 * declarationService.updateIssueDeclarations(ISSUE_ID_1)
            1 * declarationService.updateIssueDeclarations(ISSUE_ID_2)
        then:
            1 * createDemandForPaymentService.prepareDemandsForPayment()
            1 * createContractTerminationService.prepareContractTerminations()
    }

    def "should not interrupt scheduled task execution if one fails"() {
        when:
            schedulerProcessExecutor.processSchedulerTasks(SchedulerBatchProcessType.BASE_SCHEDULE_TASKS, false)
        then:
            1 * activeIssuesBalanceUpdaterService.updateActiveIssuesBalance() >> { throw new RuntimeException() }
        then:
            0 * closeExpiredIssuesService.closeExpiredIssuesAndCreateChildIssuesIfNeeded()
        then:
            1 * createIssueService.createIssues()
        then:
            1 * outgoingCallActivityService.createOutgoingCallActivity()
        then:
            1 * syncDataService.syncCompanyData()
        then:
            1 * involvementService.updateCompaniesInvolvement()
        then:
            1 * unresolvedIssueService.findUnresolvedIssuesAndSendReportEmail()
        then:
            1 * createTagService.tagOverdueIssues()
        then:
            2 * declarationService.updateIssueDeclarations(_ as Long)
        then:
            1 * createDemandForPaymentService.prepareDemandsForPayment()
        then:
            1 * createContractTerminationService.prepareContractTerminations()
    }
}
