package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.customer.ContactPersonDto;
import com.codersteam.alwin.core.api.model.customer.DocTypeDto;
import com.codersteam.alwin.core.api.model.customer.MaritalStatusDto;
import com.codersteam.alwin.core.api.model.customer.PersonDto;
import com.codersteam.alwin.core.api.service.issue.PersonService;
import com.codersteam.alwin.validator.PersonValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/persons")
@Api("/persons")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class PersonResource {
    private static Logger logger = LoggerFactory.getLogger(PersonResource.class.getName());

    private PersonService personService;
    private PersonValidator personValidator;

    public PersonResource() {
    }

    @Inject
    public PersonResource(PersonService personService,
                          PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @PATCH
    @Path("/{personId}/company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Oznacznie lub odznaczenie osoby uprawnionej firmy jako osoba do kontaktu")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateContactPerson(@ApiParam(name = "personId", value = "Identyfikator osoby") @PathParam("personId") long personId,
                                        @ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") long companyId,
                                        @ApiParam(required = true, value = "Czy osoba do kontaku") ContactPersonDto contactPerson) {
        personService.setContactPerson(personId, companyId, contactPerson.isContactPerson());
        return Response.accepted().build();
    }

    @POST
    @Path("/company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Dodanie osoby uprawnionej firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createPerson(@ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") long companyId,
                                 @ApiParam(required = true, value = "Dane osoby") PersonDto person) {
        final Map<String, String> validationResult = personValidator.validatePerson(person);
        if (!validationResult.isEmpty()) {
            return Response.status(BAD_REQUEST).entity(validationResult).build();
        }
        personService.createPerson(companyId, person);
        return Response.created(null).build();
    }

    @GET
    @Path("maritalStatuses")
    @Secured(all = true)
    @ApiOperation("Pobieranie statusów cywilny")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<MaritalStatusDto> getMaritalStatuses() {
        return MaritalStatusDto.ALL;
    }

    @GET
    @Path("documentTypes")
    @Secured(all = true)
    @ApiOperation("Pobieranie typów dokumentu tożsamości")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<DocTypeDto> getDocumentTypes() {
        return DocTypeDto.ALL;
    }

    @GET
    @Path("/company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie osób uprawnionych firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<PersonDto> getCompanyPersons(@PathParam("companyId") long companyId) {
        return personService.getCompanyPersonsByCompanyId(companyId);
    }
}