package com.codersteam.alwin.rest;

import com.codersteam.alwin.core.api.notification.NotificationDto;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.notification.NotificationService;
import com.codersteam.alwin.core.api.service.operator.OperatorService;
import com.codersteam.alwin.model.notification.CreateIssueNotificationRequest;
import com.codersteam.alwin.validator.NotificationValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/notifications")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/notifications")
public class NotificationResource {

    private NotificationService notificationService;
    private JwtService jwtService;
    private NotificationValidator notificationValidator;
    private OperatorService operatorService;

    public NotificationResource() {
    }

    @Inject
    public NotificationResource(final NotificationService notificationService, final JwtService jwtService, final NotificationValidator notificationValidator,
                                final OperatorService operatorService) {
        this.notificationService = notificationService;
        this.jwtService = jwtService;
        this.notificationValidator = notificationValidator;
        this.operatorService = operatorService;
    }

    @GET
    @Path("/issue/{issueId}")
    @ApiOperation("Pobranie posortowanej listy powiadomień")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response findAllNotifications(@ApiParam(name = "issueId", value = "Identyfikator zlecenia") @PathParam("issueId") final Long issueId) {
        return Response.ok(notificationService.findAllIssueNotifications(issueId)).build();
    }

    @POST
    @ApiOperation("Utworzenie powiadomienia")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createNotification(@HeaderParam(AUTHORIZATION) final String token,
                                       @ApiParam(required = true, value = "Dane do utworzenia powiadomienia") final CreateIssueNotificationRequest createIssueNotificationRequest) {
        final Long senderId = jwtService.getLoggedOperatorId(token);
        final Long issueId = createIssueNotificationRequest.getIssueId();
        final String message = createIssueNotificationRequest.getMessage();
        final boolean readConfirmationRequired = createIssueNotificationRequest.isReadConfirmationRequired();

        final NotificationDto notificationDto = setNotificationFields(senderId, issueId, message, readConfirmationRequired);

        notificationValidator.validateForCreate(notificationDto);
        notificationService.createNotification(notificationDto);
        return Response.created(null).build();
    }

    @PATCH
    @Path("/{notificationId}")
    @ApiOperation("Aktualizacja powiadomienia jako odczytane")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateNotificationReadDate(@HeaderParam(AUTHORIZATION) final String token,
                                               @ApiParam(name = "notificationId", value = "Identyfikator powiadomienia")
                                               @PathParam("notificationId") final Long notificationId) {
        notificationValidator.validateForUpdate(notificationId);
        notificationService.markNotificationAsRead(notificationId);
        return Response.accepted().build();
    }

    private NotificationDto setNotificationFields(final Long senderId, final Long issueId, final String message, final boolean readConfirmationRequired) {
        final NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSender(operatorService.findOperatorById(senderId));
        notificationDto.setIssueId(issueId);
        notificationDto.setMessage(message);
        notificationDto.setReadConfirmationRequired(readConfirmationRequired);
        return notificationDto;
    }
}