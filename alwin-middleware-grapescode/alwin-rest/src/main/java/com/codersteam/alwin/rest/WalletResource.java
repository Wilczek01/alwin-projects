package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.issue.AllWallets;
import com.codersteam.alwin.core.api.service.issue.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/wallets")
@Api("/wallets")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class WalletResource {

    private WalletService walletService;

    public WalletResource() {
    }

    @Inject
    public WalletResource(final WalletService walletService) {
        this.walletService = walletService;
    }


    @GET
    @Path("/all")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich prezentowanych portfeli")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public AllWallets calculateAllWallets() {
        return walletService.findAllWallets();
    }
}