package com.codersteam.alwin.common.issue;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Typ zlecenia
 *
 * @author Piotr Naroznik
 */
public enum IssueTypeName {

    /**
     * Windykacja telefoniczna sekcja 1
     */
    PHONE_DEBT_COLLECTION_1,

    /**
     * Windykacja telefoniczna sekcja 2
     */
    PHONE_DEBT_COLLECTION_2,

    /**
     * Windykacja bezpośrednia
     */
    DIRECT_DEBT_COLLECTION,

    /**
     * Windykacja prawna - pozew o wydanie przedmiotu
     */
    LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM,

    /**
     * Transport przedmiotu
     */
    SUBJECT_TRANSPORT,

    /**
     * Realizacja zabezpieczenia
     */
    REALIZATION_OF_COLLATERAL,

    /**
     * Windykacja prawna
     */
    LAW_DEBT_COLLECTION_MOTION_TO_PAY,

    /**
     * Restrukturyzacja
     */
    RESTRUCTURING;

    /**
     * Typy windykacji telefonicznej
     */
    public static List<IssueTypeName> PHONE_DEBT_COLLECTION = asList(PHONE_DEBT_COLLECTION_1, PHONE_DEBT_COLLECTION_2);

}
