package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.service.customer.InvolvementService;
import com.codersteam.alwin.db.dao.IssueDao;
import com.codersteam.alwin.jpa.customer.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class UpdateCompaniesInvolvementService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateCompaniesInvolvementService.class.getName());

    private final IssueDao issueDao;
    private final InvolvementService involvementService;

    @Inject
    public UpdateCompaniesInvolvementService(IssueDao issueDao,
                                             InvolvementService involvementService) {
        this.issueDao = issueDao;
        this.involvementService = involvementService;
    }

    public void updateCompaniesInvolvement() {
        final List<Company> companies = getAllCompaniesWithActiveIssue();
        companies.forEach(company -> {
                    try {
                        involvementService.updateCompanyInvolvement(company.getId());
                    } catch (final Exception e) {
                        LOG.error("Can not update involvement for company with id {}", company.getId(), e);
                    }
                }
        );
    }

    private List<Company> getAllCompaniesWithActiveIssue() {
        return issueDao.findAllCompaniesWithActiveIssue();
    }
}
