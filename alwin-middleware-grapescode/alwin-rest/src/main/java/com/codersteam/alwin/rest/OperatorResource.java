package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.auth.model.ChangePasswordRequest;
import com.codersteam.alwin.common.search.criteria.IssuesSearchCriteria;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.issue.IssueService;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.core.api.service.operator.OperatorTypeService;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.util.IdsDto;
import com.codersteam.alwin.validator.IssueValidator;
import com.codersteam.alwin.validator.OperatorValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;
import org.apache.commons.lang3.BooleanUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.core.api.model.user.OperatorType.*;
import static com.codersteam.alwin.model.search.IssuesSearchCriteriaBuilder.anIssuesSearchCriteria;
import static com.codersteam.alwin.validator.IssueValidator.shouldAssignGivenIssueIds;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Path("/operators")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/operators")
public class OperatorResource {

    private OperatorService operatorService;
    private JwtService jwtService;
    private IssueService issueService;
    private IssueValidator issueValidator;
    private OperatorTypeService operatorTypeService;
    private OperatorValidator operatorValidator;

    public OperatorResource() {
    }

    @Inject
    public OperatorResource(OperatorService operatorService,
                            JwtService jwtService,
                            IssueService issueService,
                            IssueValidator issueValidator,
                            OperatorTypeService operatorTypeService,
                            OperatorValidator operatorValidator) {
        this.operatorService = operatorService;
        this.jwtService = jwtService;
        this.issueService = issueService;
        this.issueValidator = issueValidator;
        this.operatorTypeService = operatorTypeService;
        this.operatorValidator = operatorValidator;
    }


    @GET
    @Path("managed")
    @Secured(all = true)
    @ApiOperation("Pobieranie operatorów podległych zalogowanemu operatorowi")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<OperatorDto> findManagedOperators(@HeaderParam(AUTHORIZATION) String token,
                                                  @ApiParam(name = "issueTypeId", value = "Opcjonalny identyfikator typu zlecenia") @QueryParam("issueTypeId") Long issueTypeId) {
        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        return operatorService.findManagedOperators(loggedUserId, issueTypeId);
    }

    @GET
    @Path("accountManagers")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszysktich opiekunów klientów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<OperatorDto> findAllAccountManagers() {
        return operatorService.findAllAccountManagers();
    }

    @GET
    @Path("company/{companyId}/activities")
    @Secured(all = true)
    @ApiOperation("Pobieranie operatorów przypisanych do jakiejkolwiek czynności dla klienta o podanym identyfikatorze")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<OperatorUserDto> findOperatorsAssignToCompanyActivities(@ApiParam(name = "companyId", value = "Identyfikator klienta")
                                                                        @PathParam("companyId") long companyId) {
        return operatorService.findOperatorsAssignToCompanyActivities(companyId);
    }

    @POST
    @Path("/managed/issues")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Przypisywanie do operatora wybranych lub wszystkich zleceń, które spełniają przekazane filtry")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<OperatorDto> findManagedOperatorsForIssue(@HeaderParam(AUTHORIZATION) String token,
                                                          @ApiParam(required = true, value = "Identyfikatory zleceń do przypisania") IdsDto ids,
                                                          @ApiParam(name = "operatorId", value = "Identyfikatory operatorów do których przypisane są zlecenia")
                                                 @QueryParam("operatorId") List<Long> operatorIds,
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
                                                          @ApiParam(name = "state", value = "Status zlecenia")
                                                 @QueryParam("state") List<String> states,
                                                          @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                                                 @QueryParam("tagId") List<Long> tagIds,
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
                                                         value = "Określa czy wszystkim zleceniom spełniającym warunki powinien zostać przypisany operator. Wartość true ignoruje parametr ids.")
                                                 @QueryParam("updateAll") Boolean updateAll) {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);

        final List<OperatorDto> managedOperators = operatorService.findManagedOperators(loggedOperatorId, null);
        if (isEmpty(managedOperators)) {
            return managedOperators;
        }

        if (BooleanUtils.isNotTrue(updateAll)) {
            final Set<OperatorTypeDto> issuesOperatorTypes = issueService.findIssuesOperatorTypes(ids.getIds());
            final List<OperatorDto> operators =
                    managedOperators.stream().filter(operatorDto -> issuesOperatorTypes.contains(operatorDto.getType())).collect(toList());
            return operators;
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
                .withPersonName(personName)
                .withPersonPesel(personPesel)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();

        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        final Set<OperatorTypeDto> issuesOperatorTypes = issueService.findIssuesOperatorTypes(loggedOperatorType, criteria);

        return managedOperators.stream()
                .filter(operatorDto -> issuesOperatorTypes.contains(operatorDto.getType()))
                .collect(toList());
    }

    @PATCH
    @Path("{operatorToAssignId}/assignment")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Przypisywanie do operatora wybranych lub wszystkich zleceń, które spełniają przekazane filtry")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response assign(@HeaderParam(AUTHORIZATION) String token,
                           @ApiParam(required = true, value = "Identyfikatory zleceń do przypisania") IdsDto ids,
                           @ApiParam(name = "operatorToAssignId", value = "Identyfikator operatora do którego przypisywane są zlecenia")
                           @PathParam("operatorToAssignId") long operatorToAssignId,
                           @ApiParam(name = "operatorId", value = "Identyfikatory operatorów do których przypisane są zlecenia")
                           @QueryParam("operatorId") List<Long> operatorIds,
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
                           @ApiParam(name = "state", value = "Status zlecenia")
                           @QueryParam("state") List<String> states,
                           @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                           @QueryParam("tagId") List<Long> tagIds,
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
                                   value = "Określa czy wszystkim zleceniom spełniającym warunki powinien zostać przypisany operator. Wartość true ignoruje parametr ids.")
                           @QueryParam("updateAll") Boolean updateAll) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);

        if (shouldAssignGivenIssueIds(updateAll)) {
            return assignGivenIssueIds(operatorToAssignId, ids, loggedOperatorType);
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
                .withPersonName(personName)
                .withPersonPesel(personPesel)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();
        return assignAllFilteredIssues(operatorToAssignId, loggedOperatorType, criteria);
    }

    @PATCH
    @Path("unassignment")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Usunięcie przypisanego operatora dla wybranych lub wszystkich zleceń, które spełniają przekazane filtry")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response unassign(@HeaderParam(AUTHORIZATION) String token,
                             @ApiParam(required = true, value = "Identyfikatory zleceń, którym usunięte zostanie przypisanie") IdsDto ids,
                             @ApiParam(name = "operatorId", value = "Identyfikatory operatorów do których przypisane są zlecenia")
                             @QueryParam("operatorId") List<Long> operatorIds,
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
                             @ApiParam(name = "state", value = "Status zlecenia")
                             @QueryParam("state") List<String> states,
                             @ApiParam(name = "tagId", value = "Identyfikator etykiety przypisanej do zlecenia")
                             @QueryParam("tagId") List<Long> tagIds,
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
                                     value = "Określa czy wszystkim zleceniom spełniającym warunki powinno zostać usunięte przypisanie. Wartość true ignoruje parametr ids.")
                             @QueryParam("updateAll") Boolean updateAll) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);

        if (shouldAssignGivenIssueIds(updateAll)) {
            return assignGivenIssueIds(null, ids, loggedOperatorType);
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
                .withPersonName(personName)
                .withPersonPesel(personPesel)
                .withContactPhone(contactPhone)
                .withContactEmail(contactEmail)
                .withContactName(contactName)
                .build();

        return assignAllFilteredIssues(null, loggedOperatorType, criteria);
    }

    @GET
    @Path("types")
    @Secured(ADMIN)
    @ApiOperation("Pobieranie listy wszystkich typów operatorów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<OperatorTypeDto> findAllOperatorTypes() {
        return operatorTypeService.findAllTypes();
    }

    @GET
    @Secured({ADMIN})
    @ApiOperation("Pobranie stronicowanej listy operatorów. Operatorów można filtrować po loginie oraz imieniu, nazwisku lub imieniu i nazwisku razem oddzielonymi spacją tego użytkownika.")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<OperatorDto> findAllOperators(@HeaderParam(AUTHORIZATION) String token,
                                              @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                              @QueryParam("firstResult") int firstResult,
                                              @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                              @QueryParam("maxResults") int maxResults,
                                              @ApiParam(name = "searchText", value = "Wyszukiwany ciag znaków")
                                              @QueryParam("searchText") String searchText) {
        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        return operatorService.findAllOperatorsFilterByNameAndLogin(firstResult, maxResults, searchText, loggedUserId);
    }

    @PATCH
    @Path("password/{operatorId}")
    @Secured(ADMIN)
    @ApiOperation("Resetuje hasło użytkownika z wymuszeniem zmiany po ponownym zalogowaniu")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response resetPassword(@ApiParam(name = "operatorId", value = "Identyfikator operatora")
                                  @PathParam("operatorId") long operatorId,
                                  @ApiParam(value = "Nowe hasło", required = true) ChangePasswordRequest passwordRequest) {
        operatorValidator.checkIfOperatorExists(operatorId);
        operatorValidator.validateChangePassword(passwordRequest);
        operatorService.changePassword(operatorId, passwordRequest.getPassword(), true);
        return Response.accepted().build();
    }

    @PATCH
    @Path("password/change")
    @Secured(all = true)
    @ApiOperation("Zmienia hasło zalogowanego użytkownika jeżeli został do tego zmuszony")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response changePassword(@HeaderParam(AUTHORIZATION) String token,
                                   @ApiParam(value = "Nowe hasło", required = true) ChangePasswordRequest passwordRequest) {
        operatorValidator.validateChangePassword(passwordRequest);
        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        operatorValidator.validateOperatorForcedToChangePassword(loggedUserId);
        operatorService.changePassword(loggedUserId, passwordRequest.getPassword(), false);
        return Response.accepted().build();
    }

    @GET
    @Path("fields")
    @Secured({ADMIN, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobiera stronicowana listę aktywnych operatorów w roli windykatora terenowego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<OperatorDto> findActiveFieldDebtCollectors(@ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                           @QueryParam("firstResult") int firstResult,
                                                           @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                           @QueryParam("maxResults") int maxResults) {
        return operatorService.findActiveFieldDebtCollectors(firstResult, maxResults);
    }

    private Response assignAllFilteredIssues(final Long operatorToAssignId, final OperatorType loggedOperatorType, final IssuesSearchCriteria criteria) {
        issueService.updateIssuesAssignment(operatorToAssignId, loggedOperatorType, criteria);
        return Response.accepted().build();
    }

    private Response assignGivenIssueIds(final Long operatorToAssignId, final IdsDto ids, final OperatorType loggedOperatorType) {
        issueValidator.validateManagedIssues(loggedOperatorType, ids);

        issueService.updateIssuesAssignment(operatorToAssignId, ids.getIds());
        return Response.accepted().build();
    }

}
