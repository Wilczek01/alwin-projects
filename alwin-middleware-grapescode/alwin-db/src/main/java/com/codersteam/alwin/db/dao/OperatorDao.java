package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.OperatorNameType;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do danych użytkowników systemu
 *
 * @author Michal Horowic
 */
@Stateless
public class OperatorDao extends AbstractAuditDao<Operator> {

    /**
     * Znajduje aktywnego operatora dla podanego loginu
     *
     * @param login - login operatora
     * @return opcjonalnie aktywny operator o podanym loginie
     */
    public Optional<Operator> findActiveOperatorByLogin(final String login) {
        final List<Operator> users = entityManager.createQuery("SELECT o FROM Operator o WHERE o.login = :login AND o.active = true", Operator.class)
                .setParameter("login", login)
                .getResultList();

        return checkForSingleResult(users);
    }

    /**
     * Zwraca listę tych loginów operatorów z podanych, które już istnieją
     *
     * @param logins - loginy operatorów
     * @return loginy istniejących operatorów
     */
    public List<String> findExistingOperatorLogins(final List<String> logins) {
        final TypedQuery<String> query = entityManager.createQuery("SELECT o.login FROM Operator o WHERE o.login in (:logins)", String.class);
        query.setParameter("logins", logins);
        return query.getResultList();
    }

    public List<Operator> findActiveManagedOperators(final long parentOperatorId) {
        return entityManager.createQuery("SELECT o FROM Operator o  JOIN o.parentOperator p JOIN o.type t JOIN p.type pt" +
                " WHERE o.active = true AND p.id = :parentOperatorId AND pt.id = t.parentOperatorType.id order by o.id", Operator.class)
                .setParameter("parentOperatorId", parentOperatorId)
                .getResultList();
    }

    /**
     * Zwraca listę operatorów, którzy są przypisani do jakichkolwiek czynności klienta o podanym identyfikatorze
     *
     * @param companyId - identyfikator klienta
     * @return - lista operatorów
     */
    public List<Operator> findOperatorsAssignToCompanyActivities(final long companyId) {
        return entityManager.createQuery("SELECT distinct o FROM Issue i left JOIN i.customer.company c left JOIN i.contract.customer.company cc " +
                "JOIN i.activities a JOIN a.operator o WHERE o.active = true AND (c.id = :companyId or cc.id = :companyId) order by o.id", Operator.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

    /**
     * Pobranie listy operatorów po typie
     *
     * @param operatorNameType - typ operatora
     * @return lista operatorów danego typu
     */
    public List<Operator> findOperatorsByNameType(final OperatorNameType operatorNameType) {
        return entityManager
                .createQuery("SELECT o FROM Operator o WHERE o.type.typeName = :nameType", Operator.class)
                .setParameter("nameType", operatorNameType)
                .getResultList();
    }

    /**
     * Pobiera stronicowana listę aktywnych operatorów o podanym typie
     *
     * @param operatorNameType - typ operatora
     * @param firstResult      - pierwsza pozycja na stronie
     * @param maxResults       - ilość pozycji per strona
     * @return - lista aktywnych operatorów
     */
    public List<Operator> findActiveOperatorsByType(final OperatorNameType operatorNameType, final int firstResult, final int maxResults) {
        return entityManager
                .createQuery("SELECT o FROM Operator o WHERE o.type.typeName = :nameType and o.active = true order by o.id", Operator.class)
                .setParameter("nameType", operatorNameType)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    /**
     * Zwraca ilość aktywnych operatorów o podanym typie
     *
     * @param operatorNameType - typ operatora
     * @return ilość aktywnych operatorów
     */
    public long findActiveOperatorsByTypeCount(final OperatorNameType operatorNameType) {
        return entityManager
                .createQuery("SELECT count(*) FROM Operator o WHERE o.type.typeName = :nameType and o.active = true", Long.class)
                .setParameter("nameType", operatorNameType)
                .getSingleResult();
    }

    /**
     * Zwraca ilość operatorów w systemie, których login, imię, nazwisko lub imię i nazwisko oddzielone spacją dla użytkownika zgadzają dla się z
     * przekazanym parametrem. W wynikach pomijany jest operator, który odpytuje o listę
     *
     * @param searchText         - ciąg znaków dopasowywany do loginu, imienia, nazwiska lub imienia i nazwiska razem
     * @param excludedOperatorId - identyfikator odpytującego operatora
     * @return ilość operatorów
     */
    public long findAllOperatorsFilterByNameAndLoginCount(final String searchText, final long excludedOperatorId) {
        final String queryString = "select count(o) from User u join u.operators o where o.id != :excludedOperatorId and (u.firstName like :searchText or " +
                "u.lastName like :searchText or o.login like :searchText or concat(u.firstName, ' ', u.lastName) like :searchText)";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("searchText", "%" + (searchText != null ? searchText : "") + "%");
        query.setParameter("excludedOperatorId", excludedOperatorId);
        return query.getSingleResult();
    }

    /**
     * Zwraca stronicowaną listę operatorów, których login, imię, nazwisko lub imię i nazwisko oddzielone spacją dla użytkownika zgadzają dla się z
     * przekazanym parametrem. W wynikach pomijany jest operator, który odpytuje o listę
     *
     * @param firstResult        - pierwszy element na liście
     * @param maxResults         - maksymalna ilość elementów na stronie
     * @param searchText         - ciąg znaków dopasowywany do loginu, imienia, nazwiska lub imienia i nazwiska razem
     * @param excludedOperatorId - identyfikator odpytującego operatora
     * @return strona z listą operatorów
     */
    public List<Operator> findAllOperatorsFilterByNameAndLogin(final int firstResult, final int maxResults, final String searchText, final long excludedOperatorId) {
        final String queryString = "select o from User u join u.operators o where o.id != :excludedOperatorId and (u.firstName like :searchText or " +
                "u.lastName like :searchText or o.login like :searchText or concat(u.firstName, ' ', u.lastName) like :searchText) group by o.id order by o.id";
        final TypedQuery<Operator> query = entityManager.createQuery(queryString, Operator.class);
        query.setParameter("searchText", "%" + (searchText != null ? searchText : "") + "%");
        query.setParameter("excludedOperatorId", excludedOperatorId);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    /**
     * Pobieranie typów zleceń, do których ma uprawnienia dany operator
     *
     * @param operatorId - identyfikator operatora
     * @return lista typów zleceń
     */
    public List<IssueTypeName> findOperatorIssueTypes(final Long operatorId) {
        final String queryString = "select it.name from Operator o, IssueType it join it.operatorTypes ot where o.id=:operatorId and ot.id=o.type.id";
        return entityManager.createQuery(queryString, IssueTypeName.class)
                .setParameter("operatorId", operatorId)
                .getResultList();
    }

    @Override
    public Class<Operator> getType() {
        return Operator.class;
    }
}
