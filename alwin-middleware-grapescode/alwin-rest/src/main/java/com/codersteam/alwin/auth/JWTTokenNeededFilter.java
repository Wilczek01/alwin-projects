package com.codersteam.alwin.auth;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.RequestOperator;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static com.codersteam.alwin.core.api.service.auth.JwtService.BEARER_HEADER_PREFIX;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Filtr do zabezpieczenia endpointów
 *
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("CdiInjectionPointsInspection")
@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Inject
    private JwtService jwtService;

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private RequestOperator requestOperator;

    /**
     * Filtrowanie requestu
     *
     * @param requestContext kontekst requestu
     */
    @Override
    public void filter(final ContainerRequestContext requestContext) {
        final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (isBlank(authorizationHeader) || !authorizationHeader.startsWith(BEARER_HEADER_PREFIX)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        final Jws<Claims> claimsJws;
        try {
            final String token = authorizationHeader.substring(BEARER_HEADER_PREFIX.length()).trim();
            claimsJws = jwtService.parseToken(token);
        } catch (final Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        final Class<?> resourceClass = resourceInfo.getResourceClass();
        final Collection<OperatorType> classOperatorTypes = extractOperatorTypes(resourceClass);
        final Method resourceMethod = resourceInfo.getResourceMethod();
        final Collection<OperatorType> methodOperatorTypes = extractOperatorTypes(resourceMethod);

        final OperatorType claimedOperatorType = getOperatorType(claimsJws);

        if (claimedOperatorType == null || userHasNoAuthority(claimedOperatorType, classOperatorTypes, methodOperatorTypes)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
        bindOperatorIdWithRequest(authorizationHeader);
    }

    private void bindOperatorIdWithRequest(final String authorizationHeader) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(authorizationHeader);
        requestOperator.setOperatorId(loggedOperatorId);
    }

    private boolean userHasNoAuthority(final OperatorType claimedOperatorType, final Collection<OperatorType> classOperatorTypes,
                                       final Collection<OperatorType> methodOperatorTypes) {
        return !(methodOperatorTypes.contains(claimedOperatorType) || classOperatorTypes.contains(claimedOperatorType));
    }

    private OperatorType getOperatorType(final Jws<Claims> claimsJws) {
        final Claims body = claimsJws.getBody();
        final String tokenOperatorType = (String) body.get(JwtService.OPERATOR_TYPE);
        return OperatorType.forName(tokenOperatorType);
    }

    /**
     * Pobranie typów operatorów z adnotowanego elementu
     *
     * @param annotatedElement - annotowanyElement
     * @return lista typów operatorów
     */
    protected Collection<OperatorType> extractOperatorTypes(final AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return emptySet();
        }
        final Secured secured = annotatedElement.getAnnotation(Secured.class);
        if (secured == null) {
            return emptySet();
        }

        if (secured.all()) {
            return new HashSet<>(Arrays.asList(OperatorType.values()));
        }
        return new HashSet<>(Arrays.asList(secured.value()));
    }

    public void setJwtService(final JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setResourceInfo(final ResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public void setRequestOperator(final RequestOperator requestOperator) {
        this.requestOperator = requestOperator;
    }
}