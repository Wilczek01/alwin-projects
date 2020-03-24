package com.codersteam.alwin.core.service.impl.operator;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.IssueTypeDao;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.OperatorNameType;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

import static com.codersteam.alwin.core.api.model.common.Page.emptyPage;
import static com.codersteam.alwin.jpa.type.OperatorNameType.ACCOUNT_MANAGER;
import static com.codersteam.alwin.jpa.type.OperatorNameType.FIELD_DEBT_COLLECTOR;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class OperatorServiceImpl implements OperatorService {

    private final OperatorDao operatorDao;
    private final IssueTypeDao issueTypeDao;
    private final AlwinMapper mapper;

    @Inject
    public OperatorServiceImpl(final OperatorDao operatorDao, final IssueTypeDao issueTypeDao, final AlwinMapper mapper) {
        this.mapper = mapper;
        this.operatorDao = operatorDao;
        this.issueTypeDao = issueTypeDao;
    }

    @Override
    public List<OperatorDto> findManagedOperators(final Long parentOperatorId, final Long issueTypeId) {
        final List<Operator> operators = operatorDao.findActiveManagedOperators(parentOperatorId);
        if (isEmpty(operators)) {
            return emptyList();
        }

        return mapOperators(filterOperatorsByIssueType(operators, issueTypeId));
    }

    protected List<Operator> filterOperatorsByIssueType(final List<Operator> operators, final Long issueTypeId) {
        if (issueTypeId == null) {
            return operators;
        }

        final Optional<IssueType> issueType = issueTypeDao.findIssueTypeById(issueTypeId);
        if (!issueType.isPresent()) {
            return operators;
        }

        final Set<com.codersteam.alwin.jpa.operator.OperatorType> operatorTypes = issueType.get().getOperatorTypes();
        if (isEmpty(operatorTypes)) {
            return emptyList();
        }

        return operators.stream().filter(operator -> operatorTypes.contains(operator.getType())).collect(toList());
    }

    private List<OperatorDto> mapOperators(final List<Operator> operators) {
        return mapper.mapAsList(operators, OperatorDto.class);
    }

    @Override
    public List<String> findOperatorEmails(final OperatorType operatorType) {
        final OperatorNameType operatorNameType = mapper.map(operatorType, OperatorNameType.class);
        final List<Operator> operators = operatorDao.findOperatorsByNameType(operatorNameType);
        if (isEmpty(operators)) {
            return emptyList();
        }
        return operators.stream().map(operator -> operator.getUser().getEmail()).collect(toList());
    }

    @Override
    public List<String> checkIfOperatorsExist(final List<String> logins) {
        return operatorDao.findExistingOperatorLogins(logins);
    }

    @Override
    public Page<OperatorDto> findAllOperatorsFilterByNameAndLogin(final int firstResult, final int maxResults, final String searchText,
                                                                  final long excludedOperatorId) {
        final List<Operator> users = operatorDao.findAllOperatorsFilterByNameAndLogin(firstResult, maxResults, searchText, excludedOperatorId);
        if (isEmpty(users)) {
            return emptyPage();
        }
        final long allUsersCount = operatorDao.findAllOperatorsFilterByNameAndLoginCount(searchText, excludedOperatorId);
        final List<OperatorDto> mappedUsers = mapper.mapAsList(users, OperatorDto.class);
        return new Page<>(mappedUsers, allUsersCount);
    }

    @Override
    public OperatorDto findOperatorById(final Long operatorId) {
        final Optional<Operator> operator = operatorDao.get(operatorId);
        return operator.map(alwinOperator -> mapper.map(alwinOperator, OperatorDto.class)).orElse(null);
    }

    @Override
    public List<OperatorUserDto> findOperatorsAssignToCompanyActivities(final long companyId) {
        final List<Operator> operators = operatorDao.findOperatorsAssignToCompanyActivities(companyId);
        if (isEmpty(operators)) {
            return emptyList();
        }
        return mapper.mapAsList(operators, OperatorUserDto.class);
    }

    @Override
    public void changePassword(final long operatorId, final String newPassword, final boolean forceToUpdate) {
        final Operator operator = operatorDao.get(operatorId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to find operator with id %s", operatorId)));
        final String salt = DigestUtils.sha512Hex(new Date().toString() + UUID.randomUUID().toString());
        final String password = DigestUtils.sha512Hex(salt + newPassword);
        operator.setSalt(salt);
        operator.setPassword(password);
        operator.setForceUpdatePassword(forceToUpdate);
        operatorDao.update(operator);
    }

    @Override
    public List<OperatorDto> findAllAccountManagers() {
        final List<Operator> operators = operatorDao.findOperatorsByNameType(ACCOUNT_MANAGER);
        if (isEmpty(operators)) {
            return emptyList();
        }
        return mapOperators(operators);
    }

    @Override
    public boolean checkIfOperatorExists(final long operatorId) {
        return operatorDao.exists(operatorId);
    }

    @Override
    public Page<OperatorDto> findActiveFieldDebtCollectors(final int firstResult, final int maxResults) {
        final List<Operator> operators = operatorDao.findActiveOperatorsByType(FIELD_DEBT_COLLECTOR, firstResult, maxResults);
        final long count = operatorDao.findActiveOperatorsByTypeCount(FIELD_DEBT_COLLECTOR);
        return new Page<>(mapper.mapAsList(operators, OperatorDto.class), count);
    }
}
