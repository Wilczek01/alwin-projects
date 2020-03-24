package com.codersteam.alwin.common;

import java.math.BigDecimal;
import java.time.ZoneId;

/**
 * @author Tomasz Sliwinski
 */
public final class AlwinConstants {

    public static final BigDecimal ZERO = new BigDecimal("0.00");

    public static final ZoneId SYSTEM_DEFAULT_TIME_ZONE = ZoneId.systemDefault();

    private AlwinConstants() {
    }
}
