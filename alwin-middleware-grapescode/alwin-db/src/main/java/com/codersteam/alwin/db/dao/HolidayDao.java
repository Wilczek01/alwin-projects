package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.db.dao.criteria.HolidayCriteriaBuilder;
import com.codersteam.alwin.jpa.holiday.Holiday;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Klasa dostępu do dni wolnych
 *
 * @author Michal Horowic
 */
@Stateless
public class HolidayDao extends AbstractDao<Holiday> {

    /**
     * Pobiera listę dni wolnych dla danego dnia, w przypadku przekazania identyfikatora użytkownika zwraca tylko jego dni wolne
     *
     * @param day   - dzień do sprawdzenia dni wolnych
     * @param month - miesiąc dla dnia do sprawdzenia dni wolnych
     * @param year  - rok dla dnia do sprawdzenia dni wolnych
     * @return lista dni wolnych dla danego dnia
     */
    public List<Holiday> findAllHolidaysPerDay(final int day, final int month, final int year, final Long userId) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Holiday> query = HolidayCriteriaBuilder.createQuery(criteriaBuilder, day, month, year, userId);
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Pobiera listę dni wolnych dla danego miesiąca, w przypadku przekazania identyfikatora użytkownika zwraca tylko jego dni wolne
     *
     * @param month - miesiąc do sprawdzenia dni wolnych
     * @param year  - rok dla miesiąc do sprawdzenia dni wolnych
     * @return lista dni wolnych dla danego miesiąca
     */
    public List<Holiday> findAllHolidaysPerMonth(final int month, final int year, final Long userId) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Holiday> query = HolidayCriteriaBuilder.createQuery(criteriaBuilder, month, year, userId);
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Pobiera listę dni wolnych dla danego roku, w przypadku przekazania identyfikatora użytkownika zwraca tylko jego dni wolne
     *
     * @param year - rok do sprawdzenia dni wolnych
     * @return lista dni wolnych dla danego roku
     */
    public List<Holiday> findAllHolidaysPerYear(final int year, final Long userId) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Holiday> query = HolidayCriteriaBuilder.createQuery(criteriaBuilder, year, userId);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Class<Holiday> getType() {
        return Holiday.class;
    }
}
