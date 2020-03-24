package com.codersteam.alwin.rest;

import com.codersteam.aida.core.api.model.InvoiceSettlementDto;
import com.codersteam.aida.core.api.service.SettlementService;
import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.service.AidaService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/settlements")
@Api("/settlements")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SettlementResource {

    private SettlementService settlementService;

    public SettlementResource() {
    }

    @Inject
    public SettlementResource(final AidaService aidaService) {
        this.settlementService = aidaService.getSettlementService();
    }

    @GET
    @Path("invoice")
    @Secured(all = true)
    @ApiOperation("Pobranie listy rozrachunków dokumentu")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<InvoiceSettlementDto> findInvoiceSettlements(@ApiParam(name = "invoiceNo", value = "Numer dokumentu")
                                           @QueryParam("invoiceNo") final String invoiceNo) {
        return settlementService.findInvoiceSettlementsByInvoiceNumber(invoiceNo);
    }
}
