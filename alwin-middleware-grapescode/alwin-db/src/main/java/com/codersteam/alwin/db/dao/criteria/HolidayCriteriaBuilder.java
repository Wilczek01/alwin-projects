package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.User_;
import com.codersteam.alwin.jpa.holiday.Holiday;
import com.codersteam.alwin.jpa.holiday.Holiday_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa tworząca warunki potrzebne do filtrowania dni wolnych
 *
 * @author Michal Horowic
 */
public final class HolidayCriteriaBuilder {

    private HolidayCriteriaBuilder() {
    }

    /**
     * Tworzy zapytanie pobierające dni wolne filtrowane po dniu, miesiącu, roku oraz identyfikatorze użytkownika
     *
     * @param cb     - klasa budująca kryteria zapytania
     * @param day    - dzień
     * @param month  - miesiąc
     * @param year   - rok
     * @param userId - identyfikator użytkownika
     * @return zapytanie pobierające dni wolne
     */
    public static CriteriaQuery<Holiday> createQuery(CriteriaBuilder cb,
                                                     Integer day,
                                                     Integer month,
                                                     Integer year,
                                                     Long userId) {
        CriteriaQuery<Holiday> criteriaQuery = cb.createQuery(Holiday.class);
        Root<Holiday> holiday = criteriaQuery.from(Holiday.class);
        List<Predicate> predicates = buildPredicates(cb, day, month, year, userId, holiday);
        return criteriaQuery.select(holiday)
                .orderBy(cb.asc(holiday.get(Holiday_.id)))
                .where(predicates.toArray(new Predicate[0]));
    }

    private static List<Predicate> buildPredicates(CriteriaBuilder cb,
                                                   Integer day,
                                                   Integer month,
                                                   Integer year,
                                                   Long userId,
                                                   Root<Holiday> holiday) {
        final List<Predicate> predicates = new ArrayList<>();

        if (day != null) {
            predicates.add(cb.equal(holiday.get(Holiday_.day), day));
        }
        if (month != null) {
            predicates.add(cb.equal(holiday.get(Holiday_.month), month));
        }
        if (userId != null) {
            Join<Holiday, User> userJoin = holiday.join(Holiday_.user, JoinType.LEFT);
            predicates.add(
                    cb.or(
                            cb.equal(userJoin.get(User_.id), userId),
                            cb.isNull(holiday.get(Holiday_.user))
                    )
            );
        }
        predicates.add(
                cb.or(
                        cb.equal(holiday.get(Holiday_.year), year),
                        cb.isNull(holiday.get(Holiday_.year))
                )
        );

        return predicates;
    }

    /**
     * Tworzy zapytanie pobierające dni wolne filtrowane po miesiącu, roku oraz identyfikatorze użytkownika
     *
     * @param cb     - klasa budująca kryteria zapytania
     * @param month  - miesiąc
     * @param year   - rok
     * @param userId - identyfikator użytkownika
     * @return zapytanie pobierające dni wolne
     */
    public static CriteriaQuery<Holiday> createQuery(final CriteriaBuilder cb, final Integer month, final Integer year, final Long userId) {
        return createQuery(cb, null, month, year, userId);
    }

    /**
     * Tworzy zapytanie pobierające dni wolne filtrowane po roku oraz identyfikatorze użytkownika
     *
     * @param cb     - klasa budująca kryteria zapytania
     * @param year   - rok
     * @param userId - identyfikator użytkownika
     * @return zapytanie pobierające dni wolne
     */
    public static CriteriaQuery<Holiday> createQuery(final CriteriaBuilder cb, final Integer year, final Long userId) {
        return createQuery(cb, null, null, year, userId);
    }
}
