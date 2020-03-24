package com.codersteam.alwin.core.service.impl.message;

import com.codersteam.alwin.core.api.exception.SmsSendingException;
import com.codersteam.alwin.core.api.service.message.SmsSenderService;
import com.codersteam.alwin.core.config.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Naroznik
 */
@Stateless
public class SmsSenderServiceImpl implements SmsSenderService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    @Property("mobiltek.url")
    private String smsApiUrl;

    @Inject
    @Property("mobiltek.orig")
    private String smsApiOrig;

    @Inject
    @Property("mobiltek.login")
    private String smsApiLogin;

    @Inject
    @Property("mobiltek.passwd")
    private String smsApiPass;

    @Inject
    @Property("mobiltek.service")
    private String smsApiService;

    private final Executor executor;

    public SmsSenderServiceImpl() {
        executor = prepareHttpExecutor();
    }

    public void send(final String phoneNumber, final String message, final int id) throws SmsSendingException {
        try {
            final Request request = prepareRequest(phoneNumber, message, id);
            final String responseBody = send(request).returnContent().asString();
            throwExceptionIfMessageWasNotSend(phoneNumber, message, id, responseBody);
        } catch (final IOException e) {
            LOG.error("Cannot send sms message {} to {} because of error {}", phoneNumber, message, e);
            throw new SmsSendingException(phoneNumber, message, id, e);
        }
    }

    private void throwExceptionIfMessageWasNotSend(final String phoneNumber, final String message, final int id, final String responseBody) throws
            SmsSendingException {
        if (!wasMessageSendSuccessful(responseBody)) {
            LOG.error("Cannot send sms message with id {} and body {} to {}. Response message is {}", id, message, phoneNumber, responseBody);
            throw new SmsSendingException(phoneNumber, message, id, null);
        }
    }

    private boolean wasMessageSendSuccessful(final String responseBody) {
        return StringUtils.equals(responseBody, "OK\n");
    }

    protected Response send(final Request request) throws IOException {
        return executor.execute(request);
    }

    private Request prepareRequest(final String phoneNumber, final String message, final int id) {
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("login", smsApiLogin));
        params.add(new BasicNameValuePair("passwd", smsApiPass));
        params.add(new BasicNameValuePair("service", smsApiService));
        params.add(new BasicNameValuePair("orig", smsApiOrig));
        params.add(new BasicNameValuePair("encoding", "utf8"));
        params.add(new BasicNameValuePair("id", String.valueOf(id)));
        params.add(new BasicNameValuePair("text", message));
        params.add(new BasicNameValuePair("dests[]", phoneNumber));
        return Request.Post(smsApiUrl).bodyForm(params, StandardCharsets.UTF_8);
    }

    private Executor prepareHttpExecutor() {
        return Executor.newInstance(prepareHttpClient());
    }

    private CloseableHttpClient prepareHttpClient() {
        final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    public void setSmsApiUrl(final String smsApiUrl) {
        this.smsApiUrl = smsApiUrl;
    }

    public void setSmsApiOrig(final String smsApiOrig) {
        this.smsApiOrig = smsApiOrig;
    }

    public void setSmsApiLogin(final String smsApiLogin) {
        this.smsApiLogin = smsApiLogin;
    }

    public void setSmsApiPass(final String smsApiPass) {
        this.smsApiPass = smsApiPass;
    }

    public void setSmsApiService(final String smsApiService) {
        this.smsApiService = smsApiService;
    }
}