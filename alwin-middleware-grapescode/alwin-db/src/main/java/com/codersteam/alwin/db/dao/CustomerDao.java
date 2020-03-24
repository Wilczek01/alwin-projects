package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.Customer;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do klientów
 *
 * @author Adam Stepnowski
 */
@Stateless
public class CustomerDao extends AbstractAuditDao<Customer> {

    /**
     * Pobiera kontrahenta.
     *
     * @param extCompanyId - id firmy kontrahenta z zewnętrznego systemu
     * @return kontrahent
     */

    public Optional<Customer> findCustomerByExternalCompanyId(final long extCompanyId) {
        final List<Customer> customers =
                entityManager.createQuery("SELECT c FROM Customer c JOIN c.company cu WHERE cu.extCompanyId = :extCompanyId", Customer.class)
                        .setParameter("extCompanyId", extCompanyId)
                        .getResultList();

        return checkForSingleResult(customers);
    }

    /**
     * Modyfikacja opiekuna klienta.
     * Usuwanie następuje, gdy przekazany identyifikator opiekuna jest pusty
     *
     * @param customerId       - identyfikator klienta
     * @param accountManagerId - identyfikator opiekuna
     * @return ilość zaaktualizowanych klientów
     */

    public int updateCustomerAccountManager(final long customerId, final Long accountManagerId) {
        final String sql = "update Customer c set c.accountManager.id = :accountManagerId WHERE c.id = :customerId";
        final Query query = entityManager.createQuery(sql)
                .setParameter("customerId", customerId)
                .setParameter("accountManagerId", accountManagerId);
        return query.executeUpdate();
    }

    @Override
    public Class<Customer> getType() {
        return Customer.class;
    }
}

