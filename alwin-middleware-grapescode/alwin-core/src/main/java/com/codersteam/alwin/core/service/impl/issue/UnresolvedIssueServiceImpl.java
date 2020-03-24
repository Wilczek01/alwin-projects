package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.model.email.UnresolvedIssuesEmailMessage;
import com.codersteam.alwin.core.api.model.issue.UnresolvedIssueDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.email.EmailSendMessageEnqueueService;
import com.codersteam.alwin.core.api.service.issue.UnresolvedIssueService;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.db.dao.LastDataSyncDao;
import com.codersteam.alwin.jpa.LastDataSync;
import com.codersteam.alwin.jpa.issue.Issue;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.codersteam.alwin.common.issue.IssueTypeName.DIRECT_DEBT_COLLECTION;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.core.service.AlwinParameters.SYNC_DATA_DAY_START;
import static com.codersteam.alwin.jpa.type.IssueState.NEW;
import static com.codersteam.alwin.jpa.type.LastDataSyncType.FIND_UNRESOLVED_ISSUES;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Piotr Naroznik
 */
@Stateless
public class UnresolvedIssueServiceImpl implements UnresolvedIssueService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private IssueDao issueDao;
    private DateProvider dateProvider;
    private LastDataSyncDao lastDataSyncDao;
    private OperatorService operatorService;
    private ConfigurableMapper mapper;
    private EmailSendMessageEnqueueService emailSendMessageEnqueueService;

    @SuppressWarnings("unused")
    public UnresolvedIssueServiceImpl() {
        // For deployment
    }

    @Inject
    public UnresolvedIssueServiceImpl(final IssueDao issueDao, @SuppressWarnings("CdiInjectionPointsInspection") final DateProvider dateProvider,
                                      final LastDataSyncDao lastDataSyncDao, final OperatorService operatorService,
                                      final AlwinMapper mapper,
                                      final EmailSendMessageEnqueueService emailSendMessageEnqueueService) {
        this.issueDao = issueDao;
        this.dateProvider = dateProvider;
        this.lastDataSyncDao = lastDataSyncDao;
        this.operatorService = operatorService;
        this.mapper = mapper;
        this.emailSendMessageEnqueueService = emailSendMessageEnqueueService;
    }

    @Override
    public void findUnresolvedIssuesAndSendReportEmail() {
        final LastDataSync lastDataSync = prepareLastDataSync();
        final List<Issue> issues = issueDao.findIssuesCreatedBySystem(DIRECT_DEBT_COLLECTION, NEW, lastDataSync.getFromDate(), lastDataSync.getToDate());
        final List<UnresolvedIssueDto> unresolvedIssues = mapper.mapAsList(issues, UnresolvedIssueDto.class);
        if (isEmpty(unresolvedIssues)) {
            LOG.info("No unresolved issues found.");
            lastDataSyncDao.update(lastDataSync);
            return;
        }
        final List<String> emailRecipients = operatorService.findOperatorEmails(PHONE_DEBT_COLLECTOR_MANAGER);
        emailSendMessageEnqueueService.enqueueUnresolvedIssuesEmail(new UnresolvedIssuesEmailMessage(emailRecipients, lastDataSync.getFromDate(),
                lastDataSync.getToDate(), unresolvedIssues));
        LOG.info("Unresolved issues email message queued. Number of issues: {}", unresolvedIssues.size());
        lastDataSyncDao.update(lastDataSync);
    }

    private LastDataSync prepareLastDataSync() {
        final LastDataSync lastDataSync = lastDataSyncDao.findByType(FIND_UNRESOLVED_ISSUES).orElse(createLastDataSync());
        lastDataSync.setFromDate(lastDataSync.getToDate());
        lastDataSync.setToDate(dateProvider.getCurrentDate());
        return lastDataSync;
    }

    private LastDataSync createLastDataSync() {
        final LastDataSync lastDataSync = new LastDataSync();
        lastDataSync.setType(FIND_UNRESOLVED_ISSUES);
        lastDataSync.setToDate(dateProvider.getDateStartOfDayMinusDays(SYNC_DATA_DAY_START));
        return lastDataSync;
    }
}
