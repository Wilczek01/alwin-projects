package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.scheduler.SchedulerBatchProcessType;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerBatchProcessTypeDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerConfigurationDto;
import com.codersteam.alwin.core.api.model.scheduler.SchedulerExecutionInfoDto;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerConfigurationService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerExecutionInfoService;
import com.codersteam.alwin.core.api.service.scheduler.SchedulerProcessExecutor;
import com.codersteam.alwin.exception.AlwinValidationException;
import com.codersteam.alwin.validator.SchedulerValidator;
import io.swagger.annotations.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.codersteam.alwin.core.api.model.user.OperatorType.ADMIN;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/schedulers")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/schedulers")
@Stateless  //FIXME Stateless użyty w resource
public class SchedulerExecutionResource {

    private SchedulerExecutionInfoService schedulerExecutionService;
    private SchedulerConfigurationService schedulerConfigurationService;
    private SchedulerProcessExecutor schedulerProcessExecutor;
    private SchedulerValidator schedulerValidator;

    public SchedulerExecutionResource() {
    }

    @Inject
    public SchedulerExecutionResource(final SchedulerExecutionInfoService schedulerExecutionService,
                                      final SchedulerConfigurationService schedulerConfigurationService,
                                      final SchedulerProcessExecutor schedulerProcessExecutor,
                                      final SchedulerValidator schedulerValidator) {
        this.schedulerExecutionService = schedulerExecutionService;
        this.schedulerConfigurationService = schedulerConfigurationService;
        this.schedulerProcessExecutor = schedulerProcessExecutor;
        this.schedulerValidator = schedulerValidator;
    }

    @GET
    @Secured(ADMIN)
    @ApiOperation("Pobieranie stronicowanych zadań cyklicznych psortowanych po dacie uruchomienia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<SchedulerExecutionInfoDto> findSchedulersExecution(@ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                                                   @QueryParam("firstResult") int firstResult,
                                                                   @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                                                   @QueryParam("maxResults") int maxResults) {
        return schedulerExecutionService.findSchedulersExecutions(firstResult, maxResults);
    }

    @GET
    @Path("/batchProcessTypes")
    @Secured(ADMIN)
    @ApiOperation("Pobieranie wszystkich typów grup zadań cyklicznych")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<SchedulerBatchProcessTypeDto> findAllBatchProcessTypes() {
        return schedulerExecutionService.findAllBatchProcessTypes();
    }

    @POST
    @Path("/batchProcessTypes/{batchProcess}/start")
    @Secured(ADMIN)
    @ApiOperation("Uruchamia groupę zadań cyklicznych")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public Response startBatchProcess(@ApiParam(name = "batchProcess", value = "Identyfikator grupy zadań cyklicznych")
                                      @PathParam("batchProcess") SchedulerBatchProcessType batchProcess) {
        try {
            schedulerProcessExecutor.processSchedulerTasksAsync(batchProcess, true);
            return Response.accepted().build();
        } catch (SchedulerProcessExecutor.TaskIsRunningValidationException e) {
            throw new AlwinValidationException(e.getMessage());
        }
    }

    @PUT
    @Path("/configurations/{batchProcess}/executionTime")
    @Secured(ADMIN)
    @ApiOperation("Zmiana czasu startu grupy zadań cyklicznych")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response changeTimeOfExecution(@ApiParam(name = "batchProcess", value = "Identyfikator grupy zadań cyklicznych")
                                          @PathParam("batchProcess") SchedulerBatchProcessType batchProcessType,
                                          @ApiParam(name = "hour", value = "Godzina rozpoczęcia zadania (0-23)")
                                          @QueryParam("hour") int hour) {
        schedulerValidator.validateTimeOfExecution(hour);
        schedulerConfigurationService.changeTimeOfExecution(batchProcessType, hour);
        return Response.accepted().build();
    }

    @GET
    @Path("/configurations")
    @Secured(ADMIN)
    @ApiOperation("Pobieranie wszystkich konfiguracji grupy zadań")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<SchedulerConfigurationDto> findAllScheduleConfigurations() {
        return schedulerConfigurationService.findAll();
    }
}
