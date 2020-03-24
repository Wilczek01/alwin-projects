package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.issue.IssueTag;

import javax.ejb.Stateless;

/**
 * Klasa dostępu do powiązania zleceń z etykietami
 *
 * @author Michal Horowic
 */
@Stateless
public class IssueTagDao extends AbstractAuditDao<IssueTag> {

    /**
     * Usuwa wszystkie przypisania do zleceń etykiety o podanym identyfikatorze
     *
     * @param tagId - identyfikator etykiety
     */
    public void deleteAllAssignments(final long tagId) {
        entityManager.createQuery("delete from IssueTag where tagId = :tagId").setParameter("tagId", tagId).executeUpdate();
    }

    @Override
    public Class<IssueTag> getType() {
        return IssueTag.class;
    }


}
