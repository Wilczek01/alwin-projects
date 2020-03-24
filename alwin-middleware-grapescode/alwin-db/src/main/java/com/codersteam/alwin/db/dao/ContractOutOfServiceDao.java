package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.customer.ContractOutOfService;
import com.codersteam.alwin.jpa.operator.Operator;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Horowic
 */
@Stateless
public class ContractOutOfServiceDao extends AbstractDao<ContractOutOfService> {


    /**
     * Pobiera listę aktywnych zablokowań kontraktów dla podanego klienta z podanego przedziału czasu lub wszystkie jeśli nie podano daty początkowej
     * przedziału
     *
     * @param extCompanyId      - identyfikator klienta z zewnętrznego systemu
     * @param investigationDate - data sprawdzenia bieżącej listy zablokowanych kontraktów klienta
     * @return lista zablokowanych kontraktów
     */
    public List<ContractOutOfService> findActiveContractsOutOfService(final long extCompanyId, final Date investigationDate) {
        final String queryString = "select cos from ContractOutOfService cos where cos.extCompanyId = :extCompanyId and " +
                "((cos.startDate <= :investigationDate and cos.endDate is null) or (cos.startDate <= :investigationDate and cos.endDate >= :investigationDate)) " +
                "order by cos.id";
        final TypedQuery<ContractOutOfService> query = entityManager.createQuery(queryString, ContractOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        query.setParameter("investigationDate", investigationDate);
        return query.getResultList();
    }

    /**
     * Pobiera listę wszystkich zablokowań kontraktów dla podanego klienta
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista zablokowanych kontraktów
     */
    public List<ContractOutOfService> findAllContractsOutOfService(final long extCompanyId) {
        final String queryString = "select cos from ContractOutOfService cos where cos.extCompanyId = :extCompanyId " +
                "order by cos.startDate, cos.endDate, cos.id";
        final TypedQuery<ContractOutOfService> query = entityManager.createQuery(queryString, ContractOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        return query.getResultList();
    }

    @Override
    public Class<ContractOutOfService> getType() {
        return ContractOutOfService.class;
    }

    /**
     * Tworzy nową blokadę kontraktu klienta ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param contractOutOfService - blokada kontraktu klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego kontrakt klienta
     */
    public void create(final ContractOutOfService contractOutOfService, final Long blockingOperatorId) {
        contractOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        create(contractOutOfService);
    }

    /**
     * Aktualizuje blokadę umowy klienta ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param contractOutOfService - blokada umowy klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego umowę klienta
     */
    public void update(final ContractOutOfService contractOutOfService, final Long blockingOperatorId) {
        contractOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        update(contractOutOfService);
    }

    /**
     * Zlicza ilośc rekordów o podanym identyfikatorze blokady umowy klienta oraz identyfikatorze klienta
     *
     * @param contractOutOfServiceId - identyfikator blokady umowy klienta
     * @param extCompanyId           - identyfikator klienta z zewnętrznego systemu
     * @return - 0 jeżeli blokada nie istnieje, 1 w przeciwnym wypadku
     */
    public int checkIfContractOutOfServiceExists(final long contractOutOfServiceId, final long extCompanyId) {
        final String queryString = "select count(*) from ContractOutOfService cos where cos.id = :contractOutOfServiceId and cos.extCompanyId = :extCompanyId";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("contractOutOfServiceId", contractOutOfServiceId);
        query.setParameter("extCompanyId", extCompanyId);
        return query.getSingleResult().intValue();
    }
}
