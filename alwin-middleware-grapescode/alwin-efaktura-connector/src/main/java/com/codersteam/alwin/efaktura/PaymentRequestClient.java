package com.codersteam.alwin.efaktura;

import com.codersteam.alwin.common.prop.AlwinProperties;
import com.codersteam.alwin.common.prop.AlwinPropertyKey;
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentRequestDto;
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentResponseDto;
import com.codersteam.alwin.efaktura.model.payment.PaymentRequestException;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.aliorleasing.ali.payment_request_api.ws.*;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import java.lang.invoke.MethodHandles;

@Stateless
public class PaymentRequestClient {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AlwinProperties alwinProperties;
    private ConfigurableMapper mapper;

    @WebServiceRef(PaymentRequestApiBeanService.class)
    private PaymentRequestApiBeanService paymentRequestService;

    private PaymentRequestServiceType service;

    public PaymentRequestClient() {
    }

    @Inject
    public PaymentRequestClient(final AlwinProperties alwinProperties, final PaymentRequestClientMapper mapper) {
        this.alwinProperties = alwinProperties;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        service = paymentRequestService.getPaymentRequestServicePort();
        final BindingProvider bp = (BindingProvider) service;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, alwinProperties.getProperty(AlwinPropertyKey.EFAKTURA_ENDPOINT));
    }

    public GeneratePaymentResponseDto generatePaymentRequest(final GeneratePaymentRequestDto request) throws PaymentRequestException {
        final GeneratePaymentRequestRequest generatePaymentRequestRequest = new GeneratePaymentRequestRequest();
        generatePaymentRequestRequest.setPaymentRequest(mapper.map(request, PaymentRequest.class));
        generatePaymentRequestRequest.setSecurityInfo(PaymentRequestSecurityInfoUtils.prepareToken());
        try {
            final PaymentRequest response = service.generatePaymentRequest(generatePaymentRequestRequest);
            return mapper.map(response, GeneratePaymentResponseDto.class);
        } catch (SecurityException_Exception | Exception_Exception e) {
            LOG.error("Error while generating payment request", e);
            throw new PaymentRequestException(e.getMessage());
        }
    }
}
