package com.codersteam.alwin.common.util;

/**
 * @author Michal Horowic
 */
public class MandatoryUtils {

    /**
     * Rzuca wyjątek jeżeli podana wartość jest nullem lub zwraca ją w przeciwnym wypadku
     *
     * @param value - przekazana wartość
     * @param name  - nazwa co przekazana wartość reprezentuje
     * @param <T>   - typ przekazanej wartości
     * @return przekazana wartość lub rzuca wyjątek jeżeli jest ona nullem
     * @throws IllegalArgumentException jeżeli przekazana wartość jest nullem
     */
    public static <T> T mandatory(final T value, final String name) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(String.format("Brakujące wartość dla: %s", name));
        }
        return value;
    }
}
