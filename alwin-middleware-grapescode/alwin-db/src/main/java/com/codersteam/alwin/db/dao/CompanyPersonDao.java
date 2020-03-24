package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.CompanyPerson;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do osób uprawnionych firmy
 *
 * @author Piotr Naroznik
 */
@Stateless
public class CompanyPersonDao extends AbstractDao<CompanyPerson> {

    /**
     * Pobieranie osoby uprawnionej firmy
     *
     * @param personId  - identyfikator osoby
     * @param companyId - identyfikator firmy
     * @return osoba uprawniona
     */
    public Optional<CompanyPerson> findByPersonIdAndCompanyId(final Long personId, final Long companyId) {
        final String sql = "select cp from CompanyPerson cp where cp.person.id = :personId and cp.company.id = :companyId";
        final List<CompanyPerson> resultList = entityManager.createQuery(sql, CompanyPerson.class)
                .setParameter("personId", personId)
                .setParameter("companyId", companyId)
                .getResultList();
        return checkForSingleResult(resultList);
    }

    @Override
    public Class<CompanyPerson> getType() {
        return CompanyPerson.class;
    }
}