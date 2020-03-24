package com.codersteam.alwin.efaktura;

import org.apache.commons.codec.digest.DigestUtils;
import pl.aliorleasing.ali.payment_request_api.ws.SecurityInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Michal Horowic
 */
public class PaymentRequestSecurityInfoUtils {

    private static final String SYSTEM = "ALWIN";
    private static final String HASH = "AlwinInterfejsDlaWezwan082018!.%s";

    public static SecurityInfo prepareToken() {
        final DateFormat format = new SimpleDateFormat("yyyyMMdd");
        final String token = DigestUtils.sha512Hex(String.format(HASH, format.format(new Date())));
        final SecurityInfo securityInfo = new SecurityInfo();
        securityInfo.setToken(token);
        securityInfo.setSystem(SYSTEM);
        return securityInfo;
    }
}
