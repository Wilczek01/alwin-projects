package com.codersteam.alwin.core.service.impl.activity;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.core.api.service.activity.CreateActivityService;
import com.codersteam.alwin.db.dao.IssueDao;

import javax.inject.Inject;
import java.util.List;

import static com.codersteam.alwin.common.issue.IssueTypeName.DIRECT_DEBT_COLLECTION;
import static com.codersteam.alwin.common.issue.IssueTypeName.PHONE_DEBT_COLLECTION;
import static java.util.Collections.singletonList;

public class OutgoingCallActivityService {

    private final IssueDao issueDao;
    private final CreateActivityService createActivityService;

    @Inject
    public OutgoingCallActivityService(IssueDao issueDao,
                                       CreateActivityService createActivityService) {
        this.issueDao = issueDao;
        this.createActivityService = createActivityService;
    }

    public void createOutgoingCallActivity() {
        final List<Long> allActivePhoneDebtCollectionIssuesIds = getActiveIssuesIds(PHONE_DEBT_COLLECTION);
        allActivePhoneDebtCollectionIssuesIds.forEach(createActivityService::createNewPhoneDebtCollectionActivity);

        final List<Long> allActiveDirectDebtCollectionIssuesIds = getActiveIssuesIds(singletonList(DIRECT_DEBT_COLLECTION));
        allActiveDirectDebtCollectionIssuesIds.forEach(createActivityService::createNewDirectDebtCollectionActivity);
    }

    private List<Long> getActiveIssuesIds(List<IssueTypeName> phoneDebtCollection) {
        return issueDao.findActiveIssuesIds(phoneDebtCollection);
    }
}
