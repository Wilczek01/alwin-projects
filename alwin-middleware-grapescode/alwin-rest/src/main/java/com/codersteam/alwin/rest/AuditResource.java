package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.model.audit.AuditLogEntryDto;
import com.codersteam.alwin.core.api.service.audit.AuditService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/audit")
@Api("/audit")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AuditResource {

    private AuditService auditService;

    public AuditResource() {
    }

    @Inject
    public AuditResource(final AuditService auditService) {
        this.auditService = auditService;
    }

    @GET
    @Path("issue/{issueId}")
    @Secured(all = true)
    @ApiOperation("Pobieranie wszystkich zmian dla danego zlecenia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findAuditForIssue(@ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") long issueId) {
        final List<AuditLogEntryDto> issueChanges = auditService.findIssueChanges(issueId);
        return Response.ok(issueChanges).build();
    }
}
