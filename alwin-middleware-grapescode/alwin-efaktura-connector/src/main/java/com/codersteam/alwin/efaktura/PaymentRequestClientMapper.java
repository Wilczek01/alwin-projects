package com.codersteam.alwin.efaktura;

import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentRequestDto;
import com.codersteam.alwin.efaktura.model.payment.GeneratePaymentResponseDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import pl.aliorleasing.ali.payment_request_api.ws.PaymentRequest;

import javax.ejb.Stateless;

/**
 * @author Dariusz Rackowski
 */
@Stateless
public class PaymentRequestClientMapper extends ConfigurableMapper {

    @Override
    protected void configure(final MapperFactory factory) {

        factory.classMap(GeneratePaymentRequestDto.class, PaymentRequest.class)
                .field("outstandingInvoices", "outstandingInvoices.invoice")
                .byDefault()
                .register();

        factory.classMap(GeneratePaymentResponseDto.class, PaymentRequest.class)
                .byDefault()
                .register();

    }

}
