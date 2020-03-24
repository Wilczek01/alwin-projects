package com.codersteam.alwin.integration.common;

import org.apache.http.HttpResponse;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

/**
 * @author Tomasz Sliwinski
 */
public class ResponseCodeAsserter {

    public static void assertUnauthorized(final int responseCode) {
        assertEquals(UNAUTHORIZED.getStatusCode(), responseCode);
    }

    public static void assertBadRequest(final int responseCode) {
        assertEquals(BAD_REQUEST.getStatusCode(), responseCode);
    }

    public static void assertBadRequest(final HttpResponse response) {
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertForbidden(final int responseCode) {
        assertEquals(FORBIDDEN.getStatusCode(), responseCode);
    }

    public static void assertCreated(final int responseCode) {
        assertEquals(CREATED.getStatusCode(), responseCode);
    }

    public static void assertCreated(final HttpResponse response) {
        assertEquals(CREATED.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertAccepted(final int responseCode) {
        assertEquals(ACCEPTED.getStatusCode(), responseCode);
    }

    public static void assertAccepted(final HttpResponse response) {
        assertEquals(ACCEPTED.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertNotFound(final int responseCode) {
        assertEquals(NOT_FOUND.getStatusCode(), responseCode);
    }

    public static void assertNotFound(final HttpResponse response) {
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertNoContent(final int responseCode) {
        assertEquals(NO_CONTENT.getStatusCode(), responseCode);
    }

    public static void assertNoContent(final HttpResponse response) {
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertOk(final int responseCode) {
        assertEquals(OK.getStatusCode(), responseCode);
    }

    public static void assertOk(final HttpResponse response) {
        assertEquals(OK.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertInternalServerError(final HttpResponse response) {
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatusLine().getStatusCode());
    }

    public static void assertInternalServerError(final int responseCode) {
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), responseCode);
    }
}
