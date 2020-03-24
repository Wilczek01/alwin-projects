package com.codersteam.alwin.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {

    @PersistenceContext(unitName = "AlwinUnit")
    protected EntityManager entityManager;

    protected abstract Class<T> getType();

    protected <T> Optional<T> checkForSingleResult(final List<T> resultList) {
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public void create(final T object) {
        entityManager.persist(object);
    }

    public T update(final T object) {
        return entityManager.merge(object);
    }

    public void delete(final Object id) {
        final T o = entityManager.find(getType(), id);
        entityManager.remove(o);
    }

    public List<T> all() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(getType());

        cq.select(cq.from(getType()));
        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * Zwraca listę obiektów posortowanych po identyfikatorze
     *
     * @return lista obiektów
     */
    public List<T> allOrderedById() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(getType());
        final Root<T> from = cq.from(getType());

        cq.select(from).orderBy(cb.asc(from.get("id")));
        return entityManager.createQuery(cq).getResultList();
    }

    public Optional<T> get(final Object id) {
        final T t = entityManager.find(getType(), id);
        return t == null ? Optional.empty() : Optional.of(t);
    }

    /**
     * Sprawdza czy obiekt o podanym identyfikatorze istnieje
     *
     * @param id - identyfikator obiektu
     * @return true jeżeli obiekt istnieje, false w przeciwnym razie
     */
    public boolean exists(final Object id) {
        final String queryString = "select count(*) from " + getType().getSimpleName() + " obj where obj.id = :id";
        final TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("id", id);
        return query.getSingleResult().intValue() == 1;
    }
}
