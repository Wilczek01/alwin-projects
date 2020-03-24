package com.codersteam.alwin.db.dao.criteria;

import com.codersteam.alwin.jpa.PostalCode;
import com.codersteam.alwin.jpa.PostalCode_;
import com.codersteam.alwin.jpa.operator.Operator;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.codersteam.alwin.common.search.util.PostalCodeUtils.buildAllPossibleMasksGivenMask;
import static com.codersteam.alwin.common.search.util.PostalCodeUtils.extractPostalCodeStart;

/**
 * Klasa tworząca warunki potrzebne do filtrowania masek kodów pocztowych
 *
 * @author Michal Horowic
 */
public final class PostalCodeCriteriaBuilder {

    private PostalCodeCriteriaBuilder() {
    }

    /**
     * Tworzy zapytanie pobierające maski kodow pocztowych filtrowane po masce
     *
     * @param cb   - klasa budująca kryteria zapytania
     * @param mask - maska kodu pocztowego
     * @return zapytanie pobierające maski kodów pocztowych
     */
    public static CriteriaQuery<PostalCode> createQuery(CriteriaBuilder cb,
                                                        String mask) {
        CriteriaQuery<PostalCode> criteriaQuery = cb.createQuery(PostalCode.class);
        Root<PostalCode> postalCode = criteriaQuery.from(PostalCode.class);
        List<Predicate> predicates = buildPredicates(cb, mask, postalCode);
        criteriaQuery.select(postalCode)
                .orderBy(cb.asc(postalCode.get(PostalCode_.id)));
        return predicates.isEmpty() ? criteriaQuery : criteriaQuery.where(cb.or(predicates.toArray(new Predicate[0])));
    }

    /**
     * Tworzy zapytanie pobierające ilość masek kodów pocztowych filtrowanych po masce
     *
     * @param cb   - klasa budująca kryteria zapytania
     * @param mask - maska kodu pocztowego
     * @return zapytanie pobierające ilość masek kodów pocztowych
     */
    public static CriteriaQuery<Long> createCountQuery(CriteriaBuilder cb,
                                                       String mask) {
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<PostalCode> postalCode = criteriaQuery.from(PostalCode.class);
        List<Predicate> predicates = buildPredicates(cb, mask, postalCode);
        criteriaQuery.select(cb.countDistinct(postalCode));
        return predicates.isEmpty() ? criteriaQuery : criteriaQuery.where(cb.or(predicates.toArray(new Predicate[0])));
    }

    /**
     * Tworzy zapytanie pobierające operatora z najlepiej dopasowaną maską kodu pocztowego do podanego kodu pocztowego
     *
     * @param cb              - klasa budująca kryteria zapytania
     * @param postalCodeValue - kod pocztowy
     * @return zapytanie pobierające operatora dla podanego kodu pocztowego
     */
    public static CriteriaQuery<Operator> createQueryForMatchingOperator(CriteriaBuilder cb,
                                                                         String postalCodeValue) {
        CriteriaQuery<Operator> criteriaQuery = cb.createQuery(Operator.class);
        Root<PostalCode> postalCode = criteriaQuery.from(PostalCode.class);
        List<Predicate> predicates = buildPredicates(cb, postalCodeValue, postalCode);

        Expression locateX = cb.locate(cb.lower(postalCode.get(PostalCode_.mask)), "x");
        Expression<Object> orderByMatching = cb.selectCase()
                .when(cb.equal(locateX, 0), 6)
                .otherwise(locateX);

        criteriaQuery.select(postalCode.get(PostalCode_.operator)).orderBy(
                cb.desc(orderByMatching),
                cb.asc(postalCode.get(PostalCode_.id))
        );
        return criteriaQuery.where(cb.or(predicates.toArray(new Predicate[0])));
    }

    private static List<Predicate> buildPredicates(CriteriaBuilder cb,
                                                   String mask,
                                                   Root<PostalCode> postalCode) {
        List<Predicate> predicates = new ArrayList<>();
        String postalCodeStart = extractPostalCodeStart(mask);
        if (StringUtils.isNotEmpty(postalCodeStart)) {
            predicates.add(cb.like(cb.lower(postalCode.get(PostalCode_.mask)), String.format("%s%%", postalCodeStart.toLowerCase())));
        }
        buildAllPossibleMasksGivenMask(mask)
                .stream()
                .map(m -> cb.equal(postalCode.get(PostalCode_.mask), m))
                .forEach(predicates::add);
        return predicates;
    }
}
