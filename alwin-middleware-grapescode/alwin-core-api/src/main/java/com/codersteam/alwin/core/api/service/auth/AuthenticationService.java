package com.codersteam.alwin.core.api.service.auth;

import com.codersteam.alwin.core.api.model.operator.OperatorDto;

import javax.ejb.Local;
import java.util.Optional;

/**
 * Serwis odpowiedzialny za uwierzytelnienie użytkownika
 *
 * @author Michal Horowic
 */
@Local
public interface AuthenticationService {

    Optional<OperatorDto> authenticateUser(String login, String password);

}
