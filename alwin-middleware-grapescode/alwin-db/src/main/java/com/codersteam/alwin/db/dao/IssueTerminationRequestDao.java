package com.codersteam.alwin.db.dao;

import com.codersteam.alwin.jpa.issue.IssueTerminationRequest;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static com.codersteam.alwin.jpa.type.IssueTerminationRequestState.NEW;

/**
 * @author Piotr Naroznik
 */
@Stateless
public class IssueTerminationRequestDao extends AbstractDao<IssueTerminationRequest> {

    @Override
    public Class<IssueTerminationRequest> getType() {
        return IssueTerminationRequest.class;
    }

    /**
     * Pobieranie otwartego żądania o przedterminowe zakończenie zlecenia po identyfikatorze zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return żądanie przedterminowego zakończenia zlecenia
     */
    public Optional<IssueTerminationRequest> findIssueTerminationRequestByIssueId(final Long issueId) {
        final String sql = "select itr from IssueTerminationRequest itr where itr.issue.id =:issueId and itr.state = :state";
        final TypedQuery<IssueTerminationRequest> query = entityManager.createQuery(sql, IssueTerminationRequest.class);
        query.setParameter("issueId", issueId);
        query.setParameter("state", NEW);
        return checkForSingleResult(query.getResultList());
    }
}