package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.auth.model.BalanceResponse;
import com.codersteam.alwin.auth.model.BalanceStartAndAdditionalResponse;
import com.codersteam.alwin.core.api.model.balance.BalanceStartAndAdditionalDto;
import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.core.api.service.debt.BalanceCalculatorService;
import com.codersteam.alwin.core.api.service.issue.IssueBalanceUpdaterService;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.codersteam.alwin.core.api.model.currency.Currency.EUR;
import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;
import static com.codersteam.alwin.core.api.model.user.OperatorType.DIRECT_DEBT_COLLECTION_MANAGER;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/balances")
@Api("/balances")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class BalanceResource {

    private BalanceCalculatorService balanceCalculatorService;
    private IssueBalanceUpdaterService issueBalanceUpdaterService;

    public BalanceResource() {
    }

    @Inject
    public BalanceResource(final BalanceCalculatorService balanceCalculatorService, final IssueBalanceUpdaterService issueBalanceUpdaterService) {
        this.balanceCalculatorService = balanceCalculatorService;
        this.issueBalanceUpdaterService = issueBalanceUpdaterService;
    }

    @GET
    @Path("company/{extCompanyId}")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobranie aktualnego salda dla firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public BalanceResponse getCompanyBalance(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy w systemie AIDA")
                                      @PathParam("extCompanyId") Long extCompanyId) {
        return new BalanceResponse(balanceCalculatorService.currentBalance(extCompanyId).get(PLN).getBalance(),
                balanceCalculatorService.currentBalance(extCompanyId).get(EUR).getBalance());
    }

    @PATCH
    @Path("issue/{issueId}")
    @Secured(all = true)
    @ApiOperation("Uaktualnienie slad dokumentów zlecenia oraz salda zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateIssueBalances(@ApiParam(name = "issueId", value = "Identyfikator zlecenia")
                                        @PathParam("issueId") Long issueId) {
        issueBalanceUpdaterService.getIssueWithUpdatedBalance(issueId);
        return Response.accepted().build();
    }

    @GET
    @Path("issue/{issueId}/overall")
    @Secured(all = true)
    @ApiOperation("Pobranie całościowego salda klienta zlecenia (wymagane i niewymagane)")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public BalanceStartAndAdditionalResponse getBalanceStartAndAdditional(@ApiParam(name = "issueId", value = "Identyfikator zlecenia")
                                                 @PathParam("issueId") Long issueId) {
        final Map<Currency, BalanceStartAndAdditionalDto> currencyBalanceStartAndAdditional =
                balanceCalculatorService.calculateBalanceStartAndAdditional(issueId);
        return new BalanceStartAndAdditionalResponse(currencyBalanceStartAndAdditional.get(PLN), currencyBalanceStartAndAdditional.get(EUR));
    }
}
