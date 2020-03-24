package com.codersteam.alwin.jpa.type;

/**
 * Typy czynności windykacyjnych
 *
 * @author Piotr Naroznik
 */
public enum ActivityTypeKey {

    /**
     * Telefon wychodzący
     */
    OUTGOING_PHONE_CALL,

    /**
     * Wezwanie do zapłaty (podstawowe)
     */
    FIRST_DEMAND_PAYMENT,

    /**
     * Wezwanie do zapłaty (ostateczne)
     */
    LAST_DEMAND_PAYMENT,

    /**
     * Wiadomość SMS wychodząca
     */
    OUTGOING_SMS,

    /**
     * Telefon przychodzący
     */
    INCOMING_PHONE_CALL,

    /**
     * Email wychodzący
     */
    OUTGOING_EMAIL,

    /**
     * Nieudana próba kontaktu tel.
     */
    FAILED_PHONE_CALL_ATTEMPT,

    /**
     * Email przychodzący
     */
    INCOMING_EMAIL,

    /**
     * Wiadomość SMS przychodząca
     */
    INCOMING_SMS,

    /**
     * Komentarz
     */
    COMMENT,

    /**
     * Uaktualnienie danych klienta
     */
    DATA_UPDATE,

    /**
     * Wizyta terenowa
     */
    FIELD_VISIT,

    /**
     * Wypowiedzenie warunkowe umowy
     */
    CONTRACT_TERMINATION,

    /**
     * Wezwanie do zwrotu przedmiotu
     */
    RETURN_SUBJECT_DEMAND,

    /**
     * Podejrzenie fraudu
     */
    FRAUD_SUSPECTED,

    /**
     * Odbiór przedmiotu leasingu
     */
    SUBJECT_COLLECTED,

    /**
     * Potwierdzenie wpłaty klienta
     */
    PAYMENT_COLLECTED_CONFIRMATION

}