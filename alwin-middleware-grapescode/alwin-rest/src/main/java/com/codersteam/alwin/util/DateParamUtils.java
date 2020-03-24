package com.codersteam.alwin.util;

import com.codersteam.alwin.parameters.DateParam;

import java.util.Date;

/**
 * @author Tomasz Sliwinski
 */
public final class DateParamUtils {

    private DateParamUtils() {
    }

    public static Date getDateNullSafe(final DateParam dateParam) {
        return dateParam != null ? dateParam.getDate() : null;
    }
}
