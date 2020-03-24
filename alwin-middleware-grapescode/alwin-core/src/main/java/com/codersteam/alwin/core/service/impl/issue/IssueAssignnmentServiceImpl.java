package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.model.issue.IssueDto;
import com.codersteam.alwin.core.api.service.issue.IssueAssignnmentService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Singleton
public class IssueAssignnmentServiceImpl implements IssueAssignnmentService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private IssueService issueService;

    public IssueAssignnmentServiceImpl() {
    }

    @Inject
    public IssueAssignnmentServiceImpl(IssueService issueService) {
        this.issueService = issueService;
    }

    @Lock(LockType.WRITE)
    @AccessTimeout(value = 10, unit = TimeUnit.MINUTES)
    public Optional<IssueDto> findWorkForUser(final Long userId) {
        LOG.info("findWorkForUser: " + userId);
        Optional<IssueDto> issueToWorkOn = issueService.findIssueToWorkOn(userId);

        while (true) {
            if (!issueToWorkOn.isPresent()) {
                return Optional.empty();
            }
            final Optional<IssueDto> recalculatedIssue = issueService.getRecalculatedIssueIfNotClosed(issueToWorkOn.get().getId());
            if (recalculatedIssue.isPresent()) {
                LOG.info("findWorkForUser: " + userId + " finished");
                return recalculatedIssue;
            }
            issueToWorkOn = issueService.findIssueToWorkOn(userId);
        }
    }
}
