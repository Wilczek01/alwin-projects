package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionCustomerOutOfService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author Piotr Naroznik
 */
@Stateless
public class FormalDebtCollectionCustomerOutOfServiceDao extends AbstractDao<FormalDebtCollectionCustomerOutOfService> {

    /**
     * Sprawdzenie, czy klient nie jest wyłączony z obsługi
     *
     * @param customerId    id klienta
     * @param operationDate data
     */
    public boolean isCustomerOutOfFormalDebtCollection(final Long customerId, final Date operationDate) {
        final TypedQuery<Long> query = entityManager.createQuery("select count(*) from FormalDebtCollectionCustomerOutOfService oos where " +
                "oos.customer.id = :customerId and ((oos.startDate <= :operationDate and oos.endDate is null) or (oos.startDate <= :operationDate and " +
                "oos.endDate >= :operationDate))", Long.class);
        query.setParameter("customerId", customerId);
        query.setParameter("operationDate", operationDate);
        return query.getSingleResult() != 0;
    }

    /**
     * Sprawdzenie, czy klient nie jest wyłączony z obsługi
     *
     * @param customerId           - identyfikator klienta
     * @param operationDate        - data
     * @param demandForPaymentType - typ wezwania do zapłaty
     */
    public boolean isCustomerOutOfFormalDebtCollection(final Long customerId, final Date operationDate, final DemandForPaymentTypeKey demandForPaymentType) {
        final TypedQuery<Long> query = entityManager.createQuery("select count(*) from FormalDebtCollectionCustomerOutOfService oos where " +
                "oos.customer.id = :customerId and ((oos.startDate <= :operationDate and oos.endDate is null) or (oos.startDate <= :operationDate and " +
                "oos.endDate >= :operationDate)) and oos.demandForPaymentType = :demandForPaymentType", Long.class);
        query.setParameter("customerId", customerId);
        query.setParameter("operationDate", operationDate);
        query.setParameter("demandForPaymentType", demandForPaymentType);
        return query.getSingleResult() != 0;
    }

    /**
     * Zwraca listę aktywnych blokad klienta na podany dzień
     *
     * @param extCompanyId      - identyfikator firmy z zewnętrznego systemu
     * @param investigationDate - data sprawdzenia bieżącej listy blokad
     * @return lista blokad klienta
     */
    public List<FormalDebtCollectionCustomerOutOfService> findFormalDebtCollectionCustomersOutOfService(final long extCompanyId, final Date investigationDate) {
        final String queryString = "select cos from FormalDebtCollectionCustomerOutOfService cos where cos.customer.company.extCompanyId = :extCompanyId and " +
                "((cos.startDate <= :investigationDate and cos.endDate is null) or (cos.startDate <= :investigationDate and cos.endDate >= :investigationDate))" +
                " order by cos.id";
        final TypedQuery<FormalDebtCollectionCustomerOutOfService> query = entityManager.createQuery(queryString, FormalDebtCollectionCustomerOutOfService.class);
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
    public List<FormalDebtCollectionCustomerOutOfService> findAllFormalDebtCollectionCustomersOutOfService(final long extCompanyId) {
        final String queryString = "select cos from FormalDebtCollectionCustomerOutOfService cos where cos.customer.company.extCompanyId = :extCompanyId " +
                "order by cos.id";
        final TypedQuery<FormalDebtCollectionCustomerOutOfService> query = entityManager.createQuery(queryString, FormalDebtCollectionCustomerOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        return query.getResultList();
    }

    @Override
    protected Class<FormalDebtCollectionCustomerOutOfService> getType() {
        return FormalDebtCollectionCustomerOutOfService.class;
    }

    /**
     * Tworzy nową blokadę windykacji formalnej dla podanego klienta ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param customerOutOfService - blokada klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego klienta
     */
    public void create(final FormalDebtCollectionCustomerOutOfService customerOutOfService, final Long blockingOperatorId) {
        customerOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        create(customerOutOfService);
    }

    /**
     * Zlicza ilość rekordów o podanym identyfikatorze blokady klienta
     *
     * @param customerOutOfServiceId - identyfikator blokady klienta
     * @return - true jeżeli blokada istnieje, false w przeciwnym wypadku
     */
    public boolean checkIfFormalDebtCollectionCustomerOutOfServiceExists(final long customerOutOfServiceId) {
        final String queryString = "select count(*) from FormalDebtCollectionCustomerOutOfService cos where cos.id = :customerOutOfServiceId";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("customerOutOfServiceId", customerOutOfServiceId);
        return query.getSingleResult() != 0;
    }

    /**
     * Aktualizuje blokadę dla podanego klienta ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param customerOutOfService - blokada klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego klienta
     */
    public void update(final FormalDebtCollectionCustomerOutOfService customerOutOfService, final Long blockingOperatorId) {
        customerOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        update(customerOutOfService);
    }
}
