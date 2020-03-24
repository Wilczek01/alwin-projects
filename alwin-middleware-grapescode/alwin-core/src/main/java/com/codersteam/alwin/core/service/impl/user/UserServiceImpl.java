package com.codersteam.alwin.core.service.impl.user;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.EditableOperatorDto;
import com.codersteam.alwin.core.api.model.user.EditableUserDto;
import com.codersteam.alwin.core.api.model.user.FullUserDto;
import com.codersteam.alwin.core.api.model.user.UserDto;
import com.codersteam.alwin.core.api.service.user.UserService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.db.dao.OperatorTypeDao;
import com.codersteam.alwin.db.dao.UserDao;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.operator.OperatorType;
import com.codersteam.alwin.jpa.operator.Permission;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

import static com.codersteam.alwin.core.api.model.common.Page.emptyPage;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final OperatorDao operatorDao;
    private final OperatorTypeDao operatorTypeDao;
    private final AlwinMapper mapper;

    @Inject
    public UserServiceImpl(final UserDao userDao, final OperatorDao operatorDao, final OperatorTypeDao operatorTypeDao, final AlwinMapper mapper) {
        this.mapper = mapper;
        this.operatorDao = operatorDao;
        this.operatorTypeDao = operatorTypeDao;
        this.userDao = userDao;
    }

    @Override
    public boolean doesUserExist(final long id) {
        return userDao.get(id).isPresent();
    }

    @Override
    public Page<FullUserDto> findAllUsers(int firstResult, int maxResults) {
        final List<User> users = userDao.findAllUsers(firstResult, maxResults);
        if (isEmpty(users)) {
            return emptyPage();
        }
        final long allUsersCount = userDao.findAllUsersCount();
        final List<FullUserDto> mappedUsers = mapper.mapAsList(users, FullUserDto.class);
        return new Page<>(mappedUsers, allUsersCount);
    }

    @Override
    public Page<FullUserDto> findAllUsersFilterByNameAndLogin(final int firstResult, final int maxResults, final String searchText) {
        final List<User> users = userDao.findAllUsersFilterByNameAndLogin(firstResult, maxResults, searchText);
        if (isEmpty(users)) {
            return emptyPage();
        }
        final long allUsersCount = userDao.findAllUsersFilterByNameAndLoginCount(searchText);
        final List<FullUserDto> mappedUsers = mapper.mapAsList(users, FullUserDto.class);
        return new Page<>(mappedUsers, allUsersCount);
    }

    @Override
    public Optional<EditableUserDto> findUser(long userId) {
        final Optional<User> optionalUser = userDao.get(userId);
        return optionalUser.map(user -> mapper.map(user, EditableUserDto.class));

    }

    @Override
    public void createUser(UserDto user) {
        final User userEntity = mapper.map(user, User.class);
        userEntity.setCreationDate(new Date());
        userEntity.setUpdateDate(new Date());
        userDao.create(userEntity);
    }

    @Override
    public void updateUser(EditableUserDto user) {
        final Optional<User> origin = userDao.get(user.getId());
        if (!origin.isPresent()) {
            throw new EntityNotFoundException(user.getId());
        }
        final User userEntity = origin.get();
        setUserFields(user, userEntity);
        setOperatorsFields(user.getOperators(), userEntity);
        userDao.update(userEntity);
    }

    private void setOperatorsFields(final List<EditableOperatorDto> operators, final User user) {
        if (isEmpty(operators)) {
            user.setOperators(emptyList());
            return;
        }

        if (isEmpty(user.getOperators())) {
            user.setOperators(new ArrayList<>(operators.size()));
        }

        for (final EditableOperatorDto operator : operators) {
            if (operator.getId() != null) {
                updateExistingOperator(user, operator);
            } else {
                user.getOperators().add(createNewOperator(user, operator));
            }
        }
    }

    private Operator createNewOperator(final User user, final EditableOperatorDto operator) {
        final Operator newOperator = new Operator();
        newOperator.setActive(operator.isActive());
        final String salt = DigestUtils.sha512Hex(new Date().toString() + UUID.randomUUID().toString());
        final String password = DigestUtils.sha512Hex(salt + operator.getPassword());
        newOperator.setPassword(password);
        newOperator.setSalt(salt);
        newOperator.setLogin(operator.getLogin());
        newOperator.setUser(user);
        newOperator.setPermission(mapper.map(operator.getPermission(), Permission.class));
        final Optional<OperatorType> type = operatorTypeDao.get(operator.getType().getId());
        newOperator.setType(type.orElseThrow(IllegalStateException::new));
        if (operator.getParentOperator() != null) {
            final Optional<Operator> parentOperator = operatorDao.get(operator.getParentOperator().getId());
            newOperator.setParentOperator(parentOperator.orElse(null));
        }
        return newOperator;
    }

    private void updateExistingOperator(final User user, final EditableOperatorDto operator) {
        final Optional<Operator> optionalUserOperator = user.getOperators().stream().filter(userOperator -> operator.getId().equals(userOperator.getId())).findFirst();
        final Operator userOperator = optionalUserOperator.orElseThrow(IllegalStateException::new);
        userOperator.setActive(operator.isActive());
        userOperator.setLogin(operator.getLogin());
        final Optional<OperatorType> type = operatorTypeDao.get(operator.getType().getId());
        userOperator.setType(type.orElseThrow(IllegalStateException::new));
        userOperator.setPermission(mapper.map(operator.getPermission(), Permission.class));
        if (operator.getParentOperator() != null) {
            final Optional<Operator> parentOperator = operatorDao.get(operator.getParentOperator().getId());
            userOperator.setParentOperator(parentOperator.orElseThrow(IllegalStateException::new));
        }
    }

    private void setUserFields(final EditableUserDto user, final User userEntity) {
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setUpdateDate(new Date());
    }
}
