package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.common.prop.AlwinProperties;
import com.codersteam.alwin.common.search.criteria.ActivitiesSearchCriteria;
import com.codersteam.alwin.common.sort.ActivitySortField;
import com.codersteam.alwin.common.sort.SortOrder;
import com.codersteam.alwin.core.api.model.activity.*;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;
import com.codersteam.alwin.core.api.service.activity.ActivityDetailPropertyService;
import com.codersteam.alwin.core.api.service.activity.ActivityService;
import com.codersteam.alwin.core.api.service.activity.ActivityTypeService;
import com.codersteam.alwin.core.api.service.activity.DefaultIssueActivityService;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.dms.DmsClient;
import com.codersteam.alwin.dms.model.DocumentDto;
import com.codersteam.alwin.dms.model.SendDocumentDto;
import com.codersteam.alwin.parameters.DateParam;
import com.codersteam.alwin.validator.ActivityValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.codersteam.alwin.common.prop.AlwinPropertyKey.DMS_DOCUMENT_URL;
import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.EXECUTED;
import static com.codersteam.alwin.core.api.model.activity.ActivityStateDto.PLANNED;
import static com.codersteam.alwin.core.api.model.user.OperatorType.ADMIN;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.model.search.ActivitySearchCriteriaBuilder.aSearchCriteria;
import static java.lang.String.format;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Path("/activities")
@Api("/activities")
@Produces(APPLICATION_JSON)
public class ActivityResource {

    private static final String ALWIN_AUTHOR = "ALWIN";
    private ActivityService activityService;
    private ActivityTypeService activityTypeService;
    private ActivityValidator activityValidator;
    private JwtService jwtService;
    private DefaultIssueActivityService defaultIssueActivityService;
    private ActivityDetailPropertyService activityDetailPropertyService;
    private DmsClient dmsClient;
    private AlwinProperties alwinProperties;

    public ActivityResource() {
    }

    @Inject
    public ActivityResource(final ActivityService activityService, final ActivityTypeService activityTypeService, final ActivityValidator activityValidator,
                            final JwtService jwtService, final DefaultIssueActivityService defaultIssueActivityService, final
                            ActivityDetailPropertyService activityDetailPropertyService, DmsClient dmsClient, final AlwinProperties alwinProperties) {
        this.activityService = activityService;
        this.activityTypeService = activityTypeService;
        this.activityValidator = activityValidator;
        this.jwtService = jwtService;
        this.defaultIssueActivityService = defaultIssueActivityService;
        this.activityDetailPropertyService = activityDetailPropertyService;
        this.dmsClient = dmsClient;
        this.alwinProperties = alwinProperties;
    }

    @GET
    @Path("issue/{issueId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie stronicowanej listy wszystkich czynności windykacyjnych dla danego zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<ActivityDto> findAllActivitiesForIssue(@ApiParam(name = "issueId", value = "Identyfikator zlecenia")
                                                       @PathParam("issueId") long issueId,
                                                       @ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                       @QueryParam("firstResult") int firstResult,
                                                       @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                       @QueryParam("maxResults") int maxResults,
                                                       @ApiParam(name = "operatorId", value = "Identyfikator operatora do którego przypisana jest czynność")
                                                       @QueryParam("operatorId") Long operatorId,
                                                       @ApiParam(name = "companyId", value = "Identyfikator klienta dla którego chcemy pobrać wszystkie czynności ze wszystkich jego zleceń")
                                                       @QueryParam("companyId") Long companyId,
                                                       @ApiParam(name = "startCreationDate", value = "Początek okresu, w którym zostały utworzone czynności")
                                                       @QueryParam("startCreationDate") DateParam startCreationDate,
                                                       @ApiParam(name = "endCreationDate", value = "Koniec okresu, w którym zostały utworzone czynności")
                                                       @QueryParam("endCreationDate") DateParam endCreationDate,
                                                       @ApiParam(name = "type", value = "Typ czynności")
                                                       @QueryParam("type") String type,
                                                       @ApiParam(name = "remark", value = "Ciąg znaków znajdujący się w komentarzu do czynności")
                                                       @QueryParam("remark") String remark,
                                                       @ApiParam(name = "state", value = "Lista statusów czynności")
                                                       @QueryParam("state") List<String> states,
                                                       @QueryParam("sortField") ActivitySortField sortField,
                                                       @QueryParam("sortOrder") @DefaultValue("ASC") SortOrder sortOrder,
                                                       @ApiParam(name = "issueType", value = "Typ zadania, z którego czynność pochodzi")
                                                       @QueryParam("issueType") IssueTypeName issueType) {
        ActivitiesSearchCriteria searchCriteria = aSearchCriteria()
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withOperatorId(operatorId)
                .withCompanyId(companyId)
                .withStartCreationDate(startCreationDate)
                .withEndCreationDate(endCreationDate)
                .withType(type)
                .withRemark(remark)
                .withStates(states)
                .withIssueType(issueType)
                .build();

        Map<ActivitySortField, SortOrder> sortCriteria = sortField != null ? Collections.singletonMap(sortField, sortOrder) : Collections.emptyMap();

        return activityService.findAllIssueActivities(issueId, searchCriteria, sortCriteria);
    }

    @POST
    @Path("issue/executed")
    @Secured(all = true)
    @ApiOperation("Dodanie wykonanej czynności windykacyjnej dla zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createExecutedActivityForIssue(@HeaderParam(AUTHORIZATION) String token,
                                                   @ApiParam(required = true, value = "Wykonana czynność") ActivityDto activity) {
        activity.setState(EXECUTED);
        activity.setOperator(getLoggedOperatorDto(token));
        return createActivity(activity);
    }

    private OperatorDto getLoggedOperatorDto(final String token) {
        final Long loggedUserId = jwtService.getLoggedOperatorId(token);
        return new OperatorDto(loggedUserId);
    }

    @POST
    @Path("issue/planned")
    @Secured(all = true)
    @ApiOperation("Dodanie zaplanowanej czynności windykacyjnej dla zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createPlannedActivityForIssue(@HeaderParam(AUTHORIZATION) String token,
                                                  @ApiParam(required = true, value = "Zaplanowana czynność") ActivityDto activity) {
        activity.setState(PLANNED);
        activity.setOperator(null);
        return createActivity(activity);
    }

    @PATCH
    @Path("issue/executed")
    @Secured(all = true)
    @ApiOperation("Uaktualnienie danych czynności windykacyjnej oraz oznaczenie wykonania")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateActivityAndSetExecuted(@HeaderParam(AUTHORIZATION) String token,
                                                 @ApiParam(required = true, value = "Wykonana czynność") ActivityDto activity) {
        activity.setState(EXECUTED);
        activity.setOperator(getLoggedOperatorDto(token));
        return updateActivity(activity);
    }

    @GET
    @Path("types")
    @Secured(all = true)
    @ApiOperation("Pobieranie typów czynności windykacyjnych dla konkretnego typu zlecenia, lub wszystkich jeżeli typ nie został podany")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findAllActivityTypes(@ApiParam(name = "issueType", value = "Typ zlecenia windykacyjnego")
                                         @QueryParam("issueType") String issueType,
                                         @ApiParam(name = "mayHaveDeclarations", value = "Czy typ czynności może posiadać deklaracje")
                                         @QueryParam("mayHaveDeclarations") Boolean mayHaveDeclarations) {
        IssueTypeName issueTypeName = null;
        if (isNotEmpty(issueType)) {
            issueTypeName = activityValidator.validateIssueType(issueType);
        }
        return Response.ok(activityTypeService.findActivityTypes(issueTypeName, mayHaveDeclarations)).build();
    }

    @GET
    @Path("types/groupedByState")
    @Secured(all = true)
    @ApiOperation("Pobieranie typów czynności windykacyjnych zgrupowanych po statusie (planowana/wykonana) dla konkretnego typu zlecenia, " +
            "lub wszystkich jeżeli typ nie został podany.")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findActivityTypesGroupedByState(@ApiParam(name = "issueType", value = "Typ zlecenia windykacyjnego")
                                                    @QueryParam("issueType") String issueType,
                                                    @ApiParam(name = "mayHaveDeclarations", value = "Czy typ czynności może posiadać deklaracje")
                                                    @QueryParam("mayHaveDeclarations") Boolean mayHaveDeclarations) {
        if (isNotEmpty(issueType)) {
            final IssueTypeName issueTypeName = activityValidator.validateIssueType(issueType);
            return Response.ok(activityTypeService.findActivityTypeByIssueTypeGroupedByState(issueTypeName, mayHaveDeclarations)).build();
        }
        return Response.ok(activityTypeService.findAllActivityTypesGroupedByState(mayHaveDeclarations)).build();
    }

    @GET
    @Path("statuses")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich statusów dla czynności windykacyjnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ActivityStateDto> findAllActivityStatuses() {
        return ActivityStateDto.STATUSES;
    }

    @PATCH
    @Path("managed/executed")
    @Secured(PHONE_DEBT_COLLECTOR_MANAGER)
    @ApiOperation("Uaktualnienie danych czynności windykacyjnej oraz oznaczenie wykonania")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateManagedActivityAndSetExecuted(@HeaderParam(AUTHORIZATION) String token,
                                                        @ApiParam(required = true, value = "Wykonana czynność") ActivityDto activity) {
        activity.setState(EXECUTED);
        return updateManagedIssueActivity(token, activity);
    }

    @POST
    @Path("managed/executed")
    @Secured(PHONE_DEBT_COLLECTOR_MANAGER)
    @ApiOperation("Dodanie wykonanej czynności windykacyjnej dla zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createExecutedManagedActivityForIssue(@HeaderParam(AUTHORIZATION) String token,
                                                          @ApiParam(required = true, value = "Wykonana czynność") ActivityDto activity) {
        activity.setState(EXECUTED);
        return createManagedActivity(token, activity);
    }

    @POST
    @Path("managed/planned")
    @Secured(PHONE_DEBT_COLLECTOR_MANAGER)
    @ApiOperation("Dodanie zaplanowanej czynności windykacyjnej dla zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createPlannedManagedActivityForIssue(@HeaderParam(AUTHORIZATION) String token,
                                                         @ApiParam(required = true, value = "Zaplanowana czynność") ActivityDto activity) {
        activity.setState(PLANNED);
        return createManagedActivity(token, activity);
    }

    @GET
    @Path("default")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich domyślnych czynności windykacyjnych, zgrupowane według typu zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<IssueTypeWithDefaultActivities> findAllDefaultActivities() {
        return defaultIssueActivityService.findAllDefaultIssueActivities();
    }

    @GET
    @Path("properties")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich cech dodatkowych zdarzeń dla czynności windykacyjnych")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ActivityDetailPropertyDto> findAllActivityDetailProperties() {
        return activityDetailPropertyService.findAllActivityDetailProperties();
    }

    @PATCH
    @Path("default/{defaultIssueActivityId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER})
    @ApiOperation("Uaktualnienie domyślnej czynności zlecenia windykacyjnego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateDefaultActivity(@ApiParam(name = "defaultIssueActivityId", value = "Identyfikator domyślnej czynności")
                                          @PathParam("defaultIssueActivityId") final long defaultIssueActivityId,
                                          @ApiParam(required = true, value = "Domyślna czynność zlecenia windykacyjnego") DefaultIssueActivityDto defaultIssueActivityDto) {
        activityService.updateDefaultIssueActivity(defaultIssueActivityId, defaultIssueActivityDto);
        return Response.accepted().build();
    }

    @PATCH
    @Path("types/{activityTypeId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER})
    @ApiOperation("Uaktualnienie typu czynności zlecenia windykacyjnego wraz z jego cechami. " +
            "W przypadku przekazania detailProperties na główym poziome DTO wybrane cechy są aktualizowane wraz z danym słownikowymi")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateActivityType(@ApiParam(name = "activityTypeId", value = "Identyfikator typu czynności")
                                       @PathParam("activityTypeId") final long activityTypeId,
                                       @ApiParam(required = true, value = "Typ czynności zlecenia windykacyjnego") ActivityTypeWithDetailPropertiesDto activityTypeDto) {
        activityValidator.validate(activityTypeDto.getDetailProperties());
        activityService.updateActivityType(activityTypeId, activityTypeDto);
        return Response.accepted().build();
    }

    @GET
    @Path("issue/{issueId}/oldest")
    @Secured(all = true)
    @ApiOperation("Pobieranie najstarszej zaplanowanej czynności 'telefon wychodzący' dla zlecenia, pusta odpowiedź ze statusem 204 w przeciwnym przypadku")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findOldestPlannedActivityForIssue(@ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") long issueId) {
        final Optional<ActivityDto> activity = activityService.findOldestPlannedActivityForIssue(issueId);
        return activity.map(this::buildOkResponseWithEntity).orElse(Response.noContent().build());
    }

    @POST
    @Path("attachment")
    @Secured(all = true)
    @ApiOperation("Dodanie załącznika dla czynności")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response addActivityAttachment(@HeaderParam(AUTHORIZATION) String token,
                                          @ApiParam(required = true, value = "Dane załącznika dla czynności windykacyjnej") ActivityAttachmentDto activityAttachment) {
        final SendDocumentDto sendDocumentDto = new SendDocumentDto();
        sendDocumentDto.setAuthor(ALWIN_AUTHOR);
        sendDocumentDto.setComment(ALWIN_AUTHOR);
        sendDocumentDto.setFileName(activityAttachment.getFilename());
        sendDocumentDto.setContentBase64(activityAttachment.getUploadedInputStream());
        final DocumentDto documentDto = dmsClient.sendDocument(sendDocumentDto);
        return Response.ok(format("\"%s\"", alwinProperties.getProperty(DMS_DOCUMENT_URL) + documentDto.getDocumentHash())).build();
    }

    private Response buildOkResponseWithEntity(final ActivityDto activityDto) {
        return Response.ok(activityDto).build();
    }

    private Response createActivity(final ActivityDto activity) {
        activityValidator.validate(activity);
        activityService.createNewActivityForIssue(activity);
        return Response.created(null).build();
    }

    private Response createManagedActivity(final String token, final ActivityDto activity) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        activityValidator.validateManagedActivity(activity, loggedOperatorType);
        activityService.createNewActivityForIssue(activity);
        return Response.created(null).build();
    }

    private Response updateActivity(final ActivityDto activity) {
        activityValidator.validate(activity);
        activityService.updateActivity(activity, true);
        return Response.accepted().build();
    }

    private Response updateManagedIssueActivity(final String token, final ActivityDto activity) {
        final OperatorType loggedOperatorType = jwtService.getLoggedOperatorType(token);
        activityValidator.validateManagedActivity(activity, loggedOperatorType);
        activityService.updateActivity(activity, true);
        return Response.accepted().build();
    }
}
