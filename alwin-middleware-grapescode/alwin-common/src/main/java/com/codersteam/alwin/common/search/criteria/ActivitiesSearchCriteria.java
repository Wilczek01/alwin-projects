package com.codersteam.alwin.common.search.criteria;

import com.codersteam.alwin.common.issue.IssueTypeName;

import java.util.Date;
import java.util.List;

/**
 * Kryteria wyszukiwania czynności dla zlecenia
 *
 * @author Michal Horowic
 */
public class ActivitiesSearchCriteria extends SearchCriteria {

    /**
     * Identyfikator firmy
     */
    private Long companyId;

    /**
     * Identyfikator operatora
     */
    private Long operatorId;

    /**
     * Data utworzenia czynności OD
     */
    private Date startCreationDate;

    /**
     * Data utworzenia czynności DO
     */
    private Date endCreationDate;

    /**
     * Typ czynności
     */
    private String type;

    /**
     * Komentarz
     */
    private String remark;

    /**
     * Lista statusów czynności
     */
    private List<String> states;

    /**
     * Typ zlecenia, z którego czynność pochodzi
     */
    private IssueTypeName issueType;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public boolean showAllForCompany() {
        return companyId != null;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(final Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getStartCreationDate() {
        return startCreationDate;
    }

    public void setStartCreationDate(final Date startCreationDate) {
        this.startCreationDate = startCreationDate;
    }

    public Date getEndCreationDate() {
        return endCreationDate;
    }

    public void setEndCreationDate(final Date endCreationDate) {
        this.endCreationDate = endCreationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(final List<String> states) {
        this.states = states;
    }

    public IssueTypeName getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueTypeName issueType) {
        this.issueType = issueType;
    }

    @Override
    public String toString() {
        return "ActivitiesSearchCriteria{" +
                "companyId=" + companyId +
                ", operatorId=" + operatorId +
                ", startCreationDate=" + startCreationDate +
                ", endCreationDate=" + endCreationDate +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", states=" + states +
                ", issueType=" + issueType +
                '}';
    }
}

