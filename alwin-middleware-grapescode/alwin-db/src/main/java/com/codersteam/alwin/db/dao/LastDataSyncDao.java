package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.LastDataSync;
import com.codersteam.alwin.jpa.type.LastDataSyncType;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * Klasa dostępu do ostatnich synchronizacji danych
 *
 * @author Piotr Naroznik
 */
@Stateless
public class LastDataSyncDao extends AbstractDao<LastDataSync> {

    /**
     * Pobieranie inforamcji o ostatniej aktualizacji danych po typie
     *
     * @param type - typ zmienianych danych
     * @return informacje o ostaniej aktualizacji danych
     */
    public Optional<LastDataSync> findByType(final LastDataSyncType type) {
        final String query = "select a from LastDataSync a where a.type = :type";
        return checkForSingleResult(entityManager.createQuery(query, LastDataSync.class).setParameter("type", type).getResultList());
    }

    @Override
    public Class<LastDataSync> getType() {
        return LastDataSync.class;
    }
}
