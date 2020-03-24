package com.codersteam.alwin.auth

import com.codersteam.alwin.auth.annotation.Secured
import com.codersteam.alwin.common.RequestOperator
import com.codersteam.alwin.core.api.model.user.OperatorType
import com.codersteam.alwin.core.api.service.auth.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.jboss.resteasy.specimpl.BuiltResponse
import spock.lang.Specification

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import java.lang.reflect.AnnotatedElement

import static com.codersteam.alwin.core.api.service.auth.JwtService.BEARER_HEADER_PREFIX
import static com.codersteam.alwin.testdata.OperatorTestData.OPERATOR_ID_1

/**
 * @author Tomasz Sliwinski
 */
class JWTTokenNeededFilterTest extends Specification {

    private static final String INVALID_TOKEN = "invalid token"
    private static final String VALID_TOKEN = "valid token"

    private JWTTokenNeededFilter filter

    private JwtService jwtService
    private ResourceInfo resourceInfo
    private RequestOperator requestOperator = new RequestOperator()

    def setup() {
        jwtService = Mock(JwtService)
        resourceInfo = Mock(ResourceInfo)

        filter = Spy(JWTTokenNeededFilter)
        filter.jwtService = jwtService
        filter.resourceInfo = resourceInfo
        filter.requestOperator = requestOperator
    }

    def "should filter unauthorized user without token"() {
        given:
            def requestContext = Mock(ContainerRequestContext)
        when:
            filter.filter(requestContext)
        then:
            1 * requestContext.abortWith(_ as BuiltResponse) >> { List arguments ->
                def response = arguments[0] as BuiltResponse
                assert response.status == Response.Status.UNAUTHORIZED.statusCode
            }
    }

    def "should filter unauthorized user with invalid token"() {
        given:
            def requestContext = Mock(ContainerRequestContext)
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) >> BEARER_HEADER_PREFIX + " " + INVALID_TOKEN
        and:
            jwtService.parseToken(INVALID_TOKEN) >> { throw new IllegalArgumentException() }
        when:
            filter.filter(requestContext)
        then:
            1 * requestContext.abortWith(_ as BuiltResponse) >> { List arguments ->
                def response = arguments[0] as BuiltResponse
                assert response.status == Response.Status.UNAUTHORIZED.statusCode
            }
    }

    def "should filter forbidden resource"() {
        given:
            def requestContext = Mock(ContainerRequestContext)
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) >> BEARER_HEADER_PREFIX + " " + VALID_TOKEN
        and:
            def jwsClaim = Mock(Jws)
            def claims = Mock(Claims)
            claims.get(JwtService.OPERATOR_TYPE) >> "ANALYST"
            jwsClaim.getBody() >> claims
            jwtService.parseToken(VALID_TOKEN) >> jwsClaim
        and:
            filter.extractOperatorTypes(_) >> [OperatorType.ADMIN]
        when:
            filter.filter(requestContext)
        then:
            1 * requestContext.abortWith(_ as BuiltResponse) >> { List arguments ->
                def response = arguments[0] as BuiltResponse
                assert response.status == Response.Status.FORBIDDEN.statusCode
            }
    }

    def "should filter allowed resource"() {
        given:
            def requestContext = Mock(ContainerRequestContext)
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) >> BEARER_HEADER_PREFIX + " " + VALID_TOKEN
        and:
            def jwsClaim = Mock(Jws)
            def claims = Mock(Claims)
            claims.get(JwtService.OPERATOR_TYPE) >> "ANALYST"
            jwsClaim.getBody() >> claims
            jwtService.parseToken(VALID_TOKEN) >> jwsClaim
        and:
            filter.extractOperatorTypes(_) >> [OperatorType.ANALYST]
        when:
            filter.filter(requestContext)
        then:
            0 * requestContext.abortWith(_ as BuiltResponse)
    }

    def "should extract roles from annotated element"() {
        given:
            def secured = Mock(Secured)
            secured.value() >> ["ADMIN", "ANALYST"]
        and:
            def annotatedElement = Mock(AnnotatedElement)
            annotatedElement.getAnnotation(Secured) >> secured
        when:
            def roles = filter.extractOperatorTypes(annotatedElement)
        then:
            roles
            roles.size() == 2
            roles.contains(OperatorType.ADMIN)
            roles.contains(OperatorType.ANALYST)
    }

    def "should bind operator id with request after successful filter"() {
        given:
            def requestContext = Mock(ContainerRequestContext)
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) >> BEARER_HEADER_PREFIX + " " + VALID_TOKEN
        and:
            def jwsClaim = Mock(Jws)
            def claims = Mock(Claims)
            claims.get(JwtService.OPERATOR_TYPE) >> "ANALYST"
            jwsClaim.getBody() >> claims
            jwtService.parseToken(VALID_TOKEN) >> jwsClaim
        and:
            filter.extractOperatorTypes(_) >> [OperatorType.ANALYST]
        and:
            jwtService.getLoggedOperatorId(BEARER_HEADER_PREFIX + " " + VALID_TOKEN) >> OPERATOR_ID_1
        when:
            filter.filter(requestContext)
        then:
            requestOperator.operatorId == OPERATOR_ID_1
    }
}
