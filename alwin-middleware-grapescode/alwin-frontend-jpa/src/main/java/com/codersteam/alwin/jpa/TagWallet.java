package com.codersteam.alwin.jpa;

import com.codersteam.alwin.jpa.issue.Tag;

import java.math.BigDecimal;

/**
 * Portfele zleceń liczone dla zleceń przypisanych do etykiety.
 * Portfele są liczone z otwartych zleceń windykacyjnych.
 *
 * @author Piotr Naroznik
 */
public class TagWallet extends Wallet {

    /**
     * Etykieta dla której liczony jest portfel
     */
    private final Tag tag;

    public TagWallet(final Long companyCount, final BigDecimal involvement, final BigDecimal balanceStartPLN,
                     final BigDecimal balanceStartEUR, final Tag tag) {
        super(companyCount, involvement, balanceStartPLN, balanceStartEUR);
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "TagWallet{" +
                "tag=" + tag +
                "} " + super.toString();
    }
}
