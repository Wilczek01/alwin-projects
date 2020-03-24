package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.tag.TagDto;
import com.codersteam.alwin.core.api.model.tag.TagIconDto;
import com.codersteam.alwin.core.api.model.tag.TagInputDto;
import com.codersteam.alwin.core.api.service.issue.TagIconService;
import com.codersteam.alwin.core.api.service.issue.TagService;
import com.codersteam.alwin.validator.TagValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.codersteam.alwin.core.api.model.user.OperatorType.*;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/tags")
@Api("/tags")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class TagResource {

    private TagService tagService;
    private TagValidator tagValidator;
    private TagIconService tagIconService;

    public TagResource() {
    }

    @Inject
    public TagResource(final TagService tagService, final TagValidator tagValidator, final TagIconService tagIconService) {
        this.tagService = tagService;
        this.tagValidator = tagValidator;
        this.tagIconService = tagIconService;
    }

    @GET
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie wszystkich etykiet")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<TagDto> findAllTags() {
        return tagService.findAllTags();
    }

    @POST
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Tworzy nową etykietę")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewTag(final TagInputDto tag) {
        tagValidator.validate(tag);
        tagService.createTag(tag);
        return Response.created(null).build();
    }

    @PATCH
    @Path("{tagId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Aktualizuje istniejącą etykietę")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateTag(@ApiParam(name = "tagId", value = "Identyfikator etykiety") @PathParam("tagId") long tagId, final TagInputDto tag) {
        tagValidator.validate(tagId, tag);
        tagService.updateTag(tagId, tag);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{tagId}")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Usuwa istniejącą etykietę")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deleteTag(@ApiParam(name = "tagId", value = "Identyfikator etykiety") @PathParam("tagId") long tagId) {
        tagValidator.checkIfTagExists(tagId);
        tagValidator.checkIfTagIsDeletable(tagId);
        tagService.deleteTag(tagId);
    }

    @GET
    @Path("/icons")
    @Secured({ADMIN, PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobieranie wszystkich symboli etykiet")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<TagIconDto> findAllTagIcons() {
        return tagIconService.findAllTags();
    }
}
