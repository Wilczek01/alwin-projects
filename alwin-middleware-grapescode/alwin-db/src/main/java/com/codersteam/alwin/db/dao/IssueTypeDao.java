package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.type.OperatorNameType;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Klasa dostępu do typów zlecenia
 *
 * @author Piotr Naroznik
 */
@Stateless
public class IssueTypeDao extends AbstractAuditDao<IssueType> {

    /**
     * pobieranie typu zlecenia windykacyjnego na podstawie nazwy
     *
     * @param issueTypeName - typ zlecenia windykacyjnego
     * @return typ zlecenia
     */
    public IssueType findIssueTypeByName(final IssueTypeName issueTypeName) {
        final TypedQuery<IssueType> query = entityManager.createQuery("SELECT i FROM IssueType i WHERE i.name =:issueTypeName", IssueType.class);
        query.setParameter("issueTypeName", issueTypeName);
        return query.getSingleResult();
    }

    /**
     * pobieranie typu zlecenia windykacyjnego na podstawie identyfikatora
     *
     * @param issueTypeId - identyfikator typu zlecenia windykacyjnego
     * @return typ zlecenia
     */
    public Optional<IssueType> findIssueTypeById(final Long issueTypeId) {
        final TypedQuery<IssueType> query = entityManager.createQuery("SELECT i FROM IssueType i WHERE i.id =:issueTypeId", IssueType.class);
        query.setParameter("issueTypeId", issueTypeId);
        final List<IssueType> issueTypes = query.getResultList();
        return checkForSingleResult(issueTypes);
    }

    /**
     * Pobiera wszystkie typy zlecenia windykacyjnego
     *
     * @return lista typów zlecenia windykacyjnego
     */
    public List<IssueType> findAllIssueTypes() {
        final String sql = "SELECT i FROM IssueType i order by i.id";
        return entityManager.createQuery(sql, IssueType.class).getResultList();
    }

    /**
     * Pobiera typy zlecenia dla operatora
     *
     * @param operatorNameType - nazwa typ operatora
     * @return lista typów zlecenia windykacyjnego
     */
    public List<IssueType> findIssueTypesByOperatorNameType(final OperatorNameType operatorNameType) {
        final String sql = "SELECT it FROM IssueType it join it.operatorTypes ot where ot.typeName =:operatorNameType order by it.id";
        return entityManager.createQuery(sql, IssueType.class)
                .setParameter("operatorNameType", operatorNameType)
                .getResultList();
    }

    @Override
    public Class<IssueType> getType() {
        return IssueType.class;
    }

}
