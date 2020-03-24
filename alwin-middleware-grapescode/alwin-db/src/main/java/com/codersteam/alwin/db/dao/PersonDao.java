package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.Person;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do osoby
 *
 * @author Piotr Naroznik
 */
@Stateless
public class PersonDao extends AbstractAuditDao<Person> {

    @Override
    public Class<Person> getType() {
        return Person.class;
    }

    /**
     * Pobieranie osoby po zewnętrznym identyfikatorze osoby oraz firmy
     *
     * @param extPersonId  - zewnętrzny identyfikator osoby
     * @param extCompanyId - zewnętrzny identyfikator firmy
     * @return osoba
     */
    public Optional<Person> findByExtPersonIdAndExtCompanyId(final Long extPersonId, final Long extCompanyId) {
        final String sql = "select cp.id.person from CompanyPerson cp where cp.id.person.personId=:extPersonId and cp.id.company.extCompanyId=:extCompanyId";
        final List<Person> resultList = entityManager.createQuery(sql, Person.class)
                .setParameter("extPersonId", extPersonId)
                .setParameter("extCompanyId", extCompanyId)
                .getResultList();
        return checkForSingleResult(resultList);
    }
}