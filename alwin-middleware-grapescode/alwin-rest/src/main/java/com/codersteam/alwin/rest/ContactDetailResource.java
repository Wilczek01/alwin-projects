package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.customer.ContactDetailDto;
import com.codersteam.alwin.core.api.model.customer.ContactStateDto;
import com.codersteam.alwin.core.api.model.customer.ContactTypeDto;
import com.codersteam.alwin.core.api.model.customer.PhoneNumberDto;
import com.codersteam.alwin.core.api.service.customer.ContactDetailService;
import com.codersteam.alwin.core.api.service.customer.PhoneNumberService;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.codersteam.alwin.comparator.ContactDetailDtoComparatorProvider.CONTACT_DETAIL_DTO_COMPARATOR;
import static com.codersteam.alwin.core.api.model.customer.ContactStateDto.ALL_CONTACT_STATES_WITH_ORDER;
import static com.codersteam.alwin.core.api.model.customer.ContactTypeDto.ALL_CONTACT_TYPES_WITH_ORDER;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/contactDetails")
@Api("/contactDetails")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class ContactDetailResource {

    private ContactDetailService contactDetailService;
    private PhoneNumberService phoneNumberService;

    public ContactDetailResource() {
    }

    @Inject
    public ContactDetailResource(final ContactDetailService contactDetailService, final PhoneNumberService phoneNumberService) {
        this.contactDetailService = contactDetailService;
        this.phoneNumberService = phoneNumberService;
    }

    @GET
    @Path("company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich kontaktów dla danej firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContactDetailDto> findAllContactDetailsForCompany(@ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") final long companyId) {
        final List<ContactDetailDto> allContactDetailsForCompany = contactDetailService.findAllContactDetailsForCompany(companyId);
        allContactDetailsForCompany.sort(CONTACT_DETAIL_DTO_COMPARATOR);
        return allContactDetailsForCompany;
    }

    @POST
    @Path("company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Dodanie nowego kontaktu dla firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewContactDetailsForCompany(@ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") final long companyId,
                                                      @ApiParam(required = true, value = "Kontakt do utworzenia") final ContactDetailDto contactDetail) {
        contactDetailService.createNewContactDetailForCompany(companyId, contactDetail);
        return Response.created(null).build();
    }

    @GET
    @Path("person/{personId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich kontaktów dla danej osoby")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContactDetailDto> findAllContactDetailsForPerson(@ApiParam(name = "personId", value = "Identyfikator osoby") @PathParam("personId") final long personId) {
        final List<ContactDetailDto> allContactDetailsForPerson = contactDetailService.findAllContactDetailsForPerson(personId);
        allContactDetailsForPerson.sort(CONTACT_DETAIL_DTO_COMPARATOR);
        return allContactDetailsForPerson;
    }

    @POST
    @Path("person/{personId}")
    @Secured(all = true)
    @ApiOperation("Dodanie nowego kontaktu dla osoby")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewContactDetailsForPerson(@ApiParam(name = "personId", value = "Identyfikator osoby") @PathParam("personId") final long personId,
                                                     @ApiParam(required = true, value = "Kontakt do utworzenia") final ContactDetailDto contactDetailDto) {
        contactDetailService.createNewContactDetailForPerson(personId, contactDetailDto);
        return Response.created(null).build();
    }

    @PATCH
    @Path("/{contactDetailId}")
    @Secured(all = true)
    @ApiOperation("Edycja kontaktu dla firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateContactDetail(@ApiParam(name = "contactDetailId", value = "Identyfikator kontaktu") @PathParam("contactDetailId") final long contactDetailId,
                                        @ApiParam(required = true, value = "Kontakt do edycji") final ContactDetailDto contactDetail) {
        contactDetail.setId(contactDetailId);
        contactDetailService.updateContactDetail(contactDetail);
        return Response.accepted().build();
    }

    @GET
    @Path("types")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich typów kontaktów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContactTypeDto> findAllContactDetailsTypes() {
        return ALL_CONTACT_TYPES_WITH_ORDER;
    }

    @GET
    @Path("states")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich statusów kontaktów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContactStateDto> findAllContactDetailsStates() {
        return ALL_CONTACT_STATES_WITH_ORDER;
    }

    @GET
    @Path("suggestedPhoneNumbers/{companyId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich kontaktów dla danej osoby")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<PhoneNumberDto> findSuggestedPhoneNumbers(@ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") final long companyId) {
        return phoneNumberService.findSuggestedPhoneNumbers(companyId);
    }
}
