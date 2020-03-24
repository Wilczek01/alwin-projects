package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.comparator.AddressDtoComparator;
import com.codersteam.alwin.core.api.model.customer.AddressDto;
import com.codersteam.alwin.core.api.model.customer.AddressTypeDto;
import com.codersteam.alwin.core.api.service.customer.AddressService;
import com.codersteam.alwin.validator.AddressValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static com.codersteam.alwin.core.api.model.customer.AddressTypeDto.ALL_ADDRESS_TYPES;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/addresses")
@Api("/addresses")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AddressResource {

    private AddressService addressService;
    private AddressValidator addressValidator;

    public AddressResource() {
    }

    @Inject
    public AddressResource(final AddressService addressService, final AddressValidator addressValidator) {
        this.addressService = addressService;
        this.addressValidator = addressValidator;
    }

    @GET
    @Path("company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich adresów dla danej firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<AddressDto> findAllAddressesForCompany(@ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") final long companyId) {
        return addressService.findAllAddressesForCompany(companyId)
                .stream()
                .sorted(new AddressDtoComparator())
                .collect(Collectors.toList());
    }

    @POST
    @Path("company/{companyId}")
    @Secured(all = true)
    @ApiOperation("Dodanie nowego adresu dla firmy")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewAddressForCompany(@ApiParam(name = "companyId", value = "Identyfikator firmy") @PathParam("companyId") final long companyId,
                                               @ApiParam(required = true, value = "Address do utworzenia") final AddressDto address) {
        addressService.createNewAddressForCompany(companyId, address);
        return Response.created(null).build();
    }

    @GET
    @Path("person/{personId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich adresów dla danej osoby")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findAllAddressesForPerson(@ApiParam(name = "personId", value = "Identyfikator osoby") @PathParam("personId") final long personId) {
        final List<AddressDto> allAddressesForPerson = addressService.findAllAddressesForPerson(personId);
        allAddressesForPerson.sort(new AddressDtoComparator());
        return Response.ok(allAddressesForPerson).build();
    }

    @POST
    @Path("person/{personId}")
    @Secured(all = true)
    @ApiOperation("Dodanie nowego adresu dla osoby")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNewAddressForPerson(@ApiParam(name = "personId", value = "Identyfikator osoby") @PathParam("personId") final long personId,
                                              @ApiParam(required = true, value = "Address do utworzenia") final AddressDto address) {
        addressService.createNewAddressForPerson(personId, address);
        return Response.created(null).build();
    }

    @PATCH
    @Path("/{addressId}")
    @Secured(all = true)
    @ApiOperation("Edycja addresu")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateAddress(@ApiParam(name = "addressId", value = "Identyfikator adresu") @PathParam("addressId") final long addressId,
                                  @ApiParam(required = true, value = "Address do edycji") final AddressDto address) {
        address.setId(addressId);
        addressValidator.validate(address);
        addressService.updateAddress(address);
        return Response.accepted().build();
    }

    @GET
    @Path("types")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich typów addresów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<AddressTypeDto> findAllAddressesTypes() {
        return ALL_ADDRESS_TYPES;
    }
}
