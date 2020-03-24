package com.codersteam.alwin.core.service.impl.operator;

import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.service.operator.OperatorTypeService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.OperatorTypeDao;
import com.codersteam.alwin.jpa.operator.OperatorType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class OperatorTypeServiceImpl implements OperatorTypeService {

    private final OperatorTypeDao operatorTypeDao;
    private final AlwinMapper mapper;

    @Inject
    public OperatorTypeServiceImpl(final OperatorTypeDao operatorTypeDao, final AlwinMapper mapper) {
        this.mapper = mapper;
        this.operatorTypeDao = operatorTypeDao;
    }

    @Override
    public List<OperatorTypeDto> findAllTypes() {
        final List<OperatorType> operatorTypes = operatorTypeDao.findAll();
        if (isEmpty(operatorTypes)) {
            return emptyList();
        }
        return mapper.mapAsList(operatorTypes, OperatorTypeDto.class);
    }
}
