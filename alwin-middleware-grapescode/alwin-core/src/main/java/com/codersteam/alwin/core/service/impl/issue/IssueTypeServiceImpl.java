package com.codersteam.alwin.core.service.impl.issue;

import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;
import com.codersteam.alwin.core.api.model.issue.IssueTypeWithOperatorTypesDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.issue.IssueTypeService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueTypeDao;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.type.OperatorNameType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class IssueTypeServiceImpl implements IssueTypeService {

    private final IssueTypeDao issueTypeDao;
    private final AlwinMapper alwinMapper;

    @Inject
    public IssueTypeServiceImpl(final IssueTypeDao issueTypeDao, final AlwinMapper alwinMapper) {
        this.issueTypeDao = issueTypeDao;
        this.alwinMapper = alwinMapper;
    }

    /**
     * Pobiera wszystkie typy zlecenia windykacyjnego
     *
     * @return lista typów zlecenia windykacyjnego
     */
    @Override
    public List<IssueTypeWithOperatorTypesDto> findAllIssueTypes() {
        final List<IssueType> types = issueTypeDao.findAllIssueTypes();
        return alwinMapper.mapAsList(types, IssueTypeWithOperatorTypesDto.class);
    }

    @Override
    public List<IssueTypeDto> findIssueTypesByOperatorType(final OperatorType operatorType) {
        final OperatorNameType operatorNameType = alwinMapper.map(operatorType, OperatorNameType.class);
        final List<IssueType> types = issueTypeDao.findIssueTypesByOperatorNameType(operatorNameType);
        return alwinMapper.mapAsList(types, IssueTypeDto.class);
    }

    @Override
    public IssueTypeWithOperatorTypesDto findIssueTypeById(final Long issueTypeId) {
        final Optional<IssueType> issueType = issueTypeDao.findIssueTypeById(issueTypeId);
        return issueType.map(type -> alwinMapper.map(type, IssueTypeWithOperatorTypesDto.class)).orElse(null);
    }
}
