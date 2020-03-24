package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.issue.IssueTerminationRequestDto;

import javax.ejb.Local;

/**
 * Serwis do obsługi żądań przedterminowego zakończenia zlecenia
 *
 * @author Piotr Naroznik
 */
@Local
public interface IssueTerminationService {

    /**
     * Zaakceptowanie żądania o przedterminowe zakończenie zlecenia oraz zakończenie zlecenia.
     *
     * @param issueTerminationRequestId - identyfikator żądania o przedterminowe zakończenie zlecenia
     * @param operatorId                - identyfikator operatora
     * @param terminationRequest        - dane do zakończenia zlecenia
     */
    void accept(Long issueTerminationRequestId, Long operatorId, IssueTerminationRequestDto terminationRequest);

    /**
     * Odrzucenie żądania o przedterminowe zakończenie zlecenia.
     *
     * @param issueTerminationRequestId - identyfikator żądania o przedterminowe zakończenie zlecenia
     * @param operatorId                - identyfikator operatora
     * @param terminationRequest        - dane do zakończenia zlecenia
     */
    void reject(Long issueTerminationRequestId, Long operatorId, IssueTerminationRequestDto terminationRequest);

    /**
     * Pobieranie otwartego żądania o przedterminowe zakończenia zlecenia
     *
     * @param issueId - identyfikator zlecenia
     * @return żądanie przedterminowego zakończenie zlecenia
     */
    IssueTerminationRequestDto findTerminationRequestByIssueId(Long issueId);

    /**
     * Pobieranie żądania o przedterminowe zakończenia zlecenia
     *
     * @param issueTerminationRequestId - identyfikator zlecenia
     * @return żądanie przedterminowego zakończenie zlecenia
     */
    IssueTerminationRequestDto findTerminationRequestById(Long issueTerminationRequestId);
}