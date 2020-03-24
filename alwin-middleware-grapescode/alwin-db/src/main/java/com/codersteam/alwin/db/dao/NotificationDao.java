package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.Notification;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa dostępu do powiadomień dla zlecenia
 *
 * @author Adam Stepnowski
 */
@Stateless
public class NotificationDao extends AbstractDao<Notification> {

    /**
     * Pobiera wszystkie powiadomienia dla danego zlecenia
     * Powiadomienia posortowane są po dacie utworzenia
     *
     * @param issueId - identyfikator zlecenia
     * @return lista powiadomień
     */
    public List<Notification> findAllIssueNotifications(final Long issueId) {
        final TypedQuery<Notification> query = entityManager.createQuery("SELECT n FROM Notification n WHERE n.issueId = :issueId ORDER BY n.creationDate desc",
                Notification.class);
        query.setParameter("issueId", issueId);
        return query.getResultList();
    }

    @Override
    protected Class<Notification> getType() {
        return Notification.class;
    }
}
