package com.codersteam.alwin.jpa.termination;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.operator.Operator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Piotr Naroznik
 */

@Entity
@Table(name = "formal_debt_collection_customer_out_of_service")
public class FormalDebtCollectionCustomerOutOfService {

    @SequenceGenerator(name = "formal_debt_collection_customer_out_of_serviceSEQ", sequenceName = "formal_debt_collection_customer_out_of_service_id_seq")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formal_debt_collection_customer_out_of_serviceSEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blocking_operator_id", referencedColumnName = "id", nullable = false)
    private Operator blockingOperator;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "remark")
    private String remark;

    @Column(name = "reason_type")
    @Enumerated(EnumType.STRING)
    private ReasonType reasonType;

    @Column(name = "demand_for_payment_type")
    @Enumerated(EnumType.STRING)
    private DemandForPaymentTypeKey demandForPaymentType;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Operator getBlockingOperator() {
        return blockingOperator;
    }

    public void setBlockingOperator(final Operator blockingOperatorId) {
        this.blockingOperator = blockingOperatorId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(final ReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public DemandForPaymentTypeKey getDemandForPaymentType() {
        return demandForPaymentType;
    }

    public void setDemandForPaymentType(final DemandForPaymentTypeKey demandType) {
        this.demandForPaymentType = demandType;
    }
}
