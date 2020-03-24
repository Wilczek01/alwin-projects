package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;
import com.codersteam.alwin.core.api.model.issue.IssueTypeWithOperatorTypesDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@Local
public interface IssueTypeService {

    /**
     * Pobiera wszystkie typy zlecenia windykacyjnego
     *
     * @return lista typów zlecenia windykacyjnego
     */
    List<IssueTypeWithOperatorTypesDto> findAllIssueTypes();

    /**
     * Pobiera typy zlecenia dla operatora
     *
     * @param operatorType - nazwa typ operatora
     * @return lista typów zlecenia windykacyjnego
     */
    List<IssueTypeDto> findIssueTypesByOperatorType(final OperatorType operatorType);

    /**
     * Pobieranie typu zlecenia windykacyjnego na podstawie identyfikatora wraz z typami operatorów
     *
     * @param issueTypeId - identyfikator typu zlecenia windykacyjnego
     * @return typ zlecenia
     */
    IssueTypeWithOperatorTypesDto findIssueTypeById(Long issueTypeId);
}
