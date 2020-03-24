package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.search.criteria.ContractTerminationSearchCriteria;
import com.codersteam.alwin.common.sort.ContractTerminationSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.termination.ActivateContractTerminationDto;
import com.codersteam.alwin.core.api.model.termination.ProcessContractsTerminationDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationService;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.validator.ContractTerminationValidator;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.model.search.ContractTerminationSearchCriteriaBuilder.aContractTerminationSearchCriteria;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/contractTerminations")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/contractTerminations")
public class ContractTerminationResource {

    private JwtService jwtService;
    private ContractTerminationValidator contractTerminationValidator;
    private ContractTerminationService contractTerminationService;

    public ContractTerminationResource() {
    }

    @Inject
    public ContractTerminationResource(final JwtService jwtService,
                                       final ContractTerminationValidator contractTerminationValidator,
                                       final ContractTerminationService contractTerminationService) {
        this.jwtService = jwtService;
        this.contractTerminationValidator = contractTerminationValidator;
        this.contractTerminationService = contractTerminationService;
    }

    @GET
    @Path("/open")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER})
    @ApiOperation("Zwraca wypowiedzenia umów do obsługi posortowane według daty wypowiedzenia malejąco (najpierw najnowsze) i id klienta rosnąco")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<TerminationDto> findContractTerminationsToOperate() {
        return contractTerminationService.findTerminationsToProcess();
    }

    @GET
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER})
    @ApiOperation("Zwraca wypowiedzenia umów do obsługi posortowane według daty wypowiedzenia malejąco (najpierw najnowsze) i id klienta rosnąco")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<TerminationDto> findTerminations(@ApiParam(name = "initialInvoiceNo", value = "Numer faktury inicjującej wystawienie")
                                                 @QueryParam("initialInvoiceNo") String initialInvoiceNo,
                                                 @ApiParam(name = "contractNo", value = "Numer umowy na wezwaniu do zapłaty")
                                                 @QueryParam("contractNo") String contractNo,
                                                 @ApiParam(name = "companyName", value = "Nazwa firmy")
                                                 @QueryParam("companyName") String companyName,
                                                 @ApiParam(name = "extCompanyId", value = "Numer klienta")
                                                 @QueryParam("extCompanyId") Long extCompanyId,
                                                 @ApiParam(name = "startTerminationDate", value = "Początek okresu wypowiedzenia")
                                                 @QueryParam("startTerminationDate") DateParam startTerminationDate,
                                                 @ApiParam(name = "endTerminationDate", value = "Koniec okresu wypowiedzenia")
                                                 @QueryParam("endTerminationDate") DateParam endTerminationDate,
                                                 @ApiParam(name = "type", value = "Typ wypowiedzenia")
                                                 @QueryParam("type") ContractTerminationType type,
                                                 @ApiParam(name = "state", value = "Stan wypowiedzenia")
                                                 @QueryParam("state") ContractTerminationState state,
                                                 @ApiParam(name = "remark", value = "Komentarz")
                                                 @QueryParam("remark") String remark,
                                                 @ApiParam(name = "generatedBy", value = "Imię i nazwisko operatora generującego dokument")
                                                 @QueryParam("generatedBy") String generatedBy,
                                                 @ApiParam(name = "resumptionCostMin", value = "Minimalna wysokość opłaty za wznowienie")
                                                 @QueryParam("resumptionCostMin") BigDecimal resumptionCostMin,
                                                 @ApiParam(name = "resumptionCostMax", value = "Maksymalna wysokość opłaty za wznowienie")
                                                 @QueryParam("resumptionCostMax") BigDecimal resumptionCostMax,
                                                 @ApiParam(name = "startDueDateInDemandForPayment", value = "Początek okresu wskazania do zapłaty")
                                                 @QueryParam("startDueDateInDemandForPayment") DateParam startDueDateInDemandForPayment,
                                                 @ApiParam(name = "endDueDateInDemandForPayment", value = "Koniec okresu wskazania do zapłaty")
                                                 @QueryParam("endDueDateInDemandForPayment") DateParam endDueDateInDemandForPayment,
                                                 @ApiParam(name = "amountInDemandForPaymentPLNMin", value = "Minimalna kwota wskazana do spłaty na wezwaniu PLN")
                                                 @QueryParam("amountInDemandForPaymentPLNMin") BigDecimal amountInDemandForPaymentPLNMin,
                                                 @ApiParam(name = "amountInDemandForPaymentPLNMax", value = "Maksymalna kwota wskazana do spłaty na wezwaniu PLN")
                                                 @QueryParam("amountInDemandForPaymentPLNMax") BigDecimal amountInDemandForPaymentPLNMax,
                                                 @ApiParam(name = "amountInDemandForPaymentEURMin", value = "Minimalna kwota wskazana do spłaty na wezwaniu EUR")
                                                 @QueryParam("amountInDemandForPaymentEURMin") BigDecimal amountInDemandForPaymentEURMin,
                                                 @ApiParam(name = "amountInDemandForPaymentEURMax", value = "Maksymalna kwota wskazana do spłaty na wezwaniu EUR")
                                                 @QueryParam("amountInDemandForPaymentEURMax") BigDecimal amountInDemandForPaymentEURMax,
                                                 @ApiParam(name = "totalPaymentsSinceDemandForPaymentPLNMin", value = "Minimalna suma wpłat dokonanych od wezwania PLN")
                                                 @QueryParam("totalPaymentsSinceDemandForPaymentPLNMin") BigDecimal totalPaymentsSinceDemandForPaymentPLNMin,
                                                 @ApiParam(name = "totalPaymentsSinceDemandForPaymentPLNMax", value = "Maksymalna suma wpłat dokonanych od wezwania PLN")
                                                 @QueryParam("totalPaymentsSinceDemandForPaymentPLNMax") BigDecimal totalPaymentsSinceDemandForPaymentPLNMax,
                                                 @ApiParam(name = "totalPaymentsSinceDemandForPaymentEURMin", value = "Minimalna suma wpłat dokonanych od wezwania EUR")
                                                 @QueryParam("totalPaymentsSinceDemandForPaymentEURMin") BigDecimal totalPaymentsSinceDemandForPaymentEURMin,
                                                 @ApiParam(name = "totalPaymentsSinceDemandForPaymentEURMax", value = "Maksymalna suma wpłat dokonanych od wezwania EUR")
                                                 @QueryParam("totalPaymentsSinceDemandForPaymentEURMax") BigDecimal totalPaymentsSinceDemandForPaymentEURMax,
                                                 @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                 @QueryParam("firstResult") final int firstResult,
                                                 @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                 @QueryParam("maxResults") final int maxResults,
                                                 @QueryParam("sortField") ContractTerminationSortField sortField,
                                                 @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder) {
        final ContractTerminationSearchCriteria searchCriteria = aContractTerminationSearchCriteria()
                .withContractNo(contractNo)
                .withCompanyName(companyName)
                .withExtCompanyId(extCompanyId)
                .withStartTerminationDate(startTerminationDate)
                .withEndTerminationDate(endTerminationDate)
                .withType(type)
                .withRemark(remark)
                .withInitialInvoiceNo(initialInvoiceNo)
                .withGeneratedBy(generatedBy)
                .withResumptionCostMin(resumptionCostMin)
                .withResumptionCostMax(resumptionCostMax)
                .withStartDueDateInDemandForPayment(startDueDateInDemandForPayment)
                .withEndDueDateInDemandForPayment(endDueDateInDemandForPayment)
                .withAmountInDemandForPaymentPLNMin(amountInDemandForPaymentPLNMin)
                .withAmountInDemandForPaymentPLNMax(amountInDemandForPaymentPLNMax)
                .withAmountInDemandForPaymentEURMin(amountInDemandForPaymentEURMin)
                .withAmountInDemandForPaymentEURMax(amountInDemandForPaymentEURMax)
                .withTotalPaymentsSinceDemandForPaymentPLNMin(totalPaymentsSinceDemandForPaymentPLNMin)
                .withTotalPaymentsSinceDemandForPaymentPLNMax(totalPaymentsSinceDemandForPaymentPLNMax)
                .withTotalPaymentsSinceDemandForPaymentEURMin(totalPaymentsSinceDemandForPaymentEURMin)
                .withTotalPaymentsSinceDemandForPaymentEURMax(totalPaymentsSinceDemandForPaymentEURMax)
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withState(state)
                .build();

        Map<ContractTerminationSortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

        return contractTerminationService.findTerminations(searchCriteria, sortCriteria);
    }

    @POST
    @Path("process")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER})
    @ApiOperation("Przetwarza przesłane sugestie wypowiedzeń umów (wysyła, odracza lub odrzuca). Uruchamia asynchronicznie wysyłanie sugestii wypowiedzeń.")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void processTerminations(@HeaderParam(AUTHORIZATION) String token,
                                        @ApiParam(required = true, value = "Sugestie wypowiedzeń umów do przetwarzania") ProcessContractsTerminationDto terminations) {
        final String loggedOperatorFullName = jwtService.getLoggedOperatorFullName(token);
        contractTerminationValidator.validate(terminations);
        contractTerminationService.rejectContractTerminations(terminations.getTerminationsToReject());
        contractTerminationService.postponeContractTerminations(terminations.getTerminationsToPostpone());
        contractTerminationService.sendContractTerminations(terminations.getTerminationsToSend(), loggedOperatorFullName);
    }

    @POST
    @Path("/{contractTerminationId}/activate")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER})
    @ApiOperation("Oznaczanie umowy jako aktywowaną")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void activateContract(@ApiParam(name = "contractTerminationId", value = "Identyfikator wypowiedzenia umowy") @PathParam("contractTerminationId") final long contractTerminationId,
                                     @ApiParam(required = true, value = "Parametry aktywacji umowy dla wypowiedzenia") ActivateContractTerminationDto activateContractTerminationDto) {
        contractTerminationValidator.validate(contractTerminationId, activateContractTerminationDto);
        contractTerminationService.activateContractTermination(contractTerminationId, activateContractTerminationDto);
    }
}
