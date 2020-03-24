package com.codersteam.alwin.common.issue;

/**
 * Segmenty klienta
 *
 * @author Piotr Naroznik
 */
public enum Segment {

    /**
     * wszyscy klienci AL/AB, którzy w trakcie trwania umów leasingu / pożyczki nie mieli opóźnień w spłacie raty powyżej 30 dni
     * w przeciągu ostatnich sześciu miesięcy trwania umowy
     */
    A,

    /**
     * wszyscy klienci AL/AB, którzy w trakcie trwania umów leasingu / pożyczki mieli opóźnienia w spłacie raty przekraczające 30 dni,
     * w przeciągu ostatnich sześciu miesięcy trwania umowy (w tym klienci po restrukturyzacji)
     * oraz klienci których historia współpracy z AL/AB jest krótsza niż pół roku
     */
    B
}
