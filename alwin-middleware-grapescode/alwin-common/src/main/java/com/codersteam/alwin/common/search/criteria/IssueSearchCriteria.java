package com.codersteam.alwin.common.search.criteria;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Adam Stepnowski
 */
public class IssueSearchCriteria extends SearchCriteria {

    private Date startStartDate;
    private Date endStartDate;
    private Date startExpirationDate;
    private Date endExpirationDate;
    private BigDecimal startTotalCurrentBalancePLN;
    private BigDecimal endTotalCurrentBalancePLN;
    private Date startActivityDate;
    private Date endActivityDate;
    private Date startPlannedDate;
    private Date endPlannedDate;

    public Date getStartStartDate() {
        return startStartDate;
    }

    public void setStartStartDate(final Date startStartDate) {
        this.startStartDate = startStartDate;
    }

    public Date getEndStartDate() {
        return endStartDate;
    }

    public void setEndStartDate(final Date endStartDate) {
        this.endStartDate = endStartDate;
    }

    public Date getStartExpirationDate() {
        return startExpirationDate;
    }

    public void setStartExpirationDate(final Date startExpirationDate) {
        this.startExpirationDate = startExpirationDate;
    }

    public Date getEndExpirationDate() {
        return endExpirationDate;
    }

    public void setEndExpirationDate(final Date endExpirationDate) {
        this.endExpirationDate = endExpirationDate;
    }

    public Date getStartActivityDate() {
        return startActivityDate;
    }

    public void setStartActivityDate(final Date startActivityDate) {
        this.startActivityDate = startActivityDate;
    }

    public Date getEndActivityDate() {
        return endActivityDate;
    }

    public void setEndActivityDate(final Date endActivityDate) {
        this.endActivityDate = endActivityDate;
    }

    public Date getStartPlannedDate() {
        return startPlannedDate;
    }

    public void setStartPlannedDate(final Date startPlannedDate) {
        this.startPlannedDate = startPlannedDate;
    }

    public Date getEndPlannedDate() {
        return endPlannedDate;
    }

    public void setEndPlannedDate(final Date endPlannedDate) {
        this.endPlannedDate = endPlannedDate;
    }

    public BigDecimal getStartTotalCurrentBalancePLN() {
        return startTotalCurrentBalancePLN;
    }

    public void setStartTotalCurrentBalancePLN(final BigDecimal startTotalCurrentBalancePLN) {
        this.startTotalCurrentBalancePLN = startTotalCurrentBalancePLN;
    }

    public BigDecimal getEndTotalCurrentBalancePLN() {
        return endTotalCurrentBalancePLN;
    }

    public void setEndTotalCurrentBalancePLN(final BigDecimal endTotalCurrentBalancePLN) {
        this.endTotalCurrentBalancePLN = endTotalCurrentBalancePLN;
    }
}

