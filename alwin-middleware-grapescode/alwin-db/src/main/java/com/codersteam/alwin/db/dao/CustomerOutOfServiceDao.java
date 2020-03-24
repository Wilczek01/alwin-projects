package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.CustomerOutOfService;
import com.codersteam.alwin.jpa.operator.Operator;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author Tomasz Sliwinski
 */
@Stateless
public class CustomerOutOfServiceDao extends AbstractDao<CustomerOutOfService> {

    /**
     * Zlicza ilość rekordów o podanym identyfikatorze blokady klienta
     *
     * @param customerOutOfServiceId - identyfikator blokady klienta
     * @return - 0 jeżeli blokada nie istnieje, 1 w przeciwnym wypadku
     */
    public int checkIfCustomerOutOfServiceExists(final long customerOutOfServiceId) {
        final String queryString = "select count(*) from CustomerOutOfService cos where cos.id = :customerOutOfServiceId";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("customerOutOfServiceId", customerOutOfServiceId);
        return query.getSingleResult().intValue();
    }

    /**
     * Sprawdzenie, czy klient nie jest wyłączony z obsługi
     *
     * @param customerId    id klienta
     * @param operationDate data
     */
    public boolean isCustomerOutOfService(final Long customerId, final Date operationDate) {
        final TypedQuery<Long> query = entityManager.createQuery("select count(*) from CustomerOutOfService oos where oos.customer.id = :customerId " +
                "and ((oos.startDate <= :operationDate and oos.endDate is null) or (oos.startDate <= :operationDate and oos.endDate >= :operationDate))", Long.class);
        query.setParameter("customerId", customerId);
        query.setParameter("operationDate", operationDate);
        return query.getSingleResult() != 0;
    }

    /**
     * Zwraca listę aktywnych blokad klienta na podany dzień
     *
     * @param extCompanyId      - identyfikator firmy z zewnętrznego systemu
     * @param investigationDate - data sprawdzenia bieżącej listy blokad
     * @return lista blokad klienta
     */
    public List<CustomerOutOfService> findCustomersOutOfService(final long extCompanyId, final Date investigationDate) {
        final String queryString = "select cos from CustomerOutOfService cos where cos.customer.company.extCompanyId = :extCompanyId and " +
                "((cos.startDate <= :investigationDate and cos.endDate is null) or (cos.startDate <= :investigationDate and cos.endDate >= :investigationDate)) order by cos.id";
        final TypedQuery<CustomerOutOfService> query = entityManager.createQuery(queryString, CustomerOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        query.setParameter("investigationDate", investigationDate);
        return query.getResultList();
    }

    /**
     * Zwraca listę wszystkich blokad klienta
     *
     * @param extCompanyId - identyfikator firmy z zewnętrznego systemu
     * @return lista blokad klienta
     */
    public List<CustomerOutOfService> findAllCustomersOutOfService(final long extCompanyId) {
        final String queryString = "select cos from CustomerOutOfService cos where cos.customer.company.extCompanyId = :extCompanyId order by cos.id";
        final TypedQuery<CustomerOutOfService> query = entityManager.createQuery(queryString, CustomerOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        return query.getResultList();
    }

    @Override
    protected Class<CustomerOutOfService> getType() {
        return CustomerOutOfService.class;
    }

    /**
     * Tworzy nową blokadę dla podanego klienta ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param customerOutOfService - blokada klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego klienta
     */
    public void create(final CustomerOutOfService customerOutOfService, final Long blockingOperatorId) {
        customerOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        create(customerOutOfService);
    }

    /**
     * Aktualizuje blokadę dla podanego klienta ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param customerOutOfService - blokada klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego klienta
     */
    public void update(final CustomerOutOfService customerOutOfService, final Long blockingOperatorId) {
        customerOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        update(customerOutOfService);
    }
}
