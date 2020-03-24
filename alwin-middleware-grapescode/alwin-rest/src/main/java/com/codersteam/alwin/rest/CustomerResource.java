package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.core.api.model.customer.*;
import com.codersteam.alwin.core.api.model.issue.SegmentDto;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.customer.CustomerService;
import com.codersteam.alwin.validator.CustomerValidator;
import com.codersteam.alwin.validator.OperatorValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codersteam.alwin.core.api.model.user.OperatorType.DIRECT_DEBT_COLLECTION_MANAGER;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/customers")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/customers")
public class CustomerResource {

    private CustomerService customerService;
    private JwtService jwtService;
    private CustomerValidator customerValidator;
    private OperatorValidator operatorValidator;

    public CustomerResource() {
    }

    @Inject
    public CustomerResource(final CustomerService customerService, final JwtService jwtService, final CustomerValidator customerValidator, final OperatorValidator operatorValidator) {
        this.customerService = customerService;
        this.jwtService = jwtService;
        this.customerValidator = customerValidator;
        this.operatorValidator = operatorValidator;
    }

    @POST
    @Path("block/{extCompanyId}/contract")
    @Secured(all = true)
    @ApiOperation("Wyłączenie kontraktu klienta z obsługi w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response blockCustomerContract(@HeaderParam(AUTHORIZATION) final String token,
                                          @ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                          @PathParam("extCompanyId") final long extCompanyId,
                                          @ApiParam(name = "contractNo", value = "Numer blokowanego kontraktu")
                                          @QueryParam("contractNo") final String contractNo,
                                          @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final ContractOutOfServiceInputDto contract) {
        customerValidator.validate(extCompanyId, contractNo, contract);
        customerService.blockCustomerContract(contract, extCompanyId, contractNo, jwtService.getLoggedOperatorId(token));
        return Response.created(null).build();
    }

    @POST
    @Path("block/{extCompanyId}/contract/formalDebtCollection")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Wyłączenie kontraktu klienta z obsługi w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response blockFormalDebtCollectionContract(@HeaderParam(AUTHORIZATION) final String token,
                                                      @ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                      @PathParam("extCompanyId") final long extCompanyId,
                                                      @ApiParam(name = "contractNo", value = "Numer blokowanego kontraktu")
                                                      @QueryParam("contractNo") final String contractNo,
                                                      @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final FormalDebtCollectionContractOutOfServiceInputDto contract) {
        customerValidator.validateFormalDebtCollectionContract(extCompanyId, contractNo, contract);
        customerService.blockFormalDebtCollectionContract(contract, extCompanyId, contractNo, jwtService.getLoggedOperatorId(token));
        return Response.created(null).build();
    }

    @POST
    @Path("block/{extCompanyId}")
    @Secured(all = true)
    @ApiOperation("Wyłączenie klienta z obsługi w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response blockCustomer(@HeaderParam(AUTHORIZATION) final String token,
                                  @ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                  @PathParam("extCompanyId") final long extCompanyId,
                                  @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final CustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        customerValidator.validate(extCompanyId, customerOutOfServiceInputDto);
        customerService.blockCustomer(customerOutOfServiceInputDto, extCompanyId, jwtService.getLoggedOperatorId(token));
        return Response.created(null).build();
    }

    @POST
    @Path("block/{extCompanyId}/formalDebtCollection")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Wyłączenie klienta z obsługi w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response blockFormalDebtCollectionCustomer(@HeaderParam(AUTHORIZATION) final String token,
                                                      @ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                      @PathParam("extCompanyId") final long extCompanyId,
                                                      @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final FormalDebtCollectionCustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        customerValidator.validateFormalDebtCollection(extCompanyId, customerOutOfServiceInputDto);
        customerService.blockFormalDebtCollectionCustomer(customerOutOfServiceInputDto, extCompanyId, jwtService.getLoggedOperatorId(token));
        return Response.created(null).build();
    }

    @GET
    @Path("blocked/{extCompanyId}")
    @Secured(all = true)
    @ApiOperation("Pobranie listy blokad dla klienta w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<CustomerOutOfServiceDto> getCustomersOutOfService(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                                  @PathParam("extCompanyId") final long extCompanyId,
                                                                  @ApiParam(name = "active", value = "Czy pobierać tylko aktywne blokady")
                                                                  @QueryParam("active") final Boolean active) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        return customerService.findCustomersOutOfService(extCompanyId, active);
    }

    @GET
    @Path("blocked/{extCompanyId}/formalDebtCollection")
    @Secured(all = true)
    @ApiOperation("Pobranie listy blokad dla klienta w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<FormalDebtCollectionCustomerOutOfServiceDto> getFormalDebtCollectionCustomersOutOfService(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                                                                          @PathParam("extCompanyId") final long extCompanyId,
                                                                                                          @ApiParam(name = "active", value = "Czy pobierać tylko aktywne blokady")
                                                                                                          @QueryParam("active") final Boolean active) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        return customerService.findFormalDebtCollectionCustomersOutOfService(extCompanyId, active);
    }

    @GET
    @Path("blocked/{extCompanyId}/contracts")
    @Secured(all = true)
    @ApiOperation("Pobranie listy blokad kontraktów dla klienta w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContractOutOfServiceDto> getCustomerContractsOutOfService(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                                          @PathParam("extCompanyId") final long extCompanyId) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        return customerService.findActiveContractsOutOfService(extCompanyId);
    }

    @GET
    @Path("blocked/{extCompanyId}/contracts/formalDebtCollection")
    @Secured(all = true)
    @ApiOperation("Pobranie listy blokad kontraktów dla klienta w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<FormalDebtCollectionContractOutOfServiceDto> getFormalDebtCollectionContractsOutOfService(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                                                                          @PathParam("extCompanyId") final long extCompanyId) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        return customerService.findActiveFormalDebtCollectionContractsOutOfService(extCompanyId);
    }

    @PATCH
    @Path("block/{customerOutOfServiceId}")
    @Secured(all = true)
    @ApiOperation("Aktualizacja istniejącej blokady klienta w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateCustomerOutOfService(@HeaderParam(AUTHORIZATION) final String token,
                                               @ApiParam(name = "customerOutOfServiceId", value = "Identyfikator blokady klienta")
                                               @PathParam("customerOutOfServiceId") final long customerOutOfServiceId,
                                               @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final CustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        customerValidator.checkIfCustomerOutOfServiceExists(customerOutOfServiceId);
        customerService.updateCustomerOutOfService(customerOutOfServiceInputDto, customerOutOfServiceId, jwtService.getLoggedOperatorId(token));
        return Response.accepted().build();
    }

    @PATCH
    @Path("block/{customerOutOfServiceId}/formalDebtCollection")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Aktualizacja istniejącej blokady klienta w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateFormalDebtCollectionCustomerOutOfService(@HeaderParam(AUTHORIZATION) final String token,
                                                                   @ApiParam(name = "customerOutOfServiceId", value = "Identyfikator blokady klienta")
                                                                   @PathParam("customerOutOfServiceId") final long customerOutOfServiceId,
                                                                   @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final FormalDebtCollectionCustomerOutOfServiceInputDto customerOutOfServiceInputDto) {
        customerValidator.checkIfFormalDebtCollectionCustomerOutOfServiceExists(customerOutOfServiceId);
        customerService.updateFormalDebtCollectionCustomerOutOfService(customerOutOfServiceInputDto, customerOutOfServiceId, jwtService.getLoggedOperatorId(token));
        return Response.accepted().build();
    }

    @PATCH
    @Path("block/{extCompanyId}/contract/{contractOutOfServiceId}")
    @Secured(all = true)
    @ApiOperation("Aktualizacja istniejącej blokady umowy klienta w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateContractOutOfService(@HeaderParam(AUTHORIZATION) final String token,
                                               @ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                               @PathParam("extCompanyId") final long extCompanyId,
                                               @ApiParam(name = "contractOutOfServiceId", value = "Identyfikator blokady umowy klienta")
                                               @PathParam("contractOutOfServiceId") final long contractOutOfServiceId,
                                               @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final ContractOutOfServiceInputDto contractOutOfServiceDto) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        customerValidator.checkIfContractOutOfServiceExists(contractOutOfServiceId, extCompanyId);
        customerService.updateCustomerContractOutOfService(contractOutOfServiceDto, contractOutOfServiceId, jwtService.getLoggedOperatorId(token));
        return Response.accepted().build();
    }

    @PATCH
    @Path("block/{extCompanyId}/contract/{contractOutOfServiceId}/formalDebtCollection")
    @Secured(all = true)
    @ApiOperation("Aktualizacja istniejącej blokady umowy klienta w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateFormalDebtCollectionContractOutOfService(@HeaderParam(AUTHORIZATION) final String token,
                                                                   @ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                                   @PathParam("extCompanyId") final long extCompanyId,
                                                                   @ApiParam(name = "contractOutOfServiceId", value = "Identyfikator blokady umowy klienta")
                                                                   @PathParam("contractOutOfServiceId") final long contractOutOfServiceId,
                                                                   @ApiParam(required = true, value = "Szczegółowe wytyczne blokady") final FormalDebtCollectionContractOutOfServiceInputDto contractOutOfServiceDto) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        customerValidator.checkIfFormalDebtCollectionContractOutOfServiceExists(contractOutOfServiceId, extCompanyId);
        customerService.updateFormalDebtCollectionCustomerContractOutOfService(contractOutOfServiceDto, contractOutOfServiceId, jwtService.getLoggedOperatorId(token));
        return Response.accepted().build();
    }

    @DELETE
    @Path("block/{customerOutOfServiceId}")
    @Secured(all = true)
    @ApiOperation("Usuwa istniejącą blokadę klienta w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deleteCustomerOutOfService(@ApiParam(name = "customerOutOfServiceId", value = "Identyfikator blokady klienta")
                                           @PathParam("customerOutOfServiceId") final long customerOutOfServiceId) {
        customerValidator.checkIfCustomerOutOfServiceExists(customerOutOfServiceId);
        customerService.deleteCustomerOutOfService(customerOutOfServiceId);
    }

    @DELETE
    @Path("block/{customerOutOfServiceId}/formalDebtCollection")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Usuwa istniejącą blokadę klienta w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deleteFormalDebtCollectionCustomerOutOfService(@ApiParam(name = "customerOutOfServiceId", value = "Identyfikator blokady klienta")
                                                               @PathParam("customerOutOfServiceId") final long customerOutOfServiceId) {
        customerValidator.checkIfFormalDebtCollectionCustomerOutOfServiceExists(customerOutOfServiceId);
        customerService.deleteFormalDebtCollectionCustomerOutOfService(customerOutOfServiceId);
    }

    @DELETE
    @Path("block/{extCompanyId}/contract/{contractOutOfServiceId}")
    @Secured(all = true)
    @ApiOperation("Usuwa istniejącą blokadę umowy klienta w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deleteContractOutOfService(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                           @PathParam("extCompanyId") final long extCompanyId,
                                           @ApiParam(name = "contractOutOfServiceId", value = "Identyfikator blokady umowy klienta")
                                           @PathParam("contractOutOfServiceId") final long contractOutOfServiceId) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        customerValidator.checkIfContractOutOfServiceExists(contractOutOfServiceId, extCompanyId);
        customerService.deleteContractOutOfService(contractOutOfServiceId);
    }

    @DELETE
    @Path("block/{extCompanyId}/contract/{contractOutOfServiceId}/formalDebtCollection")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Usuwa istniejącą blokadę umowy klienta w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public void deleteFormalDebtCollectionContractOutOfService(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                                               @PathParam("extCompanyId") final long extCompanyId,
                                                               @ApiParam(name = "contractOutOfServiceId", value = "Identyfikator blokady umowy klienta")
                                                               @PathParam("contractOutOfServiceId") final long contractOutOfServiceId) {
        customerValidator.checkIfCompanyExists(extCompanyId);
        customerValidator.checkIfFormalDebtCollectionContractOutOfServiceExists(contractOutOfServiceId, extCompanyId);
        customerService.deleteFormalDebtCollectionContractOutOfService(contractOutOfServiceId);
    }

    @PATCH
    @Path("{customerId}/accountManager")
    @Secured(PHONE_DEBT_COLLECTOR_MANAGER)
    @ApiOperation("Modyfikacja opiekuna klienta. Usuwanie następuje, gdy przekazany identyifikator opiekuna jest pusty")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateCustomerAccountManager(@ApiParam(value = "Identyfikator klienta dla którego nastąpi zmiana opiekuna")
                                                 @PathParam("customerId") long customerId,
                                                 @ApiParam(value = "Identyfikator opiekuna (pusta wartość aby usunąć)")
                                                 @QueryParam("accountManagerId") Long accountManagerId) {
        customerValidator.checkIfCustomerExists(customerId);
        if (accountManagerId != null) {
            operatorValidator.checkIfOperatorExists(accountManagerId);
        }
        customerService.updateCustomerAccountManager(customerId, accountManagerId);
        return Response.accepted().build();
    }

    @GET
    @Path("segments")
    @Secured(all = true)
    @ApiOperation("Pobieranie listy segmentów klientów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<SegmentDto> findAllSegments() {
        return SegmentDto.SEGMENTS;
    }

    @GET
    @Path("reasonTypes")
    @Secured(all = true)
    @ApiOperation("Pobieranie listy typów przyczyn zablokowania/odrzucenia klienta")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<KeyLabel> findAllReasonTypes() {
        return Stream.of(ReasonType.values()).map(KeyLabel::new).collect(Collectors.toList());
    }
}
