package com.codersteam.alwin.core.api.service.auth;

import com.codersteam.alwin.core.api.model.user.OperatorType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.ejb.Local;
import java.security.Key;
import java.time.Duration;
import java.time.temporal.TemporalAmount;

/**
 * Serwis do obslugi tokenow JWT
 *
 * @author Tomasz Sliwinski
 */
@Local
public interface JwtService {

    String BEARER_HEADER_PREFIX = "Bearer";
    String OPERATOR_TYPE = "operatorType";
    String OPERATOR_FULL_NAME = "operatorFullName";
    String USER_ID = "userId";
    SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    Key SECRET_KEY = MacProvider.generateKey(SIGNATURE_ALGORITHM);
    TemporalAmount TOKEN_VALIDITY = Duration.ofHours(8L);

    /**
     * Utworzenie tokena
     *
     * @param subject      - dane operatora
     * @param operatorType - typ operatora
     * @param operatorId   - identyfikator operatora
     * @param firstName    - imię użytkownika
     * @param lastName     - nazwisko użytkownika
     * @return
     */
    String createToken(final String subject, final String operatorType, final Long operatorId, final String firstName, final String lastName);

    /**
     * Przetworzenie otrzymanego tokenu JWT
     *
     * @param token token
     * @return
     */
    Jws<Claims> parseToken(final String token);

    /**
     * Pobiera identyfikator zalogowanego operatora z tokenu
     *
     * @param token token
     * @return identyfikator użytkownika
     */
    Long getLoggedOperatorId(final String token);

    /**
     * Pobiera typ zalogowanego operatora z tokenu
     *
     * @param token token
     * @return typ użytkownika
     */
    OperatorType getLoggedOperatorType(final String token);

    /**
     * Pobiera imię i nazwisko zalogowanego operatora z tokenu
     *
     * @param token token
     * @return imię i nazwisko użytkownika
     */
    String getLoggedOperatorFullName(final String token);
}
