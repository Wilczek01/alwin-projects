package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.common.search.criteria.DemandForPaymentSearchCriteria;
import com.codersteam.alwin.common.sort.DemandForPaymentSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.demand.ProcessDemandsForPaymentDto;
import com.codersteam.alwin.core.api.model.demand_for_payment.CreateDemandForPaymentRequestDto;
import com.codersteam.alwin.core.api.model.termination.DemandForPaymentStatusChangeDto;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.notice.DemandForPaymentService;
import com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResult;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.util.CSVUtils;
import com.codersteam.alwin.validator.DemandForPaymentValidator;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.codersteam.alwin.model.search.DemandForPaymentSearchCriteriaBuilder.aDemandForPaymentSearchCriteria;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/demands")
@Api("/demands")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class DemandForPaymentResource {

    public static final int CSV_DATA_ROWS_NUMBER_PART = 1000;
    private DemandForPaymentService demandForPaymentService;
    private DemandForPaymentValidator demandForPaymentValidator;
    private JwtService jwtService;

    public DemandForPaymentResource() {
    }

    @Inject
    public DemandForPaymentResource(final DemandForPaymentService demandForPaymentService, final DemandForPaymentValidator demandForPaymentValidator,
                                    final JwtService jwtService) {
        this.demandForPaymentService = demandForPaymentService;
        this.demandForPaymentValidator = demandForPaymentValidator;
        this.jwtService = jwtService;
    }

    @GET
    @Secured(all = true)
    @ApiOperation("Pobranie stronicowanej listy wezwań do zapłaty")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<DemandForPaymentDto> findAllDemandsForPayment(@HeaderParam(AUTHORIZATION) String token,
                                                              @ApiParam(name = "type", value = "Typ wezwania do zapłaty")
                                                              @QueryParam("type") List<String> type,
                                                              @ApiParam(name = "state", value = "Stan wezwania do zapłaty")
                                                              @QueryParam("state") List<String> state,
                                                              @ApiParam(name = "contractNo", value = "Numer umowy na wezwaniu do zapłaty")
                                                              @QueryParam("contractNo") String contractNo,
                                                              @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                              @QueryParam("firstResult") int firstResult,
                                                              @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                              @QueryParam("maxResults") int maxResults,
                                                              @ApiParam(name = "chargeInvoiceNo", value = "Numer faktury za opłatę")
                                                              @QueryParam("chargeInvoiceNo") String chargeInvoiceNo,
                                                              @ApiParam(name = "initialInvoiceNo", value = "Numer faktury inicjującej wystawienie")
                                                              @QueryParam("initialInvoiceNo") String initialInvoiceNo,
                                                              @ApiParam(name = "companyName", value = "Nazwa firmy")
                                                              @QueryParam("companyName") String companyName,
                                                              @ApiParam(name = "startIssueDate", value = "Początek okresu wystawienia")
                                                              @QueryParam("startIssueDate") DateParam startIssueDate,
                                                              @ApiParam(name = "endIssueDate", value = "Koniec okresu wystawienia")
                                                              @QueryParam("endIssueDate") DateParam endIssueDate,
                                                              @ApiParam(name = "startDueDate", value = "Początek okresu zapłaty")
                                                              @QueryParam("startDueDate") DateParam startDueDate,
                                                              @ApiParam(name = "endDueDate", value = "Koniec okresu zapłaty")
                                                              @QueryParam("endDueDate") DateParam endDueDate,
                                                              @ApiParam(name = "extCompanyId", value = "Identyfikator klienta (AIDA)")
                                                              @QueryParam("extCompanyId") Long extCompanyId,
                                                              @ApiParam(name = "segment", value = "Segment")
                                                              @QueryParam("segment") Segment segment,
                                                              @ApiParam(name = "createdManually", value = "Flaga pozwalająca na odfiltrowanie wezwań tworzonych ręcznie/automatycznie, true=zwraca tylko utworzone ręcznie, false=zwraca tylko utworzone automatycznie, brak wartości zwraca wszystko")
                                                              @QueryParam("createdManually") Boolean createdManually,
                                                              @ApiParam(name = "aborted", value = "Flaga pozwalająca na odfiltrowanie wezwań zastąpionych w procesie (np. poprzez ręcznie wystawione wezwanie tego samego stopnia)")
                                                              @QueryParam("aborted") Boolean aborted,
                                                              @QueryParam("sortField") DemandForPaymentSortField sortField,
                                                              @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        demandForPaymentValidator.validateOperatorsPermission(loggedOperatorId);
        final List<DemandForPaymentState> mappedStates = demandForPaymentValidator.validateState(state);
        final List<DemandForPaymentTypeKey> typeKeys = demandForPaymentValidator.validateType(type);
        final DemandForPaymentSearchCriteria criteria = aDemandForPaymentSearchCriteria()
                .withExtCompanyId(extCompanyId)
                .withChargeInvoiceNo(chargeInvoiceNo)
                .withInitialInvoiceNo(initialInvoiceNo)
                .withCompanyName(companyName)
                .withStartIssueDate(startIssueDate)
                .withEndIssueDate(endIssueDate)
                .withStartDueDate(startDueDate)
                .withEndDueDate(endDueDate)
                .withSegment(segment)
                .withContractNo(contractNo)
                .withState(mappedStates)
                .withTypes(typeKeys)
                .withCreatedManually(createdManually)
                .withAborted(aborted)
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .build();

        Map<DemandForPaymentSortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

        return demandForPaymentService.findAllDemandsForPayment(criteria, sortCriteria);
    }

    @POST
    @Path("process")
    @Secured(all = true)
    @ApiOperation("Przetwarza przesłane sugestie wezwania do zapłaty - usuwa oznaczone do usunięcia i oznacza jako procesowane te do wysłania. Uruchamia asynchronicznie wysyłanie sugestii wezwań do eFaktury")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void processDemandsForPayment(@HeaderParam(AUTHORIZATION) String token,
                                             @ApiParam(required = true, value = "Sugestie wezwań do zapłaty do wysłania i usunięcia") ProcessDemandsForPaymentDto demands) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        demandForPaymentValidator.validateOperatorsPermission(loggedOperatorId);
        demandForPaymentValidator.validate(demands);
        demandForPaymentService.rejectDemandsForPayment(demands.getDemandsToReject(), loggedOperatorId, demands.getRejectReason());
        final String loggedOperatorFullName = jwtService.getLoggedOperatorFullName(token);
        demandForPaymentService.processDemandsForPayment(demands.getDemandsToSend(), loggedOperatorFullName);
    }

    @POST
    @Secured(all = true)
    @ApiOperation("Utworzenie wezwań do zapłaty")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createDemandsForPayment(@HeaderParam(AUTHORIZATION) String token,
                                            @ApiParam(required = true, value = "Dane do utworzenia wezwań do zapłaty") CreateDemandForPaymentRequestDto createDemandForPaymentRequestDto) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        demandForPaymentValidator.validateOperatorsPermission(loggedOperatorId);
        final List<ManualPrepareDemandForPaymentResult> demandsForPaymentManually =
                demandForPaymentService.createDemandsForPaymentManually(createDemandForPaymentRequestDto.getTypeKey(), createDemandForPaymentRequestDto.getContractNumbers());
        return Response.created(null).entity(demandsForPaymentManually).build();
    }

    @POST
    @Path("cancel/{demandForPaymentId}")
    @Secured(all = true)
    @ApiOperation("Uaktualnienie statusu wezwania wysłanego jako anulowane")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void updateIssuedDemandForPaymentStateToCancel(@HeaderParam(AUTHORIZATION) String token,
                                                              @ApiParam(name = "demandForPaymentId", value = "identyfikator wezwania")
                                                              @PathParam("demandForPaymentId") final long demandForPaymentId,
                                                              @ApiParam(required = true, value = "Parametry zmiany stanu wezwania") DemandForPaymentStatusChangeDto demandForPaymentStatusChangeDto) {
        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        demandForPaymentValidator.validateOperatorsPermission(loggedUserId);
        demandForPaymentStatusChangeDto.setStateChangeOperatorId(loggedUserId);
        demandForPaymentValidator.validateIssuedDemandForPayment(demandForPaymentId);
        demandForPaymentService.updateDemandForPaymentState(demandForPaymentId, demandForPaymentStatusChangeDto, DemandForPaymentState.CANCELED);
    }

    @GET
    @Path("report")
    @Secured(all = true)
    @ApiOperation("Pobranie listy wezwań do zapłaty w formacie CSV")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findCSVAllDemandsForPayment(@HeaderParam(AUTHORIZATION) String token,
                                                @ApiParam(name = "type", value = "Typ wezwania do zapłaty")
                                                @QueryParam("type") List<String> type,
                                                @ApiParam(name = "state", value = "Stan wezwania do zapłaty")
                                                @QueryParam("state") List<String> state,
                                                @ApiParam(name = "contractNo", value = "Numer umowy na wezwaniu do zapłaty")
                                                @QueryParam("contractNo") String contractNo,
                                                @ApiParam(name = "chargeInvoiceNo", value = "Numer faktury za opłatę")
                                                @QueryParam("chargeInvoiceNo") String chargeInvoiceNo,
                                                @ApiParam(name = "initialInvoiceNo", value = "Numer faktury inicjującej wystawienie")
                                                @QueryParam("initialInvoiceNo") String initialInvoiceNo,
                                                @ApiParam(name = "companyName", value = "Nazwa firmy")
                                                @QueryParam("companyName") String companyName,
                                                @ApiParam(name = "startIssueDate", value = "Początek okresu wystawienia")
                                                @QueryParam("startIssueDate") DateParam startIssueDate,
                                                @ApiParam(name = "endIssueDate", value = "Koniec okresu wystawienia")
                                                @QueryParam("endIssueDate") DateParam endIssueDate,
                                                @ApiParam(name = "startDueDate", value = "Początek okresu zapłaty")
                                                @QueryParam("startDueDate") DateParam startDueDate,
                                                @ApiParam(name = "endDueDate", value = "Koniec okresu zapłaty")
                                                @QueryParam("endDueDate") DateParam endDueDate,
                                                @ApiParam(name = "extCompanyId", value = "Identyfikator klienta (AIDA)")
                                                @QueryParam("extCompanyId") Long extCompanyId,
                                                @ApiParam(name = "segment", value = "Segment")
                                                @QueryParam("segment") Segment segment,
                                                @ApiParam(name = "createdManually", value = "Flaga pozwalająca na odfiltrowanie wezwań tworzonych ręcznie/automatycznie, true=zwraca tylko utworzone ręcznie, false=zwraca tylko utworzone automatycznie, brak wartości zwraca wszystko")
                                                @QueryParam("createdManually") Boolean createdManually,
                                                @ApiParam(name = "aborted", value = "Flaga pozwalająca na odfiltrowanie wezwań zastąpionych w procesie (np. poprzez ręcznie wystawione wezwanie tego samego stopnia)")
                                                @QueryParam("aborted") Boolean aborted,
                                                @ApiParam(name = "totalValues", value = "Liczba wszystkich wezwań")
                                                @QueryParam("totalValues") final int totalValues,
                                                @QueryParam("sortField") DemandForPaymentSortField sortField,
                                                @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder) throws IOException {

        final List<DemandForPaymentDto> values = new ArrayList<>();

        int firstResult = 0;

        final int pages = totalValues / CSV_DATA_ROWS_NUMBER_PART + 1;
        for (int i = 0; i < pages; i++) {

            final List<DemandForPaymentState> mappedStates = demandForPaymentValidator.validateState(state);
            final List<DemandForPaymentTypeKey> typeKeys = demandForPaymentValidator.validateType(type);
            final DemandForPaymentSearchCriteria criteria = aDemandForPaymentSearchCriteria()
                    .withFirstResult(firstResult)
                    .withMaxResults(CSV_DATA_ROWS_NUMBER_PART)
                    .withExtCompanyId(extCompanyId)
                    .withChargeInvoiceNo(chargeInvoiceNo)
                    .withInitialInvoiceNo(initialInvoiceNo)
                    .withCompanyName(companyName)
                    .withStartIssueDate(startIssueDate)
                    .withEndIssueDate(endIssueDate)
                    .withStartDueDate(startDueDate)
                    .withEndDueDate(endDueDate)
                    .withSegment(segment)
                    .withContractNo(contractNo)
                    .withState(mappedStates)
                    .withTypes(typeKeys)
                    .withCreatedManually(createdManually)
                    .withAborted(aborted)
                    .build();

            Map<DemandForPaymentSortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

            final List<DemandForPaymentDto> demandsForPayment = demandForPaymentService.findDemandsForPayment(criteria, sortCriteria);

            values.addAll(demandsForPayment);

            firstResult += CSV_DATA_ROWS_NUMBER_PART;
        }

        final File file = getFile(values);
        return Response.ok(file).type("text/csv").build();
    }

    private File getFile(final List<DemandForPaymentDto> values) throws IOException {
        final File file = File.createTempFile("CSVDemandForPaymentTmp", ".csv");
        try (final PrintWriter pw = new PrintWriter(file, "UTF-8")) {
            final StringBuilder sb = new StringBuilder();

            CSVUtils.writeDemandHeader(sb);

            for (final DemandForPaymentDto value : values) {
                CSVUtils.writeLine(sb, Arrays.asList(value.getIssueDate(), value.getDueDate(), value.getInitialInvoiceNo(), value.getExtCompanyId(),
                        value.getCompanyName(), value.getContractNumber(), value.getType().getKey(), value.getType().getSegment().getLabel(),
                        value.getChargeInvoiceNo(),
                        stateChangeReasonWithType(value),
                        value.getAttachmentRef()));
            }

            pw.write(sb.toString());
        }
        file.deleteOnExit();
        return file;
    }

    private String stateChangeReasonWithType(final DemandForPaymentDto value) {
        final Optional<String> reasonTypeLabel = Optional.ofNullable(value.getReasonTypeLabel());
        final Optional<String> stateChangeReason = Optional.ofNullable(value.getStateChangeReason());
        return String.format("%s%s%s",
                reasonTypeLabel.orElse(""),
                reasonTypeLabel.isPresent() && stateChangeReason.isPresent() ? " - " : "",
                stateChangeReason.orElse(""));
    }
}
