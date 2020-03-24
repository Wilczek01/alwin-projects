package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.db.dao.criteria.PostalCodeCriteriaBuilder;
import com.codersteam.alwin.jpa.PostalCode;
import com.codersteam.alwin.jpa.operator.Operator;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do masek kodów pocztowych przypisanych do operatorów
 *
 * @author Michal Horowic
 */
@Stateless
public class PostalCodeDao extends AbstractAuditDao<PostalCode> {

    /**
     * Pobiera listę pasujących masek do przesłanej maski
     * maska 12-345 zwraca tylko maskę 12-345
     * maska 12-3xx zwraca maskę 12-345, 12-34x i 12-3xx
     * maska 1x-xxx zwraca wszystkie maski zaczynające się od 1
     *
     * @param mask - maska kodu pocztowego
     * @return lista mask kodów pocztowych
     */
    public List<PostalCode> findPostalCodesByMask(final String mask) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<PostalCode> query = PostalCodeCriteriaBuilder.createQuery(criteriaBuilder, mask);
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Pobiera ilość pasujących masek do przesłanej maski
     * maska 12-345 zwraca tylko jedną maskę
     * maska 12-3xx zwraca wszystkie maski, które zaczynają się od 12-3
     * maska 1x-xxx zwraca wszystkie maski zaczynające się od 1
     *
     * @param mask - maska kodu pocztowego
     * @return ilość masek kodów pocztowych
     */
    public long findPostalCodesByMaskCount(final String mask) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = PostalCodeCriteriaBuilder.createCountQuery(criteriaBuilder, mask);
        return entityManager.createQuery(query).getSingleResult();
    }

    /**
     * Pobiera operatora dla danego pełnego kodu pocztowego wg masek zapisanych w bazie
     * Jeżeli dla kodu pocztowego 12-345 istnieją następujace przypisania
     * operator 1 z maską 12-3xx
     * operator 2 z maską 12-34x
     * operator 3 z maską 12-345
     * to zostanie zwrócony operator 3, jeżeli natomiast istnieją przypisania:
     * operator 1 z maską 12-3xx
     * operator 2 z maską 12-34x
     * to zostanie zwrócony operator 2, jeżeli natomiast istnieje tylko przypisanie:
     * operator 1 z maską 12-3xx
     * to zostanie zwrócony operator 1
     *
     * @param postalCode - pełny kod pocztowy
     * @return operator do którego należy obsługa miejsca z podanym kodem pocztowym
     */
    public Optional<Operator> findOperatorForPostalCode(final String postalCode) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Operator> query = PostalCodeCriteriaBuilder.createQueryForMatchingOperator(criteriaBuilder, postalCode);
        final TypedQuery<Operator> builtQuery = entityManager.createQuery(query);
        builtQuery.setMaxResults(1);
        return checkForSingleResult(builtQuery.getResultList());
    }

    @Override
    public PostalCode update(final PostalCode object) {
        entityManager.getReference(Operator.class, object.getOperator() != null ? object.getOperator().getId() : null);
        return super.update(object);
    }

    @Override
    public void create(final PostalCode object) {
        entityManager.getReference(Operator.class, object.getOperator() != null ? object.getOperator().getId() : null);
        super.create(object);
    }

    @Override
    public Class<PostalCode> getType() {
        return PostalCode.class;
    }

    /**
     * Sprawdza czy podana maska kodu pocztowego już istnieje
     *
     * @param mask - maska kodu pocztowego
     * @return true jeżeli maska kodu pocztowego istnieje, false w przeciwnym wypadku
     */
    public boolean checkIfPostalCodeMaskExists(final String mask) {
        final TypedQuery<Long> query = entityManager.createQuery("select count(*) from PostalCode p where p.mask = :mask", Long.class);
        query.setParameter("mask", mask);
        return query.getSingleResult() != 0;
    }
}
