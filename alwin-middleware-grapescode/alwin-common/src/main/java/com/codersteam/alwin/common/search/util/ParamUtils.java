package com.codersteam.alwin.common.search.util;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author Michal Horowic
 */
public class ParamUtils {

    /**
     * Dzieli wartość na dwie częsci po ostatniej znalezionej spacji i zwraca pierwszą część
     *
     * @param param - wartość parametru
     * @return pierwsza część parametru
     */
    public static String getFirstPartSeparatedBySpace(final String param) {
        return getFirstPart(param, StringUtils.SPACE);
    }

    /**
     * Dzieli wartość na dwie częsci po ostatniej znalezionej spacji i zwraca drugą część
     *
     * @param param - wartość parametru
     * @return druga część parametru
     */
    public static String getLastPartSeparatedBySpace(final String param) {
        return getLastPart(param, StringUtils.SPACE);
    }

    private static String getFirstPart(final String param, final String separator) {
        if (param == null) {
            return null;
        }

        final int lastSeparator = param.lastIndexOf(separator);

        return lastSeparator != -1 ? param.substring(0, lastSeparator) : param;
    }

    private static String getLastPart(final String param, final String separator) {
        if (param == null) {
            return null;
        }

        final int lastSeparator = param.lastIndexOf(separator);

        return lastSeparator != -1 ? param.substring(lastSeparator + 1) : param;
    }

    /**
     * Dzieli wartość na dwie częsci po ostatniej znalezionej spacji i zwraca je w odwróconej kolejności
     * np:
     * <p>
     * 'Jan' pzostanie niezmiennie jako 'Jan'
     * 'Jan Kowalski' zostatnie zamienione na 'Kowalski Jan'
     * 'Jan Adam Kowalski' zostatnie zamienione na 'Kowalski Jan Adam'
     *
     * @param param - wartość parametru
     * @return odwrócona wartość po ostatniej spacji
     */
    public static String revertValueSeparatedByLastSpace(final String param) {
        if (isEmpty(param) || param.lastIndexOf(StringUtils.SPACE) == -1) {
            return param;
        }
        final String firstPart = getFirstPartSeparatedBySpace(param);
        final String lastPart = getLastPartSeparatedBySpace(param);

        return String.format("%s %s", lastPart, firstPart);
    }
}
