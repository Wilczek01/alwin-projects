package com.codersteam.alwin.core.api.model.issue;

import com.codersteam.alwin.core.api.model.contract.ContractDto;
import com.codersteam.alwin.core.api.model.customer.CustomerDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.model.tag.TagDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Michal Horowic
 */
public class IssueDto {
    private Long id;
    private OperatorUserDto assignee;
    private CustomerDto customer;
    private ContractDto contract;
    private Date startDate;
    private Date expirationDate;
    private String terminationCause;
    private OperatorUserDto terminationOperator;
    private Date terminationDate;
    private IssueTypeDto issueType;
    private IssueStateDto issueState;
    private BigDecimal rpbPLN;
    private BigDecimal balanceStartPLN;
    private BigDecimal paymentsPLN;
    private BigDecimal currentBalancePLN;
    private BigDecimal rpbEUR;
    private BigDecimal balanceStartEUR;
    private BigDecimal paymentsEUR;
    private BigDecimal currentBalanceEUR;
    private BigDecimal totalBalanceStartPLN;
    private BigDecimal totalCurrentBalancePLN;
    private BigDecimal totalPaymentsPLN;
    private Boolean excludedFromStats;
    private String exclusionCause;
    private Date balanceUpdateDate;
    private IssuePriorityDto priority;
    private List<TagDto> tags;

    /**
     * DPD utworzenia zlecenia
     */
    private Long dpdStart;

    /**
     * Szacowane DPD na dzień zakończenia zlecenia
     */
    private Long dpdEstimated;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public OperatorUserDto getAssignee() {
        return assignee;
    }

    public void setAssignee(final OperatorUserDto assignee) {
        this.assignee = assignee;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(final CustomerDto customer) {
        this.customer = customer;
    }

    public ContractDto getContract() {
        return contract;
    }

    public void setContract(final ContractDto contract) {
        this.contract = contract;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getTerminationCause() {
        return terminationCause;
    }

    public void setTerminationCause(final String terminationCause) {
        this.terminationCause = terminationCause;
    }

    public IssueTypeDto getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueTypeDto issueType) {
        this.issueType = issueType;
    }

    public IssueStateDto getIssueState() {
        return issueState;
    }

    public void setIssueState(final IssueStateDto issueState) {
        this.issueState = issueState;
    }

    public BigDecimal getRpbPLN() {
        return rpbPLN;
    }

    public void setRpbPLN(final BigDecimal rpbPLN) {
        this.rpbPLN = rpbPLN;
    }

    public BigDecimal getBalanceStartPLN() {
        return balanceStartPLN;
    }

    public void setBalanceStartPLN(final BigDecimal balanceStartPLN) {
        this.balanceStartPLN = balanceStartPLN;
    }

    public BigDecimal getPaymentsPLN() {
        return paymentsPLN;
    }

    public void setPaymentsPLN(final BigDecimal paymentsPLN) {
        this.paymentsPLN = paymentsPLN;
    }

    public Boolean getExcludedFromStats() {
        return excludedFromStats;
    }

    public void setExcludedFromStats(final Boolean excludedFromStats) {
        this.excludedFromStats = excludedFromStats;
    }

    public Date getBalanceUpdateDate() {
        return balanceUpdateDate;
    }

    public void setBalanceUpdateDate(final Date balanceUpdateDate) {
        this.balanceUpdateDate = balanceUpdateDate;
    }

    public IssuePriorityDto getPriority() {
        return priority;
    }

    public void setPriority(final IssuePriorityDto priority) {
        this.priority = priority;
    }

    public OperatorUserDto getTerminationOperator() {
        return terminationOperator;
    }

    public void setTerminationOperator(final OperatorUserDto terminationOperator) {
        this.terminationOperator = terminationOperator;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(final Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getExclusionCause() {
        return exclusionCause;
    }

    public void setExclusionCause(final String exclusionCause) {
        this.exclusionCause = exclusionCause;
    }

    public BigDecimal getRpbEUR() {
        return rpbEUR;
    }

    public void setRpbEUR(final BigDecimal rpbEUR) {
        this.rpbEUR = rpbEUR;
    }

    public BigDecimal getBalanceStartEUR() {
        return balanceStartEUR;
    }

    public void setBalanceStartEUR(final BigDecimal balanceStartEUR) {
        this.balanceStartEUR = balanceStartEUR;
    }

    public BigDecimal getPaymentsEUR() {
        return paymentsEUR;
    }

    public void setPaymentsEUR(final BigDecimal paymentsEUR) {
        this.paymentsEUR = paymentsEUR;
    }

    public BigDecimal getCurrentBalancePLN() {
        return currentBalancePLN;
    }

    public void setCurrentBalancePLN(final BigDecimal currentBalancePLN) {
        this.currentBalancePLN = currentBalancePLN;
    }

    public BigDecimal getCurrentBalanceEUR() {
        return currentBalanceEUR;
    }

    public void setCurrentBalanceEUR(final BigDecimal currentBalanceEUR) {
        this.currentBalanceEUR = currentBalanceEUR;
    }

    public BigDecimal getTotalBalanceStartPLN() {
        return totalBalanceStartPLN;
    }

    public void setTotalBalanceStartPLN(final BigDecimal totalBalanceStartPLN) {
        this.totalBalanceStartPLN = totalBalanceStartPLN;
    }

    public BigDecimal getTotalCurrentBalancePLN() {
        return totalCurrentBalancePLN;
    }

    public void setTotalCurrentBalancePLN(final BigDecimal totalCurrentBalancePLN) {
        this.totalCurrentBalancePLN = totalCurrentBalancePLN;
    }

    public BigDecimal getTotalPaymentsPLN() {
        return totalPaymentsPLN;
    }

    public void setTotalPaymentsPLN(final BigDecimal totalPaymentsPLN) {
        this.totalPaymentsPLN = totalPaymentsPLN;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(final List<TagDto> tags) {
        this.tags = tags;
    }

    public Long getDpdStart() {
        return dpdStart;
    }

    public void setDpdStart(final Long dpdStart) {
        this.dpdStart = dpdStart;
    }

    public Long getDpdEstimated() {
        return dpdEstimated;
    }

    public void setDpdEstimated(final Long dpdEstimated) {
        this.dpdEstimated = dpdEstimated;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "id=" + id +
                ", assignee=" + assignee +
                ", customer=" + customer +
                ", contract=" + contract +
                ", startDate=" + startDate +
                ", expirationDate=" + expirationDate +
                ", terminationCause='" + terminationCause + '\'' +
                ", terminationOperator=" + terminationOperator +
                ", terminationDate=" + terminationDate +
                ", issueType=" + issueType +
                ", issueState=" + issueState +
                ", rpbPLN=" + rpbPLN +
                ", balanceStartPLN=" + balanceStartPLN +
                ", paymentsPLN=" + paymentsPLN +
                ", currentBalancePLN=" + currentBalancePLN +
                ", rpbEUR=" + rpbEUR +
                ", balanceStartEUR=" + balanceStartEUR +
                ", paymentsEUR=" + paymentsEUR +
                ", currentBalanceEUR=" + currentBalanceEUR +
                ", totalBalanceStartPLN=" + totalBalanceStartPLN +
                ", totalCurrentBalancePLN=" + totalCurrentBalancePLN +
                ", totalPaymentsPLN=" + totalPaymentsPLN +
                ", excludedFromStats=" + excludedFromStats +
                ", exclusionCause='" + exclusionCause + '\'' +
                ", balanceUpdateDate=" + balanceUpdateDate +
                ", priority=" + priority +
                ", tags=" + tags +
                ", dpdStart=" + dpdStart +
                ", dpdEstimated=" + dpdEstimated +
                '}';
    }
}
