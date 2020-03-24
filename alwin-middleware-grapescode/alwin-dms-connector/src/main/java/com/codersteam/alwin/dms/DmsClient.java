package com.codersteam.alwin.dms;

import com.codersteam.alwin.common.prop.AlwinProperties;
import com.codersteam.alwin.dms.model.*;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.aliorleasing.dms.*;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.handler.MessageContext;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codersteam.alwin.common.prop.AlwinPropertyKey.*;
import static com.codersteam.alwin.dms.SendDocumentRequestBuilder.sendDocumentRequest;
import static pl.aliorleasing.dms.DocumentResponseStatus.OK;

/**
 * @author Michal Horowic
 */
@Stateless
public class DmsClient {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String LOGIN_SUCCESS_STATUS = "AUTHORIZED";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_HEADER = "Cookie";

    private AlwinProperties alwinProperties;
    private ConfigurableMapper mapper;

    @WebServiceRef(DocumentRPCService_Service.class)
    private DocumentRPCService_Service dmsService;

    private DocumentRPCService service;

    public DmsClient() {
    }

    @Inject
    public DmsClient(final AlwinProperties alwinProperties, final DmsMapper mapper) {
        this.alwinProperties = alwinProperties;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        service = dmsService.getDocumentPort();
        final BindingProvider bp = (BindingProvider) service;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, alwinProperties.getProperty(DMS_ENDPOINT));
    }

    public DocumentDto generateDocument(final GenerateDocumentDto generateDocumentRequestDto) {
        final GenerateDocumentRequest request = mapper.map(generateDocumentRequestDto, GenerateDocumentRequest.class);
        final GeneratedDocument response = service.generateDocument(request);
        checkStatusResponse(generateDocumentRequestDto, response);
        return mapper.map(response.getDocument(), DocumentDto.class);
    }

    public DocumentDto getDocument(final String documentHash) {
        final GetDocumentRequest request = new GetDocumentRequest();
        request.setDocumentHash(documentHash);
        final DocumentResponse response = service.getDocument(request);
        checkStatusResponse(documentHash, response);
        return mapper.map(response.getDocument(), DocumentDto.class);
    }

    public DocumentDto sendDocument(final SendDocumentDto sendDocumentDto) {
        final SendDocumentRequest request = sendDocumentRequest().withDocument(sendDocumentDto).withSystem("ALWIN").withAuthor("ALWIN").build();

        try {
            final URL url = new URL(alwinProperties.getProperty(DMS_ENDPOINT) + "?wsdl");
            final DocumentRPCService service = new DocumentRPCService_Service(url).getDocumentPort();
            final BindingProvider bp = (BindingProvider) service;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, alwinProperties.getProperty(DMS_ENDPOINT));

            final SentDocument sentDocument = sendDocument(service, request);
            return mapper.map(sentDocument.getDocument(), DocumentDto.class);

        } catch (final MalformedURLException e) {
            LOG.error("[DmsClientImpl - sendDocument] Could not generate response!", e);
            return null;
        }
    }

    private SentDocument sendDocument(final DocumentRPCService service, final SendDocumentRequest request) {
        try {
            if (login(service)) {
                final SentDocument sentDocument = service.sendDocument(request);
                if (sentDocument.getStatus().equals(DocumentResponseStatus.OK)) {
                    return sentDocument;
                } else {
                    LOG.error("[DmsClientImpl - sentDocument] Could not send response! sentDocument: " + sentDocument.toString());
                    return null;
                }
            } else {
                LOG.error("[DmsClientImpl - sentDocument] Could not generate response! NOTAUTHORIZED");
                return null;
            }
        } catch (final Exception e) {
            LOG.error("[DmsClientImpl - sentDocument] Could not generate response!", e);
            return null;
        }
    }

    private boolean login(final DocumentRPCService documentPort) {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(alwinProperties.getProperty(DMS_LOGIN));
        loginRequest.setPassword(alwinProperties.getProperty(DMS_PASSWORD));
        final LoginResponse loginResponse = documentPort.authorize(loginRequest);

        if (loginResponse.getStatus().equals(LOGIN_SUCCESS_STATUS)) {
            final Map map = (Map) ((BindingProvider) documentPort).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);
            final List<String> setCookie = (List<String>) map.get(SET_COOKIE_HEADER);
            Map responseHeader = (Map) ((BindingProvider) documentPort).getRequestContext().get(MessageContext.HTTP_REQUEST_HEADERS);

            if (responseHeader == null) {
                responseHeader = new HashMap();
                ((BindingProvider) documentPort).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, responseHeader);
            }

            responseHeader.put(COOKIE_HEADER, Collections.singletonList(setCookie.get(0)));
            LOG.info("[DmsClientImpl - login] Login: SUCCESS");
            return true;
        } else {
            LOG.error("[DmsClientImpl - login] Could not generate response! Login: UNAUTHORIZED");
            return false;
        }
    }

    public TemplateDto getDocumentTemplate(final String templateId) {
        final GetDocumentTemplateRequest request = createGetDocumentTemplateRequest(templateId);
        final DocumentTemplateResponse response = service.getDocumentTemplate(request);
        checkStatusResponse(templateId, response);
        return mapper.map(response.getDocumentTemplate(), TemplateDto.class);
    }

    public String authorize(final LoginRequestDto loginRequestDto) {
        final LoginRequest request = mapper.map(loginRequestDto, LoginRequest.class);
        final LoginResponse response = service.authorize(request);
        return response.getStatus();
    }

    public String enableDocumentTemplateOnline(final String templateId) {
        final EnableTemplateOnlineRequest request = createEnableTemplateOnlineRequest(templateId);
        final EnableTemplateOnlineResponse response = service.enableDocumentTemplateOnline(request);
        checkStatusResponse(templateId, response);
        return response.getHtmlUrl();
    }

    private GetDocumentTemplateRequest createGetDocumentTemplateRequest(final String templateId) {
        final GetDocumentTemplateRequest request = new GetDocumentTemplateRequest();
        request.setDocumentTemplateId(templateId);
        return request;
    }

    private EnableTemplateOnlineRequest createEnableTemplateOnlineRequest(final String templateId) {
        final EnableTemplateOnlineRequest request = new EnableTemplateOnlineRequest();
        request.setDocumentTemplateId(templateId);
        return request;
    }

    private void checkStatusResponse(final Object request, final StatusResponse response) {
        final DocumentResponseStatus status = response.getStatus();
        if (!OK.equals(status)) {
            LOG.error("Document service error {} for request {}", status, request);
            throw new RuntimeException("TODO Task 20060");
        }
    }
}
