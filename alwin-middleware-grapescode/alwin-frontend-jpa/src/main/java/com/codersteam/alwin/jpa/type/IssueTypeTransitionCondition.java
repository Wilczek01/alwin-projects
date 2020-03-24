package com.codersteam.alwin.jpa.type;

/**
 * Warunki który trzeba spełnić, aby przejście miedzy zleceniamy było możliwe
 *
 * @author Piotr Naroznik
 */
public enum IssueTypeTransitionCondition {

    /**
     * czy przedmiot został zabezpieczony
     */
    NONE,

    /**
     * czy przedmiot został zabezpieczony
     */
    SECURED_ITEM,

    /**
     * czy przedmiot nie został zabezpieczony
     */
    NON_SECURED_ITEM

}
