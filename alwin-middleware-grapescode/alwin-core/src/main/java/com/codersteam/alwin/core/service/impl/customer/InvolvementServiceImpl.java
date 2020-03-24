package com.codersteam.alwin.core.service.impl.customer;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.customer.InvolvementService;
import com.codersteam.alwin.db.dao.CompanyDao;
import com.codersteam.alwin.jpa.customer.Company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

/**
 * @author Piotr Naroznik
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class InvolvementServiceImpl implements InvolvementService {

    private final CompanyDao companyDao;
    private final com.codersteam.aida.core.api.service.InvolvementService aidaInvolvementService;

    @Inject
    public InvolvementServiceImpl(final CompanyDao companyDao, final AidaService aidaService) {
        this.companyDao = companyDao;
        this.aidaInvolvementService = aidaService.getInvolvementService();
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public void updateCompanyInvolvement(final Long companyId) {
        final Company company = companyDao.get(companyId).orElseThrow(() -> new EntityNotFoundException(companyId));
        updateCompanyInvolvement(company);
    }


    private void updateCompanyInvolvement(final Company company) {
        final BigDecimal involvement = aidaInvolvementService.calculateCompanyInvolvement(company.getExtCompanyId());
        if (!involvement.equals(company.getInvolvement())) {
            company.setInvolvement(involvement);
            companyDao.update(company);
        }
    }
}