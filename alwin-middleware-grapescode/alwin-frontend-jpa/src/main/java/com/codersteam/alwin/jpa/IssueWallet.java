package com.codersteam.alwin.jpa;

import com.codersteam.alwin.common.issue.Segment;
import com.codersteam.alwin.jpa.issue.IssueType;

import java.math.BigDecimal;

/**
 * Portfel zleceń liczony po typie zlecenia oraz segmencie klienta.
 * Portfele są liczone tylko dla podanych w dao statusów zleceń.
 * Dlatego ten sam typ zlecenia i segment klienta może mieć więcej portfeli.
 *
 * @author Piotr Naroznik
 */
public class IssueWallet extends Wallet {

    /**
     * Typ zlecenia
     */
    private IssueType issueType;

    /**
     * Segment klienta
     */
    private Segment segment;

    /**
     * Czas trwania zleceń w portfelu
     */
    private String duration;

    public IssueWallet(final Long companyCount, final BigDecimal involvement, final BigDecimal balanceStartPLN,
                       final BigDecimal balanceStartEUR) {
        super(companyCount, involvement, balanceStartPLN, balanceStartEUR);
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public Segment getSegment() {
        return segment;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    public void setIssueType(final IssueType issueType) {
        this.issueType = issueType;
    }

    public void setSegment(final Segment segment) {
        this.segment = segment;
    }
}
