package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.issue.IssueDto;

import java.util.Optional;

public interface IssueAssignnmentService {

    /**
     * Znajduje najważniejsze zlecenia przypisane do operatora.
     * W przypadku zleceń utworzonych automatycznie zwracane są jedynie te, które posiadają zaległości (ujemne saldo bieżące).
     * Jeżeli operator nie ma przypisanego zlecenia, przypisuje mu najważniejsze nieprzypisane zlecenie.
     * Jeżeli nie uda się znaleźć lub przypisać zlecenia, zwracana jest pusta odpowiedź.
     *
     * @param userId - identyfikator operatora
     * @return - bieżące zlecenie
     */
     Optional<IssueDto> findWorkForUser(final Long userId);
}
