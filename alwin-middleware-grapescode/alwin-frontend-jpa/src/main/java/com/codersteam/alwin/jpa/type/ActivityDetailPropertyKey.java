package com.codersteam.alwin.jpa.type;

/**
 * Typy szczegółów czynności windykacyjnych
 *
 * @author Piotr Naroznik
 */
public enum ActivityDetailPropertyKey {

    /**
     * Numer telefonu
     */
    PHONE_NUMBER,

    /**
     * Czy pozostawiono wiadomość
     */
    MESSAGE_LEFT,

    /**
     * Długość rozmowy
     */
    PHONE_CALL_LENGTH,

    /**
     * Rozmówca
     */
    PHONE_CALL_PERSON,

    /**
     * Termin zapłaty
     */
    DATE_OF_PAYMENT,

    /**
     * Treść wiadomości
     */
    MESSAGE_CONTENT,

    /**
     * Adres email
     */
    EMAIL_ADDRESS,

    /**
     * Przyczyna nieudanego kontaktu telefonicznego
     */
    FAILED_PHONE_CALL_REASON,

    /**
     * Komentarz
     */
    REMARK,

    /**
     * Czy zastano klienta
     */
    CUSTOMER_WAS_PRESENT,

    /**
     * Załącznik
     */
    ATTACHMENT

}
