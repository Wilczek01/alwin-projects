package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.issue.IssueTypeTransition;
import org.hibernate.annotations.QueryHints;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Klasa dostępu do przejść między zleceniami
 *
 * @author Piotr Naroznik
 */
@Stateless
public class IssueTypeTransitionDao extends AbstractDao<IssueTypeTransition> {

    @Override
    public Class<IssueTypeTransition> getType() {
        return IssueTypeTransition.class;
    }

    public List<IssueTypeTransition> findByClosedIssueType(final IssueType closedIssueType) {
        final String sql = "select itd from IssueTypeTransition itd where itd.closedIssueType =:closedIssueType";
        return entityManager.createQuery(sql, IssueTypeTransition.class)
                .setHint(QueryHints.CACHEABLE, Boolean.TRUE)
                .setParameter("closedIssueType", closedIssueType)
                .getResultList();
    }
}
