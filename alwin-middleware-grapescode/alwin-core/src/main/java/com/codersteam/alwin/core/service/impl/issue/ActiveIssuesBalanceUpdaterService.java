package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService;
import com.codersteam.alwin.db.dao.IssueDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class ActiveIssuesBalanceUpdaterService {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveIssuesBalanceUpdaterService.class.getName());

    private IssueDao issueDao;
    private IssueBalanceUpdaterService issueBalanceUpdaterService;

    public ActiveIssuesBalanceUpdaterService() {
        // For deployment
    }

    @Inject
    public ActiveIssuesBalanceUpdaterService(final IssueDao issueDao, final IssueBalanceUpdaterService issueBalanceUpdaterService) {
        this.issueDao = issueDao;
        this.issueBalanceUpdaterService = issueBalanceUpdaterService;
    }

    public List<Long> getIssues() {
        return issueDao.findAllActiveIssuesIds();
    }

    public void updateActiveIssuesBalance() {
        LOG.info("Updating issues balance started");
        final List<Long> issues = getIssues();
        LOG.info("Updating balance for {} active issues", issues.size());
        issues.forEach(issue -> {
            try {
                issueBalanceUpdaterService.updateIssueBalance(issue);
            } catch (final Exception e) {
                LOG.error("Update balance for issue with id={} failed", issue, e);
            }
        });
        LOG.info("Updating issues balance ended");
    }
}
