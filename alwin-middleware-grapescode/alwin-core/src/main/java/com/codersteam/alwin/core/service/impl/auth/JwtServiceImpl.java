package com.codersteam.alwin.core.service.impl.auth;

import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.ejb.Stateless;
import java.time.Instant;
import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
@Stateless
public class JwtServiceImpl implements JwtService {

    @Override
    public String createToken(final String subject, final String operatorType, final Long operatorId, final String firstName, final String lastName) {
        final Instant now = Instant.now();
        final Date expiryDate = Date.from(now.plus(TOKEN_VALIDITY));
        return Jwts.builder()
                .setSubject(subject)
                .claim(OPERATOR_TYPE, operatorType)
                .claim(USER_ID, operatorId.toString())
                .claim(OPERATOR_FULL_NAME, String.format("%s %s", firstName, lastName))
                .setExpiration(expiryDate)
                .setIssuedAt(Date.from(now))
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY)
                .compact();
    }

    @Override
    public Jws<Claims> parseToken(final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }

    @Override
    public Long getLoggedOperatorId(final String token) {
        final String tokenValue = extractToken(token);
        final Claims body = parseToken(tokenValue).getBody();
        return Long.valueOf(body.get(JwtService.USER_ID, String.class));
    }

    @Override
    public OperatorType getLoggedOperatorType(final String token) {
        final String tokenValue = extractToken(token);
        final Claims body = parseToken(tokenValue).getBody();
        return OperatorType.valueOf(body.get(JwtService.OPERATOR_TYPE, String.class));
    }

    @Override
    public String getLoggedOperatorFullName(final String token) {
        final String tokenValue = extractToken(token);
        final Claims body = parseToken(tokenValue).getBody();
        return body.get(JwtService.OPERATOR_FULL_NAME, String.class);
    }

    private String extractToken(final String token) {
        if (token.startsWith(BEARER_HEADER_PREFIX)) {
            return token.substring(BEARER_HEADER_PREFIX.length()).trim();
        }
        return token;
    }
}
