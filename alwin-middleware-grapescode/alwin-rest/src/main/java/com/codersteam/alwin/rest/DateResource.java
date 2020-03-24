package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.date.DateDto;
import com.codersteam.alwin.core.api.service.DateProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/dates")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/dates")
public class DateResource {

    private DateProvider dateProvider;

    public DateResource() {
    }

    @Inject
    public DateResource(final DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }


    @GET
    @Path("/previousWorkingDay")
    @Secured(all = true)
    @ApiOperation("Pobieranie początku poprzedniego dnia roboczego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public DateDto getStartOfPreviousWorkingDay() {
        final Date startOfPreviousWorkingDay = dateProvider.getStartOfPreviousWorkingDay();
        return new DateDto(startOfPreviousWorkingDay);
    }

}
