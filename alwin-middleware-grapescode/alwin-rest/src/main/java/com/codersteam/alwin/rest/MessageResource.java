package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.core.api.exception.SmsSendingException;
import com.codersteam.alwin.core.api.model.message.SmsMessageDto;
import com.codersteam.alwin.core.api.model.message.SmsTemplateDto;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.message.MessageService;
import com.codersteam.alwin.validator.SmsValidator;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/messages")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/messages")
public class MessageResource {

    private MessageService messageService;
    private SmsValidator smsValidator;
    private JwtService jwtService;

    public MessageResource() {
    }

    @Inject
    public MessageResource(final MessageService messageService, final JwtService jwtService, final SmsValidator smsValidator) {
        this.messageService = messageService;
        this.smsValidator = smsValidator;
        this.jwtService = jwtService;
    }

    @GET
    @Path("/sms/templates")
    @Secured(all = true)
    @ApiOperation("Pobranie szblonów wiadomości sms")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public List<SmsTemplateDto> findSmsTemplates() {
        return messageService.findSmsMessagesTemplates();
    }

    @POST
    @Path("/sms/send")
    @Secured(all = true)
    @ApiOperation("Wysłanie wiadomości sms")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response sendSmsMessage(@HeaderParam(AUTHORIZATION) final String token,
                                   @ApiParam(required = true, value = "wiadomość sms do wysłania") final SmsMessageDto smsMessage) throws SmsSendingException {
        final Long loggedOperatorId = jwtService.getLoggedOperatorId(token);
        smsValidator.validate(smsMessage);
        messageService.sendSmsMessage(smsMessage, loggedOperatorId);
        return Response.accepted().build();
    }
}
