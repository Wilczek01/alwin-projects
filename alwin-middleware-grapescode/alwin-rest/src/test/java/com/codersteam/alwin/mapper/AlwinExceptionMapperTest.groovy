package com.codersteam.alwin.mapper

import com.codersteam.alwin.core.api.exception.EntityNotFoundException
import com.codersteam.alwin.core.api.exception.SmsSendingException
import com.codersteam.alwin.exception.AlwinValidationException
import com.codersteam.alwin.integration.mock.UUIDGeneratorServiceMock
import com.codersteam.alwin.model.ErrorResponse
import org.jboss.resteasy.spi.DefaultOptionsMethodException
import org.slf4j.Logger
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import javax.ejb.EJBException
import javax.ws.rs.NotFoundException
import javax.ws.rs.WebApplicationException

import static com.codersteam.alwin.integration.mock.UUIDGeneratorServiceMock.TEST_UUID_STRING
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.NON_EXISTING_AIDA_COMPANY_ID
import static com.codersteam.alwin.validator.AddressValidator.ADDRESS_FETCHED_FROM_AIDA_COULD_NOT_BE_UPDATED
import static javax.ws.rs.core.Response.Status.*
import static javax.ws.rs.core.Response.ok

/**
 * @author Piotr Naroznik
 */
class AlwinExceptionMapperTest extends Specification {

    private static final String ERROR_ID_MESSAGE = "[errorId:{}]"

    @Subject
    private AlwinExceptionMapper mapper

    private Logger logger = Mock(Logger)

    def "setup"() {
        UUIDGeneratorServiceMock uuidGeneratorServiceMock = new UUIDGeneratorServiceMock()
        mapper = new AlwinExceptionMapper(uuidGeneratorServiceMock)
    }

    @Unroll
    def "should map #exception to response status code: #statusCode.statusCode (#statusCode) and #reportingRequiredMessage"() {
        when:
            def response = mapper.toResponse(exception)
        then:
            response.getStatus() == statusCode.statusCode
            if (reportingRequired != null) {
                def responseEntity = (ErrorResponse) response.getEntity()
                assert responseEntity.reportingRequired == reportingRequired
                assert responseEntity.errorId == UUID.fromString(TEST_UUID_STRING)
            }
        where:
            exception                                                 | statusCode            | reportingRequired
            new Exception()                                           | INTERNAL_SERVER_ERROR | true
            new SmsSendingException(null, null, 0, null)              | INTERNAL_SERVER_ERROR | false
            new EJBException(new WebApplicationException())           | INTERNAL_SERVER_ERROR | null
            new WebApplicationException()                             | INTERNAL_SERVER_ERROR | null
            new EJBException(new AlwinValidationException(null))      | BAD_REQUEST           | false
            new NotFoundException(new AlwinValidationException(null)) | BAD_REQUEST           | false
            new EJBException(new EntityNotFoundException(null))       | NOT_FOUND             | false
            new DefaultOptionsMethodException(null, ok().build())     | OK                    | null

            reportingRequiredMessage = reportingRequired ? "reporting required" : "reporting not required"
    }

    def "should log warn when EntityNotFoundException occurs"() {
        given:
            def exception = new EJBException(new EntityNotFoundException(NON_EXISTING_AIDA_COMPANY_ID))
        and:
            mapper.setLogger(logger)
        when:
            mapper.toResponse(exception)
        then:
            1 * logger.warn(_ as String, _ as UUID, _ as Object, _ as EntityNotFoundException) >> { List args ->
                String logMessage = args[0]
                assert logMessage.startsWith(ERROR_ID_MESSAGE)
                UUID errorId = (UUID) args[1][0]
                assert errorId.toString() == TEST_UUID_STRING
            }
    }

    def "should log info when AlwinValidationException occurs"() {
        given:
            def exception = new EJBException(new AlwinValidationException(ADDRESS_FETCHED_FROM_AIDA_COULD_NOT_BE_UPDATED))
        and:
            mapper.setLogger(logger)
        when:
            mapper.toResponse(exception)
        then:
            1 * logger.info(_ as String, _ as UUID, _ as String) >> { List args ->
                String logMessage = args[0]
                assert logMessage.startsWith(ERROR_ID_MESSAGE)
                UUID errorId = (UUID) args[1]
                assert errorId.toString() == TEST_UUID_STRING
            }
    }

    def "should log error when SmsSendingException occurs"() {
        given:
            def exception = new SmsSendingException("+48123456789", "proszę oddać kasę!", 0, null)
        and:
            mapper.setLogger(logger)
        when:
            mapper.toResponse(exception)
        then:
            1 * logger.error(_ as String, _ as UUID, _ as Integer, _ as String, _ as String, _ as SmsSendingException) >> { List args ->
                String logMessage = args[0]
                assert logMessage.startsWith(ERROR_ID_MESSAGE)
                UUID errorId = (UUID) args[1][0]
                assert errorId.toString() == TEST_UUID_STRING
            }
    }

    def "should log error when other exception occurs"() {
        given:
            def exception = new RuntimeException("dotarłeś do końca aplikacji")
        and:
            mapper.setLogger(logger)
        when:
            mapper.toResponse(exception)
        then:
            1 * logger.error(_ as String, _ as UUID, _ as String, _ as Exception) >> { List args ->
                String logMessage = args[0]
                assert logMessage.startsWith(ERROR_ID_MESSAGE)
                UUID errorId = (UUID) args[1][0]
                assert errorId.toString() == TEST_UUID_STRING
            }
    }

    @Unroll
    def "should check if #message is EjbWrappedWebApplicationException"() {
        expect:
            mapper.isEjbWrappedWebApplicationException(exception) == result
        where:
            message                                              | exception                                           | result
            'RuntimeException caused by RuntimeException'        | new RuntimeException(new RuntimeException())        | false
            'RuntimeException caused by WebApplicationException' | new RuntimeException(new WebApplicationException()) | false
            'EJBException caused by RuntimeException'            | new EJBException(new RuntimeException())            | false
            'EJBException caused by WebApplicationException'     | new EJBException(new WebApplicationException())     | true
    }
}
