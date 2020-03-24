package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.activity.Activity;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.type.IssueState;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Adam Stepnowski
 * @author Michal Horowic
 */
@Audited
@Entity
@Table(name = "issue")
public class Issue {

    @SequenceGenerator(name = "issueSEQ", sequenceName = "issue_id_seq", initialValue = 100, allocationSize = 1)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issueSEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_id", referencedColumnName = "id")
    @NotAudited
    private Operator assignee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @Audited
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    @Audited
    private Contract contract;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "termination_cause", length = 1000)
    private String terminationCause;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "termination_operator_id", referencedColumnName = "id")
    @Audited
    private Operator terminationOperator;

    @Column(name = "termination_date")
    private Date terminationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issue_type_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private IssueType issueType;

    @Column(name = "issue_state", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private IssueState issueState;

    @NotAudited
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "issue_tag",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    /**
     * Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia PLN
     */
    @Column(name = "rpb_pln", nullable = false)
    private BigDecimal rpbPLN;

    /**
     * Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane PLN
     */
    @Column(name = "balance_start_pln", nullable = false)
    private BigDecimal balanceStartPLN;

    /**
     * Aktualne saldo dokumentów, które są przedmiotem zlecenia PLN
     */
    @Column(name = "current_balance_pln")
    private BigDecimal currentBalancePLN;

    /**
     * Suma dokonanych wplat PLN
     */
    @Column(name = "payments_pln")
    private BigDecimal paymentsPLN;

    /**
     * Kapital pozostaly do splaty klienta/umowy w chwili utworzenia zlecenia EUR
     */
    @Column(name = "rpb_eur", nullable = false)
    private BigDecimal rpbEUR;

    /**
     * Saldo zlecenia w chwili rozpoczecia - naleznosci przeterminowane EUR
     */
    @Column(name = "balance_start_eur", nullable = false)
    private BigDecimal balanceStartEUR;

    /**
     * Aktualne saldo dokumentów, które są przedmiotem zlecenia EUR
     */
    @Column(name = "current_balance_eur")
    private BigDecimal currentBalanceEUR;

    /**
     * Suma dokonanych wplat EUR
     */
    @Column(name = "payments_eur")
    private BigDecimal paymentsEUR;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.issue", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<IssueInvoice> issueInvoices;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "excluded_from_stats", nullable = false)
    private Boolean excludedFromStats;

    @Column(name = "exclusion_cause", length = 500)
    private String exclusionCause;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excluding_operator_id", referencedColumnName = "id")
    private Operator excludingOperator;

    @Column(name = "balance_update_date")
    private Date balanceUpdateDate;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_issue_id")
    private Issue parentIssue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    private List<Activity> activities;

    @Column(name = "created_manually", nullable = false)
    private boolean createdManually;

    /**
     * Całkowite saldo zlecenia w chwili rozpoczęcia (zawiera wartości walutowe przeliczone na PLN)
     */
    @Column(name = "total_balance_start_pln")
    private BigDecimal totalBalanceStartPLN;

    /**
     * Całkowite saldo dokumentów, które są przedmiotem zlecenia (zawiera wartości walutowe przeliczone na PLN)
     */
    @Column(name = "total_current_balance_pln")
    private BigDecimal totalCurrentBalancePLN;

    /**
     * Całkowita suma dokonanych wpłat (zawiera wartości walutowe przeliczone na PLN)
     */
    @Column(name = "total_payments_pln")
    private BigDecimal totalPaymentsPLN;

    /**
     * Data, od której jest wyliczane DPD rozpoczęcia dla zlecenia
     */
    @Column(name = "dpd_start_date")
    private Date dpdStartDate;

    /**
     * Data i godzina utworzenia zlecenia
     */
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Operator getAssignee() {
        return assignee;
    }

    public void setAssignee(final Operator assignee) {
        this.assignee = assignee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
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

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(final IssueType issueType) {
        this.issueType = issueType;
    }

    public IssueState getIssueState() {
        return issueState;
    }

    public void setIssueState(final IssueState issueState) {
        this.issueState = issueState;
    }

    public BigDecimal getRpbPLN() {
        return rpbPLN;
    }

    public void setRpbPLN(final BigDecimal rpb) {
        this.rpbPLN = rpb;
    }

    public BigDecimal getBalanceStartPLN() {
        return balanceStartPLN;
    }

    public void setBalanceStartPLN(final BigDecimal balanceStart) {
        this.balanceStartPLN = balanceStart;
    }

    public BigDecimal getCurrentBalancePLN() {
        return currentBalancePLN;
    }

    public void setCurrentBalancePLN(final BigDecimal currentBalancePLN) {
        this.currentBalancePLN = currentBalancePLN;
    }

    public BigDecimal getPaymentsPLN() {
        return paymentsPLN;
    }

    public void setPaymentsPLN(final BigDecimal payments) {
        this.paymentsPLN = payments;
    }

    public List<IssueInvoice> getIssueInvoices() {
        return issueInvoices;
    }

    public void setIssueInvoices(final List<IssueInvoice> issueInvoices) {
        this.issueInvoices = issueInvoices;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }

    public Boolean getExcludedFromStats() {
        return excludedFromStats;
    }

    public void setExcludedFromStats(final Boolean excludedFromStats) {
        this.excludedFromStats = excludedFromStats;
    }

    public String getExclusionCause() {
        return exclusionCause;
    }

    public void setExclusionCause(final String exclusionCause) {
        this.exclusionCause = exclusionCause;
    }

    public Operator getExcludingOperator() {
        return excludingOperator;
    }

    public void setExcludingOperator(final Operator excludingOperator) {
        this.excludingOperator = excludingOperator;
    }

    public Date getBalanceUpdateDate() {
        return balanceUpdateDate;
    }

    public void setBalanceUpdateDate(final Date balanceUpdateDate) {
        this.balanceUpdateDate = balanceUpdateDate;
    }

    public Issue getParentIssue() {
        return parentIssue;
    }

    public void setParentIssue(final Issue parentIssue) {
        this.parentIssue = parentIssue;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(final List<Activity> activities) {
        this.activities = activities;
    }

    public boolean isCreatedManually() {
        return createdManually;
    }

    public void setCreatedManually(final boolean createdManually) {
        this.createdManually = createdManually;
    }

    public Operator getTerminationOperator() {
        return terminationOperator;
    }

    public void setTerminationOperator(final Operator terminationOperator) {
        this.terminationOperator = terminationOperator;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(final Date terminationDate) {
        this.terminationDate = terminationDate;
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

    public BigDecimal getCurrentBalanceEUR() {
        return currentBalanceEUR;
    }

    public void setCurrentBalanceEUR(final BigDecimal currentBalanceEUR) {
        this.currentBalanceEUR = currentBalanceEUR;
    }

    public BigDecimal getPaymentsEUR() {
        return paymentsEUR;
    }

    public void setPaymentsEUR(final BigDecimal paymentsEUR) {
        this.paymentsEUR = paymentsEUR;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
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

    public Date getDpdStartDate() {
        return dpdStartDate;
    }

    public void setDpdStartDate(final Date dpdStartDate) {
        this.dpdStartDate = dpdStartDate;
    }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    @PrePersist
    public void prePersist() {
        this.creationDate = new Date();
    }

    @Override
    public String toString() {
        return "Issue{" +
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
                ", tags=" + tags +
                ", rpbPLN=" + rpbPLN +
                ", balanceStartPLN=" + balanceStartPLN +
                ", currentBalancePLN=" + currentBalancePLN +
                ", paymentsPLN=" + paymentsPLN +
                ", rpbEUR=" + rpbEUR +
                ", balanceStartEUR=" + balanceStartEUR +
                ", currentBalanceEUR=" + currentBalanceEUR +
                ", paymentsEUR=" + paymentsEUR +
                ", issueInvoices=" + issueInvoices +
                ", priority=" + priority +
                ", excludedFromStats=" + excludedFromStats +
                ", exclusionCause='" + exclusionCause + '\'' +
                ", excludingOperator=" + excludingOperator +
                ", balanceUpdateDate=" + balanceUpdateDate +
                ", parentIssue=" + parentIssue +
                ", activities=" + activities +
                ", createdManually=" + createdManually +
                ", totalBalanceStartPLN=" + totalBalanceStartPLN +
                ", totalCurrentBalancePLN=" + totalCurrentBalancePLN +
                ", totalPaymentsPLN=" + totalPaymentsPLN +
                ", dpdStartDate=" + dpdStartDate +
                ", creationDate=" + creationDate +
                '}';
    }
}
