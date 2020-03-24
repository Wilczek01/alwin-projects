package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.jpa.OperatorsQueue;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class OperatorsQueueDao extends AbstractAuditDao<OperatorsQueue> {


    public Optional<OperatorsQueue> getOperatorsQueue(final Long userId, final List<IssueTypeName> operatorIssueTypes) {
        TypedQuery<OperatorsQueue> query = entityManager.createQuery("SELECT op FROM OperatorsQueue op " +
                "WHERE (op.operatorId = :userId OR op.operatorId IS NULL) and op.issueType in :issueTypeNames", OperatorsQueue.class);
        query.setParameter("issueTypeNames", operatorIssueTypes);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        return checkForSingleResult(query.getResultList());
    }

    @Override
    protected Class<OperatorsQueue> getType() {
        return OperatorsQueue.class;
    }
}
