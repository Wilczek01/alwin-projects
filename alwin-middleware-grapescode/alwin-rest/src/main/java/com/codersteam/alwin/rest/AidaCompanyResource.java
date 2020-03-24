package com.codersteam.alwin.rest;

import com.codersteam.aida.core.api.model.AidaCompanyDto;
import com.codersteam.aida.core.api.search.criteria.AidaCompanySearchCriteria;
import com.codersteam.aida.core.api.service.CompanyService;
import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.service.AidaService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.codersteam.aida.core.api.search.criteria.builder.AidaCompanySearchCriteriaBuilder.anAidaCompanySearchCriteria;
import static com.codersteam.alwin.core.api.model.user.OperatorType.DIRECT_DEBT_COLLECTION_MANAGER;
import static com.codersteam.alwin.core.api.model.user.OperatorType.PHONE_DEBT_COLLECTOR_MANAGER;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/aidaCompanies")
@Api("/aidaCompanies")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AidaCompanyResource {

    private CompanyService companyService;

    public AidaCompanyResource() {
    }

    @Inject
    public AidaCompanyResource(final AidaService aidaService) {
        this.companyService = aidaService.getCompanyService();
    }

    @GET
    @Secured({PHONE_DEBT_COLLECTOR_MANAGER, DIRECT_DEBT_COLLECTION_MANAGER})
    @ApiOperation("Pobranie stronicowanej listy firm z systemu AIDA spełniających zadane kryteria")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findAidaCompanies(@ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                      @QueryParam("firstResult") final int firstResult,
                                      @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                      @QueryParam("maxResults") final int maxResults,
                                      @ApiParam(name = "extCompanyId", value = "Identyfikator firmy w systemie AIDA")
                                      @QueryParam("extCompanyId") Long extCompanyId,
                                      @ApiParam(name = "companyName", value = "Nazwa firmy (lub jej część)")
                                      @QueryParam("companyName") String companyName,
                                      @ApiParam(name = "nip", value = "Numer identyfikacji podatkowej")
                                      @QueryParam("nip") String nip,
                                      @ApiParam(name = "regon", value = "REGON")
                                      @QueryParam("regon") String regon) {
        final AidaCompanySearchCriteria searchCriteria = anAidaCompanySearchCriteria()
                .withFirstResult(firstResult)
                .withMaxResults(maxResults)
                .withId(extCompanyId)
                .withName(companyName)
                .withNip(nip)
                .withRegon(regon)
                .build();
        final long totalCount = companyService.findCompaniesCount(searchCriteria);
        final List<AidaCompanyDto> companies = companyService.findCompanies(searchCriteria);
        final Page<AidaCompanyDto> companiesPageResult = new Page<>(companies, totalCount);
        return Response.ok(companiesPageResult).build();
    }

    @GET
    @Path("{extCompanyId}")
    @Secured(all = true)
    @ApiOperation("Pobranie firmy z systemu AIDA po jej identyfikatorze")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findAidaCompany(@ApiParam(name = "extCompanyId", value = "Identyfikator firmy z zewnętrznego systemu")
                                    @PathParam("extCompanyId") final long extCompanyId) {
        final AidaCompanyDto company = companyService.findCompanyByCompanyId(extCompanyId);
        if (company != null) {
            return Response.ok(company).build();
        }
        return Response.status(NOT_FOUND).build();
    }
}
