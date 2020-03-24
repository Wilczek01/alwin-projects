package com.codersteam.alwin.jpa.customer;

import com.codersteam.alwin.jpa.operator.Operator;

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
import java.util.Date;

/**
 * @author Michal Horowic
 */

@Entity
@Table(name = "contract_out_of_service")
public class ContractOutOfService {

    @SequenceGenerator(name = "contract_out_of_serviceSEQ", sequenceName = "contract_out_of_service_id_seq", allocationSize = 1, initialValue = 50)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_out_of_serviceSEQ")
    private Long id;

    @Column(name = "contract_no", length = 30, nullable = false)
    private String contractNo;

    @Column(name = "ext_company_id", nullable = false)
    private Long extCompanyId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blocking_operator_id", referencedColumnName = "id", nullable = false)
    private Operator blockingOperator;

    @Column(name = "reason")
    private String reason;

    @Column(name = "remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String extContractId) {
        this.contractNo = extContractId;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
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
}
