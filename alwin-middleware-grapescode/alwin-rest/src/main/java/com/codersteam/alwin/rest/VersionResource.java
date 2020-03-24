package com.codersteam.alwin.rest;

import com.codersteam.alwin.config.Property;
import com.codersteam.alwin.core.api.model.version.AppVersionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/healthCheck")
@Produces("application/json")
@Api("/healthCheck")
public class VersionResource {

    @Inject
    @Property("version.value")
    private String version;

    @GET
    @ApiOperation(value = "Wersja aplikacji oraz health check", response = AppVersionDto.class)
    @ApiResponses(@ApiResponse(code = 200, message = "Aktualna wersja aplikacji"))
    public AppVersionDto getAppVersion() {
        return new AppVersionDto(version);
    }
}