package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.Company;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do firmmy
 *
 * @author Piotr Naroznik
 */
@Stateless
public class CompanyDao extends AbstractAuditDao<Company> {

    /**
     * Pobieranie firmy na podstawie zewnętrznego identyfikatora firmy
     *
     * @param extCompanyId - zewnętrzny identyfikator firmy
     * @return firma
     */
    public Optional<Company> findCompanyByExtId(final Long extCompanyId) {
        final String query = "select c from Company c where c.extCompanyId = :extCompanyId";
        final List<Company> companies = entityManager.createQuery(query, Company.class)
                .setParameter("extCompanyId", extCompanyId)
                .getResultList();

        return checkForSingleResult(companies);
    }

    @Override
    public Class<Company> getType() {
        return Company.class;
    }
}