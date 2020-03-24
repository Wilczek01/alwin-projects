package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.issue.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class CloseExpiredIssuesService {

    private static final Logger LOG = LoggerFactory.getLogger(CloseExpiredIssuesService.class.getName());

    private DateProvider dateProvider;
    private IssueService issueService;
    private IssueDao issueDao;

    public CloseExpiredIssuesService() {
    }

    @Inject
    public CloseExpiredIssuesService(DateProvider dateProvider,
                                     IssueService issueService,
                                     IssueDao issueDao) {
        this.dateProvider = dateProvider;
        this.issueService = issueService;
        this.issueDao = issueDao;
    }

    public void closeExpiredIssuesAndCreateChildIssuesIfNeeded() {
        final Date dueDate = dateProvider.getCurrentDateStartOfDay();
        LOG.info("close expired issues for {} started", dueDate);
        final List<Issue> issuesToClose = issueDao.findIssuesToClose(dueDate);
        if (issuesToClose.isEmpty()) {
            LOG.info("No issues found to close for date {}", dueDate);
            return;
        }
        for (final Issue issue : issuesToClose) {
            try {
                issueService.closeIssueWithActivitiesAndCreateChildIssueIfNeeded(issue.getId());
                LOG.info("Expired issue with id={} has been successfully closed", issue.getId());
            } catch (final Exception e) {
                LOG.error("Close expired issue with id={} failed", issue.getId(), e);
            }
        }
        LOG.info("close {} expired issues for {}", issuesToClose.size(), dueDate);
    }
}
