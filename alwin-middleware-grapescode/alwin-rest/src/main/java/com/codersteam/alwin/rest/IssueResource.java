package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.search.criteria.IssueForCompanySearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssueSearchCriteria;
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.common.sort.IssueSortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.issue.*;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.issue.*;
import com.codersteam.alwin.model.FindActiveIssueIdResponse;
import com.codersteam.alwin.model.issue.CreateIssueRequest;
import com.codersteam.alwin.model.issue.TerminateIssueRequest;
import com.codersteam.alwin.model.search.IssueForCompanySearchCriteriaBuilder;
import com.codersteam.alwin.model.search.IssueSearchCriteriaBuilder;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.util.IdsDto;
import com.codersteam.alwin.util.ManyToManyIdsDto;
import com.codersteam.alwin.validator.IssueConfigurationValidator;
import com.codersteam.alwin.validator.IssueValidator;
import com.codersteam.alwin.validator.TagValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

import static com.codersteam.alwin.core.api.model.user.OperatorType.*;
import static com.codersteam.alwin.model.search.IssuesSearchCriteriaBuilder.anIssuesSearchCriteria;
import static com.codersteam.alwin.validator.IssueValidator.shouldAssignGivenIssueIds;
import static java.lang.String.format;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/issues")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/issues")
public class IssueResource {

    private IssueService issueService;
    private JwtService jwtService;
    private IssueTypeService issueTypeService;
    private IssueValidator issueValidator;
    private IssueTerminationService issueTerminationService;
    private TagValidator tagValidator;
    private IssueCreatorService issueCreatorService;
    private IssueConfigurationService issueConfigurationService;
    private IssueConfigurationValidator issueConfigurationValidator;
    private IssueAssignnmentService issueAssignnmentService;

    public IssueResource() {
    }

    @Inject
    public IssueResource(IssueService issueService, JwtService jwtService, IssueTypeService issueTypeService,
                         IssueValidator issueValidator, IssueTerminationService issueTerminationService,
                         TagValidator tagValidator, IssueCreatorService issueCreatorService,
                         IssueConfigurationService issueConfigurationService, IssueConfigurationValidator issueConfigurationValidator,
                         IssueAssignnmentService issueAssignnmentService) {
        this.issueService = issueService;
        this.jwtService = jwtService;
        this.issueTypeService = issueTypeService;
        this.issueValidator = issueValidator;
        this.issueTerminationService = issueTerminationService;
        this.tagValidator = tagValidator;
        this.issueCreatorService = issueCreatorService;
        this.issueConfigurationService = issueConfigurationService;
        this.issueConfigurationValidator = issueConfigurationValidator;
        this.issueAssignnmentService = issueAssignnmentService;
    }

    @PATCH
    @Path("{issueId}")
    @Secured(all = true)
    @ApiOperation("Pobranie danych zlecenia po jego identyfikatorze wraz z uprzednim przeliczeniem salda")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public OperatedIssueDto findIssueAndUpdateBalance(@HeaderParam(AUTHORIZATION) String token,
                                                      @ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") Long issueId) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        return issueService.findOperatedIssueAndUpdateBalance(issueId, loggedOperatorType);
    }

    @GET
    @Secured(all = true)
    @ApiOperation("Pobranie stronicowanej listy zleceń z obszaru zalogowanego managera/admina")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<IssueDto> findAllIssues(@HeaderParam(AUTHORIZATION) String token,
                                        @ApiParam(name = "operatorId", value = "Identyfikator operatora do którego przypisane jest zlecenie")
                                        @QueryParam("operatorId") List<Long> operatorIds,
                                        @ApiParam(name = "state", value = "Status zlecenia")
                                        @QueryParam("state") List<String> states,
                                        @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                                        @QueryParam("tagId") List<Long> tagIds,
                                        @ApiParam(name = "startStartDate", value = "Początek okresu, w którym nastąpiło przydzielenie zlecenia")
                                        @QueryParam("startStartDate") DateParam startStartDate,
                                        @ApiParam(name = "endStartDate", value = "Koniec okresu, w którym nastąpiło przydzielenie zlecenia")
                                        @QueryParam("endStartDate") DateParam endStartDate,
                                        @ApiParam(name = "startContactDate", value = "Początek okresu, w którym nastąpił kontakt z klientem")
                                        @QueryParam("startContactDate") DateParam startContactDate,
                                        @ApiParam(name = "endContactDate", value = "Koniec okresu, w którym nastąpił kontakt z klientem")
                                        @QueryParam("endContactDate") DateParam endContactDate,
                                        @ApiParam(name = "totalCurrentBalancePLNMin", value = "Minimalna wartość salda dla zlecenia")
                                        @QueryParam("totalCurrentBalancePLNMin") BigDecimal totalCurrentBalancePLNMin,
                                        @ApiParam(name = "totalCurrentBalancePLNMax", value = "Maksymalna wartość salda dla zlecenia")
                                        @QueryParam("totalCurrentBalancePLNMax") BigDecimal totalCurrentBalancePLNMax,
                                        @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                        @QueryParam("firstResult") int firstResult,
                                        @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                        @QueryParam("maxResults") int maxResults,
                                        @ApiParam(name = "unassigned", value = "Nieprzypisane zlecenia")
                                        @QueryParam("unassigned") Boolean unassigned,
                                        @ApiParam(name = "priorityKey", value = "Wybrany priorytet")
                                        @QueryParam("priorityKey") String priorityKey,
                                        @ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                        @QueryParam("extCompanyId") List<Long> extCompanyIds,
                                        @ApiParam(name = "segment", value = "Segment klienta")
                                        @QueryParam("segment") String segment,
                                        @ApiParam(name = "issueTypeId", value = "Identyfikator typu zlecenia")
                                        @QueryParam("issueTypeId") Long issueTypeId,
                                        @QueryParam("sortField") IssueSortField sortField,
                                        @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder,
                                        @ApiParam(name = "nip", value = "Numer NIP klienta")
                                        @QueryParam("nip") final String nip,
                                        @ApiParam(name = "regon", value = "Numer REGON klienta")
                                        @QueryParam("regon") final String regon,
                                        @ApiParam(name = "companyName", value = "Nazwa firmy klienta lub jej część")
                                        @QueryParam("companyName") final String companyName,
                                        @ApiParam(name = "invoiceContractNo", value = "Numer umowy na fakturze")
                                        @QueryParam("invoiceContractNo") final String invoiceContractNo,
                                        @ApiParam(name = "personPesel", value = "Numer pesel osoby powiązanej")
                                        @QueryParam("personPesel") final String personPesel,
                                        @ApiParam(name = "personName", value = "Imię i nazwisko osoby powiązanej podane w dowolnej kolejności")
                                        @QueryParam("personName") final String personName,
                                        @ApiParam(name = "contactPhone", value = "Numer telefonu do kontaktu")
                                        @QueryParam("contactPhone") final String contactPhone,
                                        @ApiParam(name = "contactEmail", value = "Adres email do kontaktu")
                                        @QueryParam("contactEmail") final String contactEmail,
                                        @ApiParam(name = "contactName", value = "Imię i nazwisko osoby kontaktowej podane w dowolnej kolejności")
                                        @QueryParam("contactName") final String contactName) {

        issueValidator.validateStates(states);
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        final IssuesSearchCriteria criteria = anIssuesSearchCriteria()
                .withOperatorIds(operatorIds)
                .withStates(states)
                .withTagIds(tagIds)
                .withStartDate(startStartDate, endStartDate)
                .withContactDate(startContactDate, endContactDate)
                .withTotalCurrentBalancePLNMin(totalCurrentBalancePLNMin)
                .withTotalCurrentBalancePLNMax(totalCurrentBalancePLNMax)
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withUnassigned(unassigned)
                .withPriorityKey(priorityKey)
                .withExtCompanyIds(extCompanyIds)
                .withIssueTypeId(issueTypeId)
                .withSegment(segment)
                .withNip(nip)
                .withRegon(regon)
                .withComapnyName(companyName)
                .withInvoiceContractNo(invoiceContractNo)
                .withPersonPesel(personPesel)
                .withPersonName(personName)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();

        Map<IssueSortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

        return issueService.findAllIssues(loggedOperatorType, criteria, sortCriteria);
    }

    @GET
    @Path("fieldDebt")
    @Secured(all = true)
    @ApiOperation("Pobranie stronicowanej listy zleceń z obszaru zalogowanego windykatora terenowego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<IssueForFieldDebtCollectorDto> findAllIssuesForFieldDebtCollector(@HeaderParam(AUTHORIZATION) String token,
                                                                                  @ApiParam(name = "state", value = "Status zlecenia")
                                                                                  @QueryParam("state") List<String> states,
                                                                                  @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                                                                                  @QueryParam("tagId") List<Long> tagIds,
                                                                                  @ApiParam(name = "startStartDate", value = "Początek okresu, w którym nastąpiło przydzielenie zlecenia")
                                                                                  @QueryParam("startStartDate") DateParam startStartDate,
                                                                                  @ApiParam(name = "endStartDate", value = "Koniec okresu, w którym nastąpiło przydzielenie zlecenia")
                                                                                  @QueryParam("endStartDate") DateParam endStartDate,
                                                                                  @ApiParam(name = "startContactDate", value = "Początek okresu, w którym nastąpił kontakt z klientem")
                                                                                  @QueryParam("startContactDate") DateParam startContactDate,
                                                                                  @ApiParam(name = "endContactDate", value = "Koniec okresu, w którym nastąpił kontakt z klientem")
                                                                                  @QueryParam("endContactDate") DateParam endContactDate,
                                                                                  @ApiParam(name = "totalCurrentBalancePLNMin", value = "Minimalna wartość salda dla zlecenia")
                                                                                  @QueryParam("totalCurrentBalancePLNMin") BigDecimal totalCurrentBalancePLNMin,
                                                                                  @ApiParam(name = "totalCurrentBalancePLNMax", value = "Maksymalna wartość salda dla zlecenia")
                                                                                  @QueryParam("totalCurrentBalancePLNMax") BigDecimal totalCurrentBalancePLNMax,
                                                                                  @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                                                  @QueryParam("firstResult") int firstResult,
                                                                                  @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                                                  @QueryParam("maxResults") int maxResults,
                                                                                  @ApiParam(name = "unassigned", value = "Nieprzypisane zlecenia")
                                                                                  @QueryParam("unassigned") Boolean unassigned,
                                                                                  @ApiParam(name = "priorityKey", value = "Wybrany priorytet")
                                                                                  @QueryParam("priorityKey") String priorityKey,
                                                                                  @ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                                                                  @QueryParam("extCompanyId") List<Long> extCompanyIds,
                                                                                  @ApiParam(name = "segment", value = "Segment klienta")
                                                                                  @QueryParam("segment") String segment,
                                                                                  @ApiParam(name = "issueTypeId", value = "Identyfikator typu zlecenia")
                                                                                  @QueryParam("issueTypeId") Long issueTypeId,
                                                                                  @QueryParam("sortField") IssueSortField sortField,
                                                                                  @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder,
                                                                                  @ApiParam(name = "nip", value = "Numer NIP klienta")
                                                                                  @QueryParam("nip") final String nip,
                                                                                  @ApiParam(name = "regon", value = "Numer REGON klienta")
                                                                                  @QueryParam("regon") final String regon,
                                                                                  @ApiParam(name = "companyName", value = "Nazwa firmy klienta lub jej część")
                                                                                  @QueryParam("companyName") final String companyName,
                                                                                  @ApiParam(name = "invoiceContractNo", value = "Numer umowy na fakturze")
                                                                                  @QueryParam("invoiceContractNo") final String invoiceContractNo,
                                                                                  @ApiParam(name = "personPesel", value = "Numer pesel osoby powiązanej")
                                                                                  @QueryParam("personPesel") final String personPesel,
                                                                                  @ApiParam(name = "personName", value = "Imię i nazwisko osoby powiązanej podane w dowolnej kolejności")
                                                                                  @QueryParam("personName") final String personName,
                                                                                  @ApiParam(name = "contactPhone", value = "Numer telefonu do kontaktu")
                                                                                  @QueryParam("contactPhone") final String contactPhone,
                                                                                  @ApiParam(name = "contactEmail", value = "Adres email do kontaktu")
                                                                                  @QueryParam("contactEmail") final String contactEmail,
                                                                                  @ApiParam(name = "contactName", value = "Imię i nazwisko osoby kontaktowej podane w dowolnej kolejności")
                                                                                  @QueryParam("contactName") final String contactName) {

        issueValidator.validateStates(states);
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        final IssuesSearchCriteria criteria = anIssuesSearchCriteria()
                .withAssignedTo(Collections.singletonList(loggedOperatorId))
                .withStates(states)
                .withTagIds(tagIds)
                .withStartDate(startStartDate, endStartDate)
                .withContactDate(startContactDate, endContactDate)
                .withTotalCurrentBalancePLNMin(totalCurrentBalancePLNMin)
                .withTotalCurrentBalancePLNMax(totalCurrentBalancePLNMax)
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withUnassigned(unassigned)
                .withPriorityKey(priorityKey)
                .withExtCompanyIds(extCompanyIds)
                .withIssueTypeId(issueTypeId)
                .withSegment(segment)
                .withNip(nip)
                .withRegon(regon)
                .withComapnyName(companyName)
                .withInvoiceContractNo(invoiceContractNo)
                .withPersonPesel(personPesel)
                .withPersonName(personName)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();

        Map<IssueSortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

        return issueService.findAllFieldDebtIssues(loggedOperatorId, loggedOperatorType, criteria, sortCriteria);
    }

    @GET
    @Path("my")
    @Secured(all = true)
    @ApiOperation("Pobranie stronicowanej listy zleceń obsługiwanych przez operatora z kryteriami wyszukiwania")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<IssueDto> findOperatorIssuesWithSearchCriteria(@HeaderParam(AUTHORIZATION) String token,
                                                               @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                               @QueryParam("firstResult") int firstResult,
                                                               @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                               @QueryParam("maxResults") int maxResults,
                                                               @ApiParam(name = "startStartDate", value = "Początek okresu przydzielenia zlecenia")
                                                               @QueryParam("startStartDate") DateParam startStartDate,
                                                               @ApiParam(name = "endStartDate", value = "Koniec okresu przydzielenia zlecenia")
                                                               @QueryParam("endStartDate") DateParam endStartDate,
                                                               @ApiParam(name = "startExpirationDate", value = "Początek okresu zakończenia zlecenia")
                                                               @QueryParam("startExpirationDate") DateParam startExpirationDate,
                                                               @ApiParam(name = "endExpirationDate", value = "Koniec okresu zakończenia zlecenia")
                                                               @QueryParam("endExpirationDate") DateParam endExpirationDate,
                                                               @ApiParam(name = "startTotalCurrentBalancePLN", value = "Początek przedziału salda bieżącego (PLN)")
                                                               @QueryParam("startTotalCurrentBalancePLN") String startTotalCurrentBalancePLN,
                                                               @ApiParam(name = "endTotalCurrentBalancePLN", value = "Koniec przedziału salda bieżącego (PLN)")
                                                               @QueryParam("endTotalCurrentBalancePLN") String endTotalCurrentBalancePLN,
                                                               @ApiParam(name = "startActivityDate", value = "Początek przedziału terminu ostatniej wykonanej czynności windykacyjnej")
                                                               @QueryParam("startActivityDate") DateParam startActivityDate,
                                                               @ApiParam(name = "endActivityDate", value = "Koniec przedziału terminu ostatniej wykonanej czynności windykacyjnej")
                                                               @QueryParam("endActivityDate") DateParam endActivityDate,
                                                               @ApiParam(name = "startPlannedDate", value = "Początek przedziału terminu kolejnej zaplanowanej czynności windykacyjnej")
                                                               @QueryParam("startPlannedDate") DateParam startPlannedDate,
                                                               @ApiParam(name = "endPlannedDate", value = "Początek przedziału terminu kolejnej zaplanowanej czynności windykacyjnej")
                                                               @QueryParam("endPlannedDate") DateParam endPlannedDate) {

        issueValidator.validateBalanceWithDotAndTwoDecimalValues(startTotalCurrentBalancePLN);
        issueValidator.validateBalanceWithDotAndTwoDecimalValues(endTotalCurrentBalancePLN);

        final IssueSearchCriteria issueSearchCriteria = IssueSearchCriteriaBuilder.aSearchCriteria()
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withStartStartDate(startStartDate)
                .withEndStartDate(endStartDate)
                .withStartExpirationDate(startExpirationDate)
                .withEndExpirationDate(endExpirationDate)
                .withStartTotalCurrentBalancePLN(startTotalCurrentBalancePLN)
                .withEndTotalCurrentBalancePLN(endTotalCurrentBalancePLN)
                .withStartActivityDate(startActivityDate)
                .withEndActivityDate(endActivityDate)
                .withStartPlannedDate(startPlannedDate)
                .withEndPlannedDate(endPlannedDate)
                .build();

        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        return issueService.findOperatorIssues(loggedUserId, issueSearchCriteria);
    }

    @POST
    @Path("work")
    @Secured({PHONE_DEBT_COLLECTOR, FIELD_DEBT_COLLECTOR, PHONE_DEBT_COLLECTOR_1, PHONE_DEBT_COLLECTOR_2})
    @ApiOperation("Pobieranie najważniejszego zlecenia do pracy dla zalogowanego operatora")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findWorkForLoggedUser(@HeaderParam(AUTHORIZATION) String token) {
        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        return issueAssignnmentService.findWorkForUser(loggedUserId)
                .map(i -> Response.ok(i).build())
                .orElse(Response.noContent().build());
    }

    @GET
    @Path("company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich zleceń dla danego klienta z pominięciem zlecenia o podanym identyfikatorze")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<CompanyIssueDto> findAllIssuesForCompanyExcludingGivenIssue(@ApiParam(name = "excludedIssueId", value = "Identyfikator ignorowanego zlecenia")
                                                                            @QueryParam("excludedIssueId") long excludedIssueId,
                                                                            @ApiParam(name = "companyId", value = "Identyfikator klienta")
                                                                            @PathParam("companyId") long companyId,
                                                                            @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                                            @QueryParam("firstResult") int firstResult,
                                                                            @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                                            @QueryParam("maxResults") int maxResults,
                                                                            @ApiParam(name = "activityDateFrom", value = "Data początku okresu, w którym czynność miała miejsce")
                                                                            @QueryParam("activityDateFrom") DateParam startDate,
                                                                            @ApiParam(name = "activityDateTo", value = "Data zakończenia okresu, w którym czynność miała miejsce")
                                                                            @QueryParam("activityDateTo") DateParam endDate,
                                                                            @ApiParam(name = "issueTypeId", value = "Identyfikator typu zlecenia")
                                                                            @QueryParam("issueTypeId") Long issueTypeId,
                                                                            @ApiParam(name = "activityTypeId", value = "Identyfikator typu czynności")
                                                                            @QueryParam("activityTypeId") Long activityTypeId) {

        final IssueForCompanySearchCriteria searchCriteria = IssueForCompanySearchCriteriaBuilder.aSearchCriteria()
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withIssueType(issueTypeId)
                .withActivityType(activityTypeId)
                .build();

        return issueService.findAllIssuesForCompanyExcludingGivenIssue(companyId, excludedIssueId, searchCriteria);
    }

    @GET
    @Path("types")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich typów zleceń")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<IssueTypeWithOperatorTypesDto> findAllIssueTypes() {
        return issueTypeService.findAllIssueTypes();
    }

    @GET
    @Path("types/my")
    @Secured(all = true)
    @ApiOperation("Pobieranie typów zleceń windykacyjnych dla zalogowanego operatora")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<IssueTypeDto> findMyIssueTypes(@HeaderParam(AUTHORIZATION) String token) {
        OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        return issueTypeService.findIssueTypesByOperatorType(loggedOperatorType);
    }

    @GET
    @Path("states")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich możliwych stanów dla zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<IssueStateDto> findAllIssueStates(@ApiParam(name = "onlyActive", value = "Flaga determinująca czy mają zostać zwrócone tylko aktywne statusy")
                                                  @QueryParam("onlyActive") Boolean onlyActive) {
        return (onlyActive != null && onlyActive) ? IssueStateDto.ACTIVE_STATES : IssueStateDto.STATES;
    }

    @GET
    @Path("managed")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie wszystkich zleceń z obszaru zalogowanego managera")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<IssueDto> findAllFilteredManagedIssues(@HeaderParam(AUTHORIZATION) String token,
                                                       @ApiParam(name = "operatorId", value = "Identyfikator operatora do którego przypisane jest zlecenie")
                                                       @QueryParam("operatorId") List<Long> operatorIds,
                                                       @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                                                       @QueryParam("tagId") List<Long> tagIds,
                                                       @ApiParam(name = "state", value = "Status zlecenia")
                                                       @QueryParam("state") List<String> states,
                                                       @ApiParam(name = "startStartDate", value = "Początek okresu, w którym nastąpiło przydzielenie zlecenia")
                                                       @QueryParam("startStartDate") DateParam startStartDate,
                                                       @ApiParam(name = "endStartDate", value = "Koniec okresu, w którym nastąpiło przydzielenie zlecenia")
                                                       @QueryParam("endStartDate") DateParam endStartDate,
                                                       @ApiParam(name = "startContactDate", value = "Początek okresu, w którym nastąpił kontakt z klientem")
                                                       @QueryParam("startContactDate") DateParam startContactDate,
                                                       @ApiParam(name = "endContactDate", value = "Koniec okresu, w którym nastąpił kontakt z klientem")
                                                       @QueryParam("endContactDate") DateParam endContactDate,
                                                       @ApiParam(name = "totalCurrentBalancePLNMin", value = "Minimalna wartość salda dla zlecenia")
                                                       @QueryParam("totalCurrentBalancePLNMin") BigDecimal totalCurrentBalancePLNMin,
                                                       @ApiParam(name = "totalCurrentBalancePLNMax", value = "Maksymalna wartość salda dla zlecenia")
                                                       @QueryParam("totalCurrentBalancePLNMax") BigDecimal totalCurrentBalancePLNMax,
                                                       @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                       @QueryParam("firstResult") int firstResult,
                                                       @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                       @QueryParam("maxResults") int maxResults,
                                                       @ApiParam(name = "unassigned", value = "Nieprzypisane zlecenia")
                                                       @QueryParam("unassigned") Boolean unassigned,
                                                       @ApiParam(name = "priorityKey", value = "Wybrany priorytet")
                                                       @QueryParam("priorityKey") String priorityKey,
                                                       @ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                                       @QueryParam("extCompanyId") List<Long> extCompanyIds,
                                                       @ApiParam(name = "segment", value = "Segment klienta")
                                                       @QueryParam("segment") String segment,
                                                       @ApiParam(name = "issueTypeId", value = "Identyfikator typu zlecenia")
                                                       @QueryParam("issueTypeId") Long issueTypeId,
                                                       @QueryParam("sortField") IssueSortField sortField,
                                                       @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder,
                                                       @ApiParam(name = "nip", value = "Numer NIP klienta")
                                                       @QueryParam("nip") final String nip,
                                                       @ApiParam(name = "regon", value = "Numer REGON klienta")
                                                       @QueryParam("regon") final String regon,
                                                       @ApiParam(name = "companyName", value = "Nazwa firmy klienta lub jej część")
                                                       @QueryParam("companyName") final String companyName,
                                                       @ApiParam(name = "invoiceContractNo", value = "Numer umowy na fakturze")
                                                       @QueryParam("invoiceContractNo") final String invoiceContractNo,
                                                       @ApiParam(name = "personPesel", value = "Numer pesel osoby powiązanej")
                                                       @QueryParam("personPesel") final String personPesel,
                                                       @ApiParam(name = "personName", value = "Imię i nazwisko osoby powiązanej podane w dowolnej kolejności")
                                                       @QueryParam("personName") final String personName,
                                                       @ApiParam(name = "contactPhone", value = "Numer telefonu do kontaktu")
                                                       @QueryParam("contactPhone") final String contactPhone,
                                                       @ApiParam(name = "contactEmail", value = "Adres email do kontaktu")
                                                       @QueryParam("contactEmail") final String contactEmail,
                                                       @ApiParam(name = "contactName", value = "Imię i nazwisko osoby kontaktowej podane w dowolnej kolejności")
                                                       @QueryParam("contactName") final String contactName) {
        issueValidator.validateStates(states);
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        final IssuesSearchCriteria criteria = anIssuesSearchCriteria()
                .withOperatorIds(operatorIds)
                .withTagIds(tagIds)
                .withStates(states)
                .withStartDate(startStartDate, endStartDate)
                .withContactDate(startContactDate, endContactDate)
                .withTotalCurrentBalancePLNMin(totalCurrentBalancePLNMin)
                .withTotalCurrentBalancePLNMax(totalCurrentBalancePLNMax)
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withUnassigned(unassigned)
                .withPriorityKey(priorityKey)
                .withExtCompanyIds(extCompanyIds)
                .withIssueTypeId(issueTypeId)
                .withSegment(segment)
                .withNip(nip)
                .withRegon(regon)
                .withComapnyName(companyName)
                .withInvoiceContractNo(invoiceContractNo)
                .withPersonPesel(personPesel)
                .withPersonName(personName)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();

        Map<IssueSortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

        return issueService.findAllFilteredManagedIssues(loggedOperatorType, criteria, sortCriteria);
    }

    @POST
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Utworzenie zlecenia windykacyjnego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public IssueCreateResult createIssue(@HeaderParam(AUTHORIZATION) String token,
                                         @ApiParam(required = true, value = "Dane do utworzenia zlecenia") CreateIssueRequest createIssueRequest) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        final Long extCompanyId = createIssueRequest.getExtCompanyId();
        final Long issueTypeId = createIssueRequest.getIssueTypeId();
        final Date expirationDate = createIssueRequest.getExpirationDate();
        final Long assigneeId = createIssueRequest.getAssigneeId();
        issueValidator.validateIssueCreate(extCompanyId, issueTypeId, loggedOperatorId, assigneeId, expirationDate);
        return issueCreatorService.createIssueManually(extCompanyId, issueTypeId, expirationDate, assigneeId);
    }

    @GET
    @Path("active/company/{extCompanyId}")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie identyfikatora aktywnego zlecenia dla firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findActiveIssueId(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                      @PathParam("extCompanyId") long extCompanyId) {
        final Optional<Long> companyActiveIssueId = issueService.findCompanyActiveIssueId(extCompanyId);
        return companyActiveIssueId.map(issueId -> Response.ok(new FindActiveIssueIdResponse(issueId)).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @POST
    @Path("terminate/{issueId}")
    @Secured(all = true)
    @ApiOperation("Przedterminowe zakończenie zlecenia windykacyjnego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response terminateIssue(@HeaderParam(AUTHORIZATION) String token,
                                   @ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") Long issueId,
                                   @ApiParam(required = true, value = "Dane do zakończenia zlecenia") TerminateIssueRequest terminateIssueRequest) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        final String terminationCause = terminateIssueRequest.getTerminationCause();
        final boolean excludedFromStats = terminateIssueRequest.isExcludedFromStats();
        final String exclusionFromStatsCause = terminateIssueRequest.getExclusionFromStatsCause();
        issueValidator.validateTerminateIssue(issueId, terminationCause, exclusionFromStatsCause);
        final TerminateIssueResult result = issueService.terminateIssue(issueId, loggedOperatorId, terminationCause, excludedFromStats, exclusionFromStatsCause);
        return Response.ok(format("\"%s\"", result.getMessage())).build();
    }

    @GET
    @Path("termination/{issueId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie żądania przedterminowego zakończenia zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public IssueTerminationRequestDto findTerminationRequest(@ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") Long issueId) {
        return issueTerminationService.findTerminationRequestByIssueId(issueId);
    }

    @POST
    @Path("termination/accept/{terminationRequestId}")
    @Secured(all = true)
    @ApiOperation("Zaakceptowanie żądania o przedterminowe zakończenie zlecenia oraz zakończenie zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response acceptTerminationRequest(@HeaderParam(AUTHORIZATION) String token,
                                             @ApiParam(name = "terminationRequestId", value = "Identyfikator żądania o przedterminowe zakończenie zlecenia")
                                             @PathParam("terminationRequestId") Long terminationRequestId,
                                             @ApiParam(required = true, value = "Dane do zaakceptowania żądania") IssueTerminationRequestDto terminationRequest) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        issueValidator.validateAcceptIssueTerminateRequest(terminationRequestId, loggedOperatorId, terminationRequest);
        issueTerminationService.accept(terminationRequestId, loggedOperatorId, terminationRequest);
        return Response.ok().build();
    }

    @POST
    @Path("termination/reject/{terminationRequestId}")
    @Secured(all = true)
    @ApiOperation("Odrzucenie żądania o przedterminowe zakończenie zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response rejectTerminationRequest(@HeaderParam(AUTHORIZATION) String token,
                                             @ApiParam(name = "terminationRequestId", value = "Identyfikator żądania o przedterminowe zakończenie zlecenia")
                                             @PathParam("terminationRequestId") Long terminationRequestId,
                                             @ApiParam(required = true, value = "Dane do odrzucenia żądania") IssueTerminationRequestDto terminationRequest) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        final String comment = terminationRequest.getComment();
        issueValidator.validateRejectIssueTerminateRequest(terminationRequestId, loggedOperatorId, comment);
        issueTerminationService.reject(terminationRequestId, loggedOperatorId, terminationRequest);
        return Response.ok().build();
    }

    @PATCH
    @Path("exclusion")
    @Secured(all = true)
    @ApiOperation("Aktualizacja parametru wykluczenia faktury z obsługi zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateIssueInvoiceExclusionRequest(@HeaderParam(AUTHORIZATION) String token,
                                                       @ApiParam(required = true, value = "Dane do odrzucenia żądania") ExclusionRequestDto exclusionRequest) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        issueValidator.validateOperatorIsAllowedToExcludeInvoicePermission(loggedOperatorId);
        issueService.updateIssueInvoicesExclusion(exclusionRequest.getIssueId(), exclusionRequest.getInvoiceId(), exclusionRequest.isExcluded());

        return Response.accepted().build();
    }

    @GET
    @Path("priorities")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich możliwych priorytetów dla zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<IssuePriorityDto> findAllIssuePriorities() {
        return IssuePriorityDto.PRIORITIES;
    }

    @PATCH
    @Path("priority/{priorityKey}")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Ustawianie priorytetu wybranych lub wszystkich zleceń, które spełniają przekazane filtry")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updatePriority(@HeaderParam(AUTHORIZATION) String token,
                                   @ApiParam(required = true, value = "Identyfikatory zleceń do przypisania") IdsDto ids,
                                   @ApiParam(required = true, value = "wybrany do ustawienia priorytet")
                                   @PathParam("priorityKey") String priorityKey,
                                   @ApiParam(name = "operatorId", value = "Identyfikator operatora do którego przypisane jest zlecenie")
                                   @QueryParam("operatorId") List<Long> operatorIds,
                                   @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                                   @QueryParam("tagId") List<Long> tagIds,
                                   @ApiParam(name = "startStartDate", value = "Początek okresu, w którym nastąpiło przydzielenie zlecenia")
                                   @QueryParam("startStartDate") DateParam startStartDate,
                                   @ApiParam(name = "endStartDate", value = "Koniec okresu, w którym nastąpiło przydzielenie zlecenia")
                                   @QueryParam("endStartDate") DateParam endStartDate,
                                   @ApiParam(name = "startContactDate", value = "Początek okresu, w którym nastąpił kontakt z klientem")
                                   @QueryParam("startContactDate") DateParam startContactDate,
                                   @ApiParam(name = "endContactDate", value = "Koniec okresu, w którym nastąpił kontakt z klientem")
                                   @QueryParam("endContactDate") DateParam endContactDate,
                                   @ApiParam(name = "totalCurrentBalancePLNMin", value = "Minimalna wartość salda dla zlecenia")
                                   @QueryParam("totalCurrentBalancePLNMin") BigDecimal totalCurrentBalancePLNMin,
                                   @ApiParam(name = "totalCurrentBalancePLNMax", value = "Maksymalna wartość salda dla zlecenia")
                                   @QueryParam("totalCurrentBalancePLNMax") BigDecimal totalCurrentBalancePLNMax,
                                   @ApiParam(name = "unassigned", value = "Nieprzypisane zlecenia")
                                   @QueryParam("unassigned") Boolean unassigned,
                                   @ApiParam(name = "state", value = "Status zlecenia")
                                   @QueryParam("state") List<String> states,
                                   @ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                   @QueryParam("extCompanyId") List<Long> extCompanyIds,
                                   @ApiParam(name = "segment", value = "Segment klienta")
                                   @QueryParam("segment") String segment,
                                   @ApiParam(name = "issueTypeId", value = "Identyfikator typu zlecenia")
                                   @QueryParam("issueTypeId") Long issueTypeId,
                                   @ApiParam(name = "nip", value = "Numer NIP klienta")
                                   @QueryParam("nip") final String nip,
                                   @ApiParam(name = "regon", value = "Numer REGON klienta")
                                   @QueryParam("regon") final String regon,
                                   @ApiParam(name = "companyName", value = "Nazwa firmy klienta lub jej część")
                                   @QueryParam("companyName") final String companyName,
                                   @ApiParam(name = "invoiceContractNo", value = "Numer umowy na fakturze")
                                   @QueryParam("invoiceContractNo") final String invoiceContractNo,
                                   @ApiParam(name = "personPesel", value = "Numer pesel osoby powiązanej")
                                   @QueryParam("personPesel") final String personPesel,
                                   @ApiParam(name = "personName", value = "Imię i nazwisko osoby powiązanej podane w dowolnej kolejności")
                                   @QueryParam("personName") final String personName,
                                   @ApiParam(name = "contactPhone", value = "Numer telefonu do kontaktu")
                                   @QueryParam("contactPhone") final String contactPhone,
                                   @ApiParam(name = "contactEmail", value = "Adres email do kontaktu")
                                   @QueryParam("contactEmail") final String contactEmail,
                                   @ApiParam(name = "contactName", value = "Imię i nazwisko osoby kontaktowej podane w dowolnej kolejności")
                                   @QueryParam("contactName") final String contactName,
                                   @ApiParam(name = "updateAll",
                                           value = "Określa czy wszystkim zleceniom spełniającym warunki powinien zostać ustawiony odpowiedni priorytet. Wartość true ignoruje parametr ids.")
                                   @QueryParam("updateAll") Boolean updateAll) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);

        if (shouldAssignGivenIssueIds(updateAll)) {
            return updatePrioritiesForGivenIssueIds(priorityKey, ids, loggedOperatorType);
        }

        final IssuesSearchCriteria criteria = anIssuesSearchCriteria()
                .withOperatorIds(operatorIds)
                .withTagIds(tagIds)
                .withStates(states)
                .withStartDate(startStartDate, endStartDate)
                .withContactDate(startContactDate, endContactDate)
                .withTotalCurrentBalancePLNMin(totalCurrentBalancePLNMin)
                .withTotalCurrentBalancePLNMax(totalCurrentBalancePLNMax)
                .withUnassigned(unassigned)
                .withExtCompanyIds(extCompanyIds)
                .withIssueTypeId(issueTypeId)
                .withSegment(segment)
                .withNip(nip)
                .withRegon(regon)
                .withComapnyName(companyName)
                .withInvoiceContractNo(invoiceContractNo)
                .withPersonPesel(personPesel)
                .withPersonName(personName)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();

        return updatePrioritiesForAllFilteredIssues(priorityKey, loggedOperatorType, criteria);
    }

    @PATCH
    @Path("{issueId}/tags")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Ustawianie etykiety wybranych lub wszystkich zleceń, które spełniają przekazane filtry")
    public Response assignTags(@ApiParam(required = true, value = "Identyfikator zlecenia")
                               @PathParam("issueId") long issueId,
                               @ApiParam(required = true, value = "Identyfikatory etykiety dla zlecenia") IdsDto tagIds) {
        issueValidator.checkIfIssueExists(issueId);
        tagValidator.checkIfTagsExists(tagIds);
        issueService.assignTags(issueId, tagIds.getIds());
        return Response.accepted().build();
    }

    @PATCH
    @Path("tags")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Ustawianie etykiety dla wybranych lub wszystkich zleceń, które spełniają przekazane filtry")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response assignTags(@HeaderParam(AUTHORIZATION) String token,
                               @ApiParam(required = true, value = "Identyfikatory zleceń oraz etykiet do przypisania") ManyToManyIdsDto ids,
                               @ApiParam(name = "operatorId", value = "Identyfikator operatora do którego przypisane jest zlecenie")
                               @QueryParam("operatorId") List<Long> operatorIds,
                               @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                               @QueryParam("tagId") List<Long> tagIds,
                               @ApiParam(name = "state", value = "Status zlecenia")
                               @QueryParam("state") List<String> states,
                               @ApiParam(name = "startStartDate", value = "Początek okresu, w którym nastąpiło przydzielenie zlecenia")
                               @QueryParam("startStartDate") DateParam startStartDate,
                               @ApiParam(name = "endStartDate", value = "Koniec okresu, w którym nastąpiło przydzielenie zlecenia")
                               @QueryParam("endStartDate") DateParam endStartDate,
                               @ApiParam(name = "startContactDate", value = "Początek okresu, w którym nastąpił kontakt z klientem")
                               @QueryParam("startContactDate") DateParam startContactDate,
                               @ApiParam(name = "endContactDate", value = "Koniec okresu, w którym nastąpił kontakt z klientem")
                               @QueryParam("endContactDate") DateParam endContactDate,
                               @ApiParam(name = "totalCurrentBalancePLNMin", value = "Minimalna wartość salda dla zlecenia")
                               @QueryParam("totalCurrentBalancePLNMin") BigDecimal totalCurrentBalancePLNMin,
                               @ApiParam(name = "totalCurrentBalancePLNMax", value = "Maksymalna wartość salda dla zlecenia")
                               @QueryParam("totalCurrentBalancePLNMax") BigDecimal totalCurrentBalancePLNMax,
                               @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                               @QueryParam("firstResult") int firstResult,
                               @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                               @QueryParam("maxResults") int maxResult,
                               @ApiParam(name = "unassigned", value = "Nieprzypisane zlecenia")
                               @QueryParam("unassigned") Boolean unassigned,
                               @ApiParam(name = "priorityKey", value = "Wybrany priorytet")
                               @QueryParam("priorityKey") String priorityKey,
                               @ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                               @QueryParam("extCompanyId") List<Long> extCompanyIds,
                               @ApiParam(name = "segment", value = "Segment klienta")
                               @QueryParam("segment") String segment,
                               @ApiParam(name = "issueTypeId", value = "Identyfikator typu zlecenia")
                               @QueryParam("issueTypeId") Long issueTypeId,
                               @ApiParam(name = "nip", value = "Numer NIP klienta")
                               @QueryParam("nip") final String nip,
                               @ApiParam(name = "regon", value = "Numer REGON klienta")
                               @QueryParam("regon") final String regon,
                               @ApiParam(name = "companyName", value = "Nazwa firmy klienta lub jej część")
                               @QueryParam("companyName") final String companyName,
                               @ApiParam(name = "invoiceContractNo", value = "Numer umowy na fakturze")
                               @QueryParam("invoiceContractNo") final String invoiceContractNo,
                               @ApiParam(name = "personPesel", value = "Numer pesel osoby powiązanej")
                               @QueryParam("personPesel") final String personPesel,
                               @ApiParam(name = "personName", value = "Imię i nazwisko osoby powiązanej podane w dowolnej kolejności")
                               @QueryParam("personName") final String personName,
                               @ApiParam(name = "contactPhone", value = "Numer telefonu do kontaktu")
                               @QueryParam("contactPhone") final String contactPhone,
                               @ApiParam(name = "contactEmail", value = "Adres email do kontaktu")
                               @QueryParam("contactEmail") final String contactEmail,
                               @ApiParam(name = "contactName", value = "Imię i nazwisko osoby kontaktowej podane w dowolnej kolejności")
                               @QueryParam("contactName") final String contactName,
                               @ApiParam(name = "updateAll",
                                       value = "Określa czy wszystkim zleceniom spełniającym warunki powinna zostać ustawiona odpowiednia etykieta. Wartość true ignoruje parametr ids.")
                               @QueryParam("updateAll") Boolean updateAll) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);

        if (shouldAssignGivenIssueIds(updateAll)) {
            return assignTagsForGivenIssueIds(ids, loggedOperatorType);
        }

        final IssuesSearchCriteria criteria = anIssuesSearchCriteria()
                .withOperatorIds(operatorIds)
                .withStates(states)
                .withTagIds(tagIds)
                .withStartDate(startStartDate, endStartDate)
                .withContactDate(startContactDate, endContactDate)
                .withTotalCurrentBalancePLNMin(totalCurrentBalancePLNMin)
                .withTotalCurrentBalancePLNMax(totalCurrentBalancePLNMax)
                .withPriorityKey(priorityKey)
                .withExtCompanyIds(extCompanyIds)
                .withIssueTypeId(issueTypeId)
                .withSegment(segment)
                .withNip(nip)
                .withRegon(regon)
                .withComapnyName(companyName)
                .withInvoiceContractNo(invoiceContractNo)
                .withPersonPesel(personPesel)
                .withPersonName(personName)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();
        return assignTagsForAllFilteredIssues(ids, loggedOperatorType, criteria);
    }

    @GET
    @Path("configurations")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie konfiguracji zlecenia windykacyjnego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<IssueTypeConfigurationDto> findAllIssueTypeConfiguration() {
        return issueConfigurationService.findAllIssueTypeConfigurations();
    }

    @PATCH
    @Path("configuration/{configurationId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Aktualizacja wybranej konfiguracji zlecenia windykacyjnego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateIssueTypeConfiguration(@ApiParam(name = "configurationId", value = "Identyfikator konfiguracji")
                                                 @PathParam("configurationId") final long configurationId,
                                                 @ApiParam(required = true, value = "Dane do aktualizacju zlecenia windykacyjnego") final IssueTypeConfigurationDto
                                                         issueTypeConfigurationDto) {
        issueConfigurationValidator.validate(issueTypeConfigurationDto);
        issueConfigurationService.updateIssueTypeConfiguration(configurationId, issueTypeConfigurationDto);
        return Response.accepted().build();
    }

    private Response updatePrioritiesForAllFilteredIssues(final String priorityKey, final OperatorType loggedOperatorType, final IssuesSearchCriteria criteria) {
        final int priority = IssuePriorityDto.priorityByKey(priorityKey);
        issueService.updateIssuesPriority(priority, loggedOperatorType, criteria);

        return Response.accepted().build();
    }

    private Response updatePrioritiesForGivenIssueIds(final String priorityKey, final IdsDto ids, final OperatorType loggedOperatorType) {
        issueValidator.validateManagedIssues(loggedOperatorType, ids);

        final int priority = IssuePriorityDto.priorityByKey(priorityKey);
        issueService.updateIssuesPriority(priority, ids.getIds());

        return Response.accepted().build();
    }

    private Response assignTagsForAllFilteredIssues(final ManyToManyIdsDto ids, final OperatorType loggedOperatorType, final IssuesSearchCriteria criteria) {
        tagValidator.checkIfTagsExists(ids.getIdsToAssign());
        issueService.updateIssuesTags(ids.getIdsToAssign(), loggedOperatorType, criteria);
        return Response.accepted().build();
    }

    private Response assignTagsForGivenIssueIds(final ManyToManyIdsDto ids, final OperatorType loggedOperatorType) {
        issueValidator.validateManagedIssues(loggedOperatorType, new IdsDto(ids.getIds()));
        tagValidator.checkIfTagsExists(ids.getIdsToAssign());
        issueService.updateIssuesTags(ids.getIdsToAssign(), ids.getIds());
        return Response.accepted().build();
    }
}
