package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.holiday.HolidayDto;
import com.codersteam.alwin.core.api.model.holiday.HolidayInputDto;
import com.codersteam.alwin.core.api.service.holiday.HolidayService;
import com.codersteam.alwin.validator.HolidayValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.codersteam.alwin.core.api.model.user.OperatorType.*;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/holidays")
@Api("/holidays")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class HolidayResource {

    private HolidayService holidayService;
    private HolidayValidator holidayValidator;

    public HolidayResource() {
    }

    @Inject
    public HolidayResource(final HolidayService holidayService, final HolidayValidator holidayValidator) {
        this.holidayService = holidayService;
        this.holidayValidator = holidayValidator;
    }

    @GET
    @Path("{year}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie wszystkich świąt dla danego dnia, miesiąca lub roku")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<HolidayDto> findAllHolidays(@ApiParam(name = "day", value = "Dzień do sprawdzenia dni wolnych") @QueryParam("day") final Integer day,
                                            @ApiParam(name = "month", value = "Miesiąc do sprawdzenia dni wolnych") @QueryParam("month") final Integer month,
                                            @ApiParam(name = "year", value = "Rok do sprawdzenia dni wolnych") @PathParam("year") final int year,
                                            @ApiParam(name = "userId", value = "Identyfikator użytkownika") @QueryParam("userId") final Long userId) {
        holidayValidator.validate(day, month, year);
        final List<HolidayDto> holidays;
        if (month == null) {
            holidays = holidayService.findAllHolidaysPerYear(year, userId);
        } else if (day != null) {
            holidays = holidayService.findAllHolidaysPerDay(day, month, year, userId);
        } else {
            holidays = holidayService.findAllHolidaysPerMonth(month, year, userId);
        }
        return holidays;
    }

    @POST
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Tworzy nowy dzień wolny")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewHoliday(final HolidayInputDto holiday) {
        holidayValidator.validate(holiday);
        holidayService.createNewHoliday(holiday);
        return Response.created(null).build();
    }

    @PATCH
    @Path("{holidayId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Aktualizuje istniejący dzień wolny")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateHoliday(@ApiParam(name = "holidayId", value = "Identyfikator dnia wolnego") @PathParam("holidayId") final long holidayId,
                                  final HolidayInputDto holiday) {
        holidayValidator.checkIfHolidayExists(holidayId);
        holidayValidator.validate(holiday);
        holidayService.updateHoliday(holidayId, holiday);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{holidayId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Usuwa istniejący dzień wolny")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deleteHoliday(@ApiParam(name = "holidayId", value = "Identyfikator dnia wolnego") @PathParam("holidayId") final long holidayId) {
        holidayValidator.checkIfHolidayExists(holidayId);
        holidayService.deleteHoliday(holidayId);
    }
}
