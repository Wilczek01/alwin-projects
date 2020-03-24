package com.codersteam.alwin.jpa.issue;

import com.codersteam.alwin.jpa.customer.Customer;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Adam Stepnowski
 */
@Audited
@Entity
@Table(name = "contract")
public class Contract {

    @SequenceGenerator(name = "contractSEQ", sequenceName = "contract_id_seq", allocationSize = 1, initialValue = 100)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractSEQ")
    private Long id;

    @Column(name = "ext_contract_id", length = 30, nullable = false)
    private String extContractId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getExtContractId() {
        return extContractId;
    }

    public void setExtContractId(final String contractId) {
        this.extContractId = contractId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }
}
