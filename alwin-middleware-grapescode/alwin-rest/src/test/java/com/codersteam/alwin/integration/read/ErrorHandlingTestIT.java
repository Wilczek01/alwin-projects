package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.model.ErrorResponse;
import org.junit.Test;

import java.io.IOException;

import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertInternalServerError;
import static com.codersteam.alwin.integration.common.ResponseCodeAsserter.assertNotFound;
import static com.codersteam.alwin.integration.mock.UUIDGeneratorServiceMock.TEST_UUID_STRING;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.EXCEPTION_CAUSING_AIDA_COMPANY_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tomasz Sliwinski
 */
public class ErrorHandlingTestIT extends ReadTestBase {

    @Test
    public void shouldMapRuntimeExceptionToErrorResponse() throws IOException {
        // given
        final String loginToken = loggedInPhoneDebtCollectorManager();

        // when
        final int responseCode = checkHttpGetCode("aidaCompanies/" + EXCEPTION_CAUSING_AIDA_COMPANY_ID, loginToken);

        // then
        assertInternalServerError(responseCode);

        // when
        final ErrorResponse errorResponse = checkHttpGet("aidaCompanies/" + EXCEPTION_CAUSING_AIDA_COMPANY_ID, loginToken, ErrorResponse.class);
        assertServerErrorResponse(errorResponse);
    }

    @Test
    public void shouldReturnNotFoundForNotExistingPage() throws IOException {
        // when
        final int responseCode = checkHttpGetCode("not_existing_page");

        // then
        assertNotFound(responseCode);
    }

    private void assertServerErrorResponse(final ErrorResponse errorResponse) {
        assertThat(errorResponse.getErrorId().toString()).isEqualTo(TEST_UUID_STRING);
        assertThat(errorResponse.isReportingRequired()).isTrue();
        assertThat(errorResponse.getMessage()).isEqualTo("Exception thrown when finding company -100");
        assertThat(errorResponse.getInternalDetails()).startsWith("java.lang.RuntimeException: Exception thrown when finding company -100" + System.lineSeparator()+
                "\tat com.codersteam.alwin.integration.mock.CompanyServiceMock.findCompanyByCompanyId(CompanyServiceMock.java:");
    }
}
