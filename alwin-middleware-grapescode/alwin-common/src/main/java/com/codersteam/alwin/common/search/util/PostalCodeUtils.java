package com.codersteam.alwin.common.search.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author Michal Horowic
 */
public class PostalCodeUtils {

    private static final String WILD_CARD = "x";
    private static final String POSTAL_CODE_MASK_PATTERN = "(\\d)(([x,X][-]([x,X]{3}))|(\\d)[-]((\\d[x,X]{2})|(\\d{2}[x,X])|(\\d{3})|([x,X]{3})))";
    public static final int POSTAL_CODE_DIGITS_COUNT = 5;

    /**
     * Zwraca początkową, stałą część kodu pocztowego z maski
     * Dla maski 1x-xxx zwraca 1
     * Dla maski 12-xxx zwraca 12-
     * Dla maski 12-3xx zwraca 12-3
     * Dla maski 12-34x zwraca 12-34
     * Dla maski 12-345 zwraca 12-345
     *
     * @param mask - maska kodu pocztowego
     * @return stała część maski z początku
     */
    public static String extractPostalCodeStart(final String mask) {
        if (isEmpty(mask) || isPostalCodeMaskInvalid(mask)) {
            return StringUtils.EMPTY;
        }
        final int wildcardIndex = mask.toLowerCase().indexOf(WILD_CARD);
        if (wildcardIndex != -1) {
            return mask.substring(0, wildcardIndex).toLowerCase();
        }
        return mask;
    }

    /**
     * Sprawdza czy podana maska ma poprawny format
     * Poprawny format zawiera cyfrę na początku, a następnie cyfrę lub znak 'x'. Po znaku 'x' nie może pojawić się już cyfra, tylko kolejne znaki 'x'
     * Przykłady poprawnych formatów: 12-34x, 12-xxx, 1x-xxx, 12-345
     * Przyjłady niepoprawnych formatów: xx-xxx, 1x-34x, 1x-345,
     *
     * @param mask - maska
     * @return true jeżeli maska ma poprawny format, false w przeciwnym razie
     */
    public static boolean isPostalCodeMaskInvalid(final String mask) {
        return !mask.matches(POSTAL_CODE_MASK_PATTERN);
    }

    /**
     * Zwraca wszystkie możliwe maski dla podaje maski
     * Jeżeli podana maska to 12-345 to możliwe dla niej maski to:
     * 1x-xxx
     * 12-xxx
     * 12-3xx
     * 12-34x
     * 12-345
     * Jeżeli podana maska to 12-3xx to możliwe dla niej maski to:
     * 1x-xxx
     * 12-xxx
     * 12-3xx
     *
     * @param mask - maska kodu pocztowego
     * @return możliwe maski kodu pocztowego dla podanej maski
     */
    public static Set<String> buildAllPossibleMasksGivenMask(final String mask) {
        if (isEmpty(mask)) {
            return Collections.emptySet();
        }

        final Set<String> allMasks = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            if (mask.toLowerCase().charAt(i) == 'x') {
                break;
            }
            allMasks.add(String.format("%s%s-%s%s%s",
                    mask.charAt(0),
                    getCharToMask(mask, i, 1),
                    getCharToMask(mask, i, 3),
                    getCharToMask(mask, i, 4),
                    getCharToMask(mask, i, 5))
            );
        }
        return allMasks;
    }

    private static char getCharToMask(final String mask, final int i, final int no) {
        return i < no ? 'x' : mask.charAt(no);
    }

    public static Optional<String> toCorrectPostalCode(final String postalCode) {
        return Optional.ofNullable(postalCode)
                .map(code -> code.replaceAll("[^\\d]", ""))
                .filter(code -> code.length() > 0 && code.length() <= POSTAL_CODE_DIGITS_COUNT)
                .map(code -> StringUtils.rightPad(code, POSTAL_CODE_DIGITS_COUNT, "0"))
                .map(code -> code.substring(0, 2) + "-" + code.substring(2, 5));
    }

}
