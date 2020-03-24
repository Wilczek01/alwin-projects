package com.codersteam.alwin.jpa.customer;

import com.codersteam.alwin.jpa.operator.Operator;
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
@Table(name = "customer")
public class Customer {

    @SequenceGenerator(name = "customerSEQ", sequenceName = "customer_id_seq", allocationSize = 1, initialValue = 50)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "debt_segment")
    private String debtSegment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_manager_id", referencedColumnName = "id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Operator accountManager;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public String getDebtSegment() {
        return debtSegment;
    }

    public void setDebtSegment(final String debtSegment) {
        this.debtSegment = debtSegment;
    }

    public Operator getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(final Operator accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", company=" + company +
                ", person=" + person +
                ", debtSegment='" + debtSegment + '\'' +
                ", accountManager='" + accountManager + '\'' +
                '}';
    }
}
