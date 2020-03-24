package com.codersteam.alwin.core.service.impl.auth;

import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.service.auth.AuthenticationService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.OperatorDao;
import com.codersteam.alwin.jpa.operator.Operator;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Service responsible for user authentication
 *
 * @author Michal Horowic
 */
@SuppressWarnings({"EjbClassBasicInspection"})
@Stateless
public class AuthenticationServiceImpl implements AuthenticationService {

    private final OperatorDao operatorDao;
    private final ConfigurableMapper mapper;

    @Inject
    public AuthenticationServiceImpl(final OperatorDao operatorDao, final AlwinMapper mapper) {
        this.operatorDao = operatorDao;
        this.mapper = mapper;
    }

    @Override
    public Optional<OperatorDto> authenticateUser(final String login, final String password) {
        final Optional<Operator> existingOperator = operatorDao.findActiveOperatorByLogin(login);
        if (!existingOperator.isPresent()) {
            return Optional.empty();
        }

        final Operator operator = existingOperator.get();
        final String userSalt = operator.getSalt();
        final String encryptedPassword = DigestUtils.sha512Hex(userSalt + password);

        final boolean isPasswordMatch = operator.getPassword().equalsIgnoreCase(encryptedPassword);

        if (!isPasswordMatch) {
            return Optional.empty();
        }
        final OperatorDto operatorDto = mapper.map(operator, OperatorDto.class);
        return Optional.of(operatorDto);
    }
}
