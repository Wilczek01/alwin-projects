package com.codersteam.alwin.core.api.service.operator;

import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Michal Horowic
 */
@Local
public interface OperatorTypeService {

    /**
     * Pobiera wszystkie typy operatorów
     *
     * @return lista typów operatorów
     */
    List<OperatorTypeDto> findAllTypes();

}
