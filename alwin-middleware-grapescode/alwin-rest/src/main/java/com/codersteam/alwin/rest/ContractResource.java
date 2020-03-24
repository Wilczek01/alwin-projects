package com.codersteam.alwin.rest;

import com.codersteam.aida.core.api.model.AidaContractWithSubjectsAndSchedulesDto;
import com.codersteam.aida.core.api.model.ContractSubjectDto;
import com.codersteam.aida.core.api.service.ContractSubjectService;
import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.contract.ContractFinancialSummaryDto;
import com.codersteam.alwin.core.api.model.contract.ContractWithExclusions;
import com.codersteam.alwin.core.api.model.contract.FormalDebtCollectionContractWithExclusions;
import com.codersteam.alwin.core.api.service.AidaService;
import com.codersteam.alwin.core.api.service.contract.ContractService;
import com.codersteam.alwin.validator.ContractValidator;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static com.codersteam.alwin.core.api.model.user.OperatorType.DIRECT_DEBT_COLLECTION_MANAGER;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/contracts")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/contracts")
public class ContractResource {

    private com.codersteam.aida.core.api.service.ContractService aidaContractService;
    private ContractSubjectService contractSubjectService;
    private ContractService contractService;
    private ContractValidator validator;

    public ContractResource() {
    }

    @Inject
    public ContractResource(final AidaService aidaService, final ContractService contractService, final ContractValidator validator) {
        this.aidaContractService = aidaService.getContractService();
        this.contractSubjectService = aidaService.getContractSubjectService();
        this.contractService = contractService;
        this.validator = validator;
    }

    @GET
    @Path("company/{extCompanyId}")
    @Secured(all = true)
    @ApiOperation("Pobranie listy kontraktów firmy wraz z harmonogramami oraz przedmiotami")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<AidaContractWithSubjectsAndSchedulesDto> findCompanyContractsWithSubjectsAndSchedules(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                                                                                      @PathParam("extCompanyId") Long extCompanyId) {
        return aidaContractService.findContractsWithSubjectsAndSchedulesByCompanyId(extCompanyId);
    }

    @GET
    @Path("simple/company/{extCompanyId}")
    @Secured(all = true)
    @ApiOperation("Pobranie listy kontraktów firmy z blokadami w windykacji nieformalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContractWithExclusions> findCompanyContracts(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                                             @PathParam("extCompanyId") Long extCompanyId) {
        return contractService.findContractsWithExclusionsByCompanyId(extCompanyId);
    }

    @GET
    @Path("simple/company/{extCompanyId}/formalDebtCollection")
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobranie listy kontraktów firmy z blokadami w windykacji formalnej")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<FormalDebtCollectionContractWithExclusions> findFormalDebtCollectionCompanyContracts(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy")
                                                                                                     @PathParam("extCompanyId") Long extCompanyId) {
        return contractService.findFormalDebtCollectionContractsWithExclusionsByCompanyId(extCompanyId);
    }

    @GET
    @Path("issue/{issueId}/summary")
    @Secured(all = true)
    @ApiOperation("Pobranie podsumowań finansowych per kontrakt w oparciu o identyfikator zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContractFinancialSummaryDto> calculateIssueContractFinancialSummaries(@ApiParam(name = "issueId", value = "Identyfikator zlecenia")
                                                                                      @PathParam("issueId") Long issueId,
                                                                                      @ApiParam(name = "notPaidOnly", value = "Czy pobierać tyko faktury nieopłacone")
                                                                                      @QueryParam("notPaidOnly") final boolean notPaidOnly,
                                                                                      @ApiParam(name = "overdueOnly", value = "Czy pobierać tyko faktury których termin płatności minął")
                                                                                      @QueryParam("overdueOnly") final boolean overdueOnly) {
        return contractService.calculateIssueContractFinancialSummaries(issueId, notPaidOnly, overdueOnly);
    }

    @GET
    @Path("subjects")
    @Secured(all = true)
    @ApiOperation("Pobranie listy przedmiotów dla umów")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<ContractSubjectDto> findContractSubjects(@ApiParam(name = "contractNo", value = "Numer umowy") @QueryParam("contractNo") String contractNo) {
        validator.validateContractNo(contractNo);
        return contractSubjectService.findContractSubjectByContractNo(contractNo);
    }
}
