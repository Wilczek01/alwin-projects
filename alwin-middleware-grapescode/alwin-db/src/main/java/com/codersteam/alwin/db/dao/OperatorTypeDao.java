package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.operator.OperatorType;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa dostępu do typów operatora
 *
 * @author Michal Horowic
 */
@Stateless
public class OperatorTypeDao extends AbstractAuditDao<OperatorType> {

    public List<OperatorType> findAll() {
        final TypedQuery<OperatorType> query = entityManager.createQuery("SELECT ot FROM OperatorType ot order by ot.id ", OperatorType.class);
        return query.getResultList();
    }

    @Override
    public Class<OperatorType> getType() {
        return OperatorType.class;
    }

}
