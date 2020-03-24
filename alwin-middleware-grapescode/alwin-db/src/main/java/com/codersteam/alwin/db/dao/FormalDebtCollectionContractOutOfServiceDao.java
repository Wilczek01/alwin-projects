package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionContractOutOfService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author Piotr Naroznik
 */
@Stateless
public class FormalDebtCollectionContractOutOfServiceDao extends AbstractDao<FormalDebtCollectionContractOutOfService> {

    /**
     * Pobiera listę aktywnych zablokowań kontraktów dla podanego klienta z podanego przedziału czasu
     *
     * @param extCompanyId      - identyfikator klienta z zewnętrznego systemu
     * @param investigationDate - data sprawdzenia bieżącej listy zablokowanych kontraktów klienta
     * @return lista zablokowanych kontraktów
     */
    public List<FormalDebtCollectionContractOutOfService> findActiveFormalDebtCollectionContractsOutOfService(final long extCompanyId, final Date investigationDate) {
        final String queryString = "select cos from FormalDebtCollectionContractOutOfService cos where cos.extCompanyId = :extCompanyId and " +
                "((cos.startDate <= :investigationDate and cos.endDate is null) or (cos.startDate <= :investigationDate and cos.endDate >= :investigationDate)) " +
                "order by cos.id";
        final TypedQuery<FormalDebtCollectionContractOutOfService> query = entityManager.createQuery(queryString, FormalDebtCollectionContractOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        query.setParameter("investigationDate", investigationDate);
        return query.getResultList();
    }

    /**
     * Sprawdza czy podana umowa jest zablokowana dla podanego przedziału czasu
     *
     * @param contractNumber       - numer umowy z zewnętrznego systemu
     * @param investigationDate    - data sprawdzenia bieżącej listy zablokowanych kontraktów klienta
     * @param demandForPaymentType - typ wezwania do zapłaty
     * @return true jeżeli umowa jest zablokowana, false w przeciwnym przypadku
     */
    public boolean isFormalDebtCollectionContractOutOfService(final String contractNumber, final Date investigationDate, final DemandForPaymentTypeKey demandForPaymentType) {
        final String queryString = "select count(*) from FormalDebtCollectionContractOutOfService cos where cos.contractNo = :contractNumber and " +
                "((cos.startDate <= :investigationDate and cos.endDate is null) or (cos.startDate <= :investigationDate and cos.endDate >= :investigationDate)) " +
                "and cos.demandForPaymentType =:demandForPaymentType ";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("contractNumber", contractNumber);
        query.setParameter("investigationDate", investigationDate);
        query.setParameter("demandForPaymentType", demandForPaymentType);
        return query.getSingleResult() != 0;
    }

    /**
     * Pobiera listę wszystkich zablokowań kontraktów dla podanego klienta
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista zablokowanych kontraktów
     */
    public List<FormalDebtCollectionContractOutOfService> findAllFormalDebtCollectionContractsOutOfService(final long extCompanyId) {
        final String queryString = "select cos from FormalDebtCollectionContractOutOfService cos where cos.extCompanyId = :extCompanyId " +
                "order by cos.startDate, cos.endDate, cos.id";
        final TypedQuery<FormalDebtCollectionContractOutOfService> query = entityManager.createQuery(queryString, FormalDebtCollectionContractOutOfService.class);
        query.setParameter("extCompanyId", extCompanyId);
        return query.getResultList();
    }

    @Override
    public Class<FormalDebtCollectionContractOutOfService> getType() {
        return FormalDebtCollectionContractOutOfService.class;
    }

    /**
     * Tworzy nową blokadę kontraktu klienta w windykacji formalnej.
     * Ustawia jako autora blokady operatora o podanym identyfikatorze.
     *
     * @param contractOutOfService - blokada kontraktu klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego kontrakt klienta
     */
    public void create(final FormalDebtCollectionContractOutOfService contractOutOfService, final Long blockingOperatorId) {
        contractOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        create(contractOutOfService);
    }

    /**
     * Sprawdza czy istnieje blokada w windykacji formalnej o podanym identyfikatorze blokady umowy klienta oraz identyfikatorze klienta
     *
     * @param contractOutOfServiceId - identyfikator blokady umowy klienta
     * @param extCompanyId           - identyfikator klienta z zewnętrznego systemu
     * @return - true jeżeli blokada nie istnieje, false w przeciwnym wypadku
     */
    public boolean checkIfFormalDebtCollectionContractOutOfServiceExists(final long contractOutOfServiceId, final long extCompanyId) {
        final String queryString = "select count(*) from FormalDebtCollectionContractOutOfService cos where cos.id = :contractOutOfServiceId and cos.extCompanyId = :extCompanyId";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("contractOutOfServiceId", contractOutOfServiceId);
        query.setParameter("extCompanyId", extCompanyId);
        return query.getSingleResult() != 0;
    }

    /**
     * Aktualizuje blokadę umowy klienta w windykacji formalnej ustawiając jako autora blokady operatora o podanym identyfikatorze
     *
     * @param contractOutOfService - blokada umowy klienta
     * @param blockingOperatorId   - identyfikator operatora blokującego umowę klienta
     */
    public void update(final FormalDebtCollectionContractOutOfService contractOutOfService, final Long blockingOperatorId) {
        contractOutOfService.setBlockingOperator(entityManager.find(Operator.class, blockingOperatorId));
        update(contractOutOfService);
    }
}
