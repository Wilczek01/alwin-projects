package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.service.DateProvider;
import com.codersteam.alwin.core.api.service.issue.TagService;
import com.codersteam.alwin.db.dao.IssueDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class CreateTagService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateTagService.class.getName());

    private final DateProvider dateProvider;
    private final TagService tagService;
    private final IssueDao issueDao;

    @Inject
    public CreateTagService(DateProvider dateProvider, TagService tagService, IssueDao issueDao) {
        this.dateProvider = dateProvider;
        this.tagService = tagService;
        this.issueDao = issueDao;
    }

    public void tagOverdueIssues() {
        final List<Long> issueIds = issueDao.findOverdueIssueIdsPerDay(dateProvider.getCurrentDate());
        issueIds.forEach(issueId -> {
            try {
                tagService.tagOverdueIssue(issueId);
            } catch (final Exception e) {
                LOG.error("Cannot tag overdue issue with id {}", issueId, e);
            }
        });
    }
}
