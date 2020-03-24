package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.User;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Klasa dostępu do danych użytkowników systemu
 *
 * @author Michal Horowic
 */
@Stateless
public class UserDao extends AbstractAuditDao<User> {

    /**
     * Zwraca ilość wszystkich użytkowników w systemie
     *
     * @return ilość użytkowników
     */
    public long findAllUsersCount() {
        return entityManager.createQuery("SELECT count(*) FROM User u", Long.class)
                .getSingleResult();
    }

    /**
     * Zwraca stronicowaną listę wszystkich użytkowników w systemie
     *
     * @param firstResult - pierwszy element na liście
     * @param maxResults  - maksymalna ilość elementów na stronie
     * @return strona z listą użytkowników
     */
    public List<User> findAllUsers(final int firstResult, final int maxResults) {
        return entityManager.createQuery("select u from User u order by u.id", User.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    /**
     * Zwraca ilość użytkowników w systemie, których login, imię, nazwisko lub imię i nazwisko oddzielone spacją zgadzają się z przekazanym parametrem
     *
     * @param searchText - ciąg znaków dopasowywany do loginu, imienia, nazwiska lub imienia i nazwiska razem
     * @return ilość użytkowników
     */
    public long findAllUsersFilterByNameAndLoginCount(final String searchText) {
        return entityManager.createQuery("SELECT count(*) from User u join u.operators o where u.firstName like :searchText or " +
                "u.lastName like :searchText or o.login like :searchText or concat(u.firstName, ' ', u.lastName) like :searchText", Long.class)
                .setParameter("searchText", "%" + searchText + "%")
                .getSingleResult();
    }

    /**
     * Zwraca stronicowaną listę użytkowników, których login, imię, nazwisko lub imię i nazwisko oddzielone spacją zgadzają się z przekazanym parametrem
     *
     * @param firstResult - pierwszy element na liście
     * @param maxResults  - maksymalna ilość elementów na stronie
     * @param searchText  - ciąg znaków dopasowywany do loginu, imienia, nazwiska lub imienia i nazwiska razem
     * @return strona z listą użytkowników
     */
    public List<User> findAllUsersFilterByNameAndLogin(final int firstResult, final int maxResults, final String searchText) {
        final String queryString = "select u from User u join u.operators o where u.firstName like :searchText or u.lastName like :searchText or o.login like :searchText or concat(u.firstName, ' ', u.lastName) like :searchText order by u.id";
        return entityManager.createQuery(queryString, User.class)
                .setParameter("searchText", "%" + searchText + "%")
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
