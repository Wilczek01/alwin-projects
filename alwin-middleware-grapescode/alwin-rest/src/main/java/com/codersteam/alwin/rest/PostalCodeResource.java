package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.postal.PostalCodeDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeInputDto;
import com.codersteam.alwin.core.api.postalcode.PostalCodeService;
import com.codersteam.alwin.validator.PostalCodeValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.codersteam.alwin.core.api.model.user.OperatorType.ADMIN;
import static com.codersteam.alwin.core.api.model.user.OperatorType.DIRECT_DEBT_COLLECTION_MANAGER;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/postalCodes")
@Api("/postalCodes")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class PostalCodeResource {

    private PostalCodeService postalCodeService;
    private PostalCodeValidator postalCodeValidator;

    public PostalCodeResource() {
    }

    @Inject
    public PostalCodeResource(final PostalCodeService postalCodeService, final PostalCodeValidator postalCodeValidator) {
        this.postalCodeService = postalCodeService;
        this.postalCodeValidator = postalCodeValidator;
    }

    @GET
    @Secured({ADMIN, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie wszystkich masek kodów pocztowych")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<PostalCodeDto> findAllPostalCodes(@ApiParam(name = "mask", value = "Maska kodu pocztowego")
                                                  @QueryParam("mask") String mask) {
        postalCodeValidator.validate(mask);
        return postalCodeService.findPostalCodesByMask(mask);
    }

    @POST
    @Secured({ADMIN, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Tworzy nową maskę kodu pocztowego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewPostalCode(final PostalCodeInputDto postalCode) {
        postalCodeValidator.validate(postalCode);
        postalCodeService.createPostalCode(postalCode);
        return Response.created(null).build();
    }

    @PATCH
    @Path("{postalCodeId}")
    @Secured({ADMIN, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Aktualizuje istniejącą maskę kodu pocztowego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updatePostalCode(@ApiParam(name = "postalCodeId", value = "Identyfikator maski kodu pocztowego")
                                     @PathParam("postalCodeId") final long postalCodeId,
                                     final PostalCodeInputDto postalCode) {
        postalCodeValidator.validate(postalCodeId, postalCode);
        postalCodeService.updatePostalCode(postalCodeId, postalCode);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{postalCodeId}")
    @Secured({ADMIN, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Usuwa istniejącą maskę kodu pocztowego")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deletePostalCode(@ApiParam(name = "postalCodeId", value = "Identyfikator maski kodu pocztowego") @PathParam("postalCodeId") final long postalCodeId) {
        postalCodeValidator.validate(postalCodeId);
        postalCodeService.deletePostalCode(postalCodeId);
    }
}
