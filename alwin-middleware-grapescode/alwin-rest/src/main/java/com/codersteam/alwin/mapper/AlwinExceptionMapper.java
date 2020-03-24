package com.codersteam.alwin.mapper;

import com.codersteam.alwin.core.api.exception.EntityNotFoundException;
import com.codersteam.alwin.core.api.exception.SmsSendingException;
import com.codersteam.alwin.core.api.service.UUIDGeneratorService;
import com.codersteam.alwin.exception.AlwinValidationException;
import com.codersteam.alwin.model.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.spi.DefaultOptionsMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Provider
public class AlwinExceptionMapper implements ExceptionMapper<Exception> {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UUIDGeneratorService uuidGeneratorService;

    // for deployment
    @SuppressWarnings("unused")
    public AlwinExceptionMapper() {
    }

    @Inject
    public AlwinExceptionMapper(final UUIDGeneratorService uuidGeneratorService) {
        this.uuidGeneratorService = uuidGeneratorService;
    }

    @Override
    public Response toResponse(final Exception e) {
        final UUID errorId = uuidGeneratorService.generateRandomUUID();

        // runtime exceptions
        if (isEntityNotFoundException(e)) {
            return mapNotFoundException((EntityNotFoundException) e.getCause(), errorId);
        }

        if (isAlwinValidationException(e)) {
            return mapAlwinValidationException((AlwinValidationException) e.getCause(), errorId);
        }

        if (isDefaultOptionsMethodException(e)) {
            return mapDefaultOptionsMethodException((DefaultOptionsMethodException) e);
        }

        if (isEjbWrappedWebApplicationException(e)) {
            return mapWebApplicationException((WebApplicationException) e.getCause(), errorId);
        }

        if (isWebApplicationException(e)) {
            return mapWebApplicationException((WebApplicationException) e, errorId);
        }

        //checked exceptions
        if (isSmsSendingException(e)) {
            return mapSmsSendingException((SmsSendingException) e, errorId);
        }

        return mapException(e, errorId);
    }

    private boolean isSmsSendingException(final Exception e) {
        return e instanceof SmsSendingException;
    }

    /**
     * Sprawdza, czy wyjątek jest typu EJBException
     * Każdy runtime exception jest pakowany w EJBException.
     * Więc żeby sprawdzić jaki jest typ wyjątku runtime, należy w kolejnym kroku porównać przyczynę wyjątku.
     *
     * @param e - wyjątek do sprawdznia
     * @return true - jeżeli wyjątek jest typu EJBException, false w przeciwnym przypadku
     */
    private boolean isEJBException(final Exception e) {
        return e instanceof EJBException;
    }

    private boolean isNotFoundException(final Exception e) {
        return e instanceof NotFoundException;
    }

    protected boolean isEjbWrappedWebApplicationException(final Exception e) {
        return (isEJBException(e) && e.getCause() instanceof WebApplicationException);
    }

    private boolean isWebApplicationException(final Exception e) {
        return e instanceof WebApplicationException;
    }

    private boolean isAlwinValidationException(final Exception e) {
        return (isEJBException(e) && e.getCause() instanceof AlwinValidationException) ||
                (isNotFoundException(e) && e.getCause() instanceof AlwinValidationException);
    }

    private boolean isEntityNotFoundException(final Exception e) {
        return isEJBException(e) && e.getCause() instanceof EntityNotFoundException;
    }

    private boolean isDefaultOptionsMethodException(final Exception e) {
        return e instanceof DefaultOptionsMethodException;
    }

    private Response mapNotFoundException(final EntityNotFoundException e, final UUID errorId) {
        logger.warn("[errorId:{}] Cannot find entity with id {}", errorId, e.getEntityId(), e);
        String message = e.getMessage();
        if (StringUtils.isBlank(message)) {
            message = format("Nie znaleziono obiektu o idnetyfikatorze %s", e.getEntityId());
        }
        return prepareErrorResponse(Response.status(NOT_FOUND), errorId, message, null, false);
    }

    private Response mapSmsSendingException(final SmsSendingException e, final UUID errorId) {
        logger.error("[errorId:{}] Cannot send sms with id {} and message {} to phone number {}", errorId, e.getId(), e.getSmsMessage(), e.getPhoneNumber(), e);
        final String message = format("Wysłanie SMS na numer %s zakończone niepowodzeniem", e.getPhoneNumber());
        return prepareErrorResponse(Response.serverError(), errorId, message, null, false);
    }

    private Response mapDefaultOptionsMethodException(final DefaultOptionsMethodException e) {
        return e.getResponse();
    }

    private Response mapWebApplicationException(final WebApplicationException e, final UUID errorId) {
        logger.info("[errorId:{}] {}", errorId, e.getMessage());
        return e.getResponse();
    }

    private Response mapAlwinValidationException(final AlwinValidationException e, final UUID errorId) {
        logger.info("[errorId:{}] {}", errorId, e.getMessage());
        return prepareErrorResponse(Response.status(BAD_REQUEST), errorId, e.getMessage(), null, false);
    }

    private Response mapException(final Exception e, final UUID errorId) {
        logger.error("[errorId:{}] {}", errorId, e.getMessage(), e);
        return prepareErrorResponse(Response.serverError(), errorId, e.getMessage(), getStackTrace(e), true);
    }

    private Response prepareErrorResponse(final Response.ResponseBuilder responseBuilder, final UUID errorId, final String message, final String details,
                                          final boolean reportingRequired) {
        final ErrorResponse errorResponse = new ErrorResponse(errorId, reportingRequired, message, details);
        return responseBuilder.entity(errorResponse).build();
    }

    // for testing purposes
    protected static void setLogger(final Logger logger) {
        AlwinExceptionMapper.logger = logger;
    }
}
