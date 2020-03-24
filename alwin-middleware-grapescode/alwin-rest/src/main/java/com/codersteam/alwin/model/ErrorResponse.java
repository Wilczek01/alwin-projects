package com.codersteam.alwin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

/**
 * Komunikat odpowiedzi w przypadku wystąpenia błędu w aplikacji
 *
 * @author Tomasz Sliwinski
 */
@ApiModel("Komunikat odpowiedzi w przypadku wystąpenia błędu w aplikacji")
public class ErrorResponse {

    @ApiModelProperty("Unikalny identyfikator błędu")
    private final UUID errorId;

    @ApiModelProperty("Czy błąd wymaga zgłoszenia do supportu")
    private final boolean reportingRequired;

    @ApiModelProperty("Komunikat błędu")
    private final String message;

    @ApiModelProperty("Dodatkowe informacje do analizy błędu - do użytku wewnętrznego (nie powinny być prezentowane użytkownikowi)")
    private final String internalDetails;

    public ErrorResponse(final UUID errorId, final boolean reportingRequired, final String message, final String internalDetails) {
        this.errorId = errorId;
        this.reportingRequired = reportingRequired;
        this.message = message;
        this.internalDetails = internalDetails;
    }

    public UUID getErrorId() {
        return errorId;
    }

    public boolean isReportingRequired() {
        return reportingRequired;
    }

    public String getMessage() {
        return message;
    }

    public String getInternalDetails() {
        return internalDetails;
    }
}
