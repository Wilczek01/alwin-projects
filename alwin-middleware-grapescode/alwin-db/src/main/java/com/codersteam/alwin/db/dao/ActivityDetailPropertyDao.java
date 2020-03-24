package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.activity.ActivityDetailProperty;
import com.codersteam.alwin.jpa.type.ActivityDetailPropertyKey;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do cech dodatkowych zdarzeń dla czynności windykacyjnych
 *
 * @author Adam Stepnowski
 */
@Stateless
public class ActivityDetailPropertyDao extends AbstractDao<ActivityDetailProperty> {

    /**
     * Pobieranie wszystkich cech czynności windykacyjnych
     *
     * @return lista cech czynności windykacyjnych
     */
    public List<ActivityDetailProperty> findAllActivityDetailProperties() {
        final String sql = "SELECT a FROM ActivityDetailProperty a order by a.id";
        return entityManager.createQuery(sql, ActivityDetailProperty.class).getResultList();
    }

    /**
     * Pobieranie cechy czynności windykacyjnej po kluczu
     *
     * @param propertyKey - klucz cechy czynności windykacyjnej
     * @return cecha czynności windykacyjnych
     */
    public Optional<ActivityDetailProperty> findActivityDetailProperty(final ActivityDetailPropertyKey propertyKey) {
        final String sql = "SELECT a FROM ActivityDetailProperty a where a.key = :propertyKey";
        final List<ActivityDetailProperty> activityDetailProperties = entityManager.createQuery(sql, ActivityDetailProperty.class)
                .setParameter("propertyKey", propertyKey).getResultList();
        return checkForSingleResult(activityDetailProperties);
    }

    @Override
    public Class<ActivityDetailProperty> getType() {
        return ActivityDetailProperty.class;
    }
}
