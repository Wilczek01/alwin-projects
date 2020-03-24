package com.codersteam.alwin.common.termination;

import java.util.Date;
import java.util.Objects;

public class ContractTerminationGroupKey {
    private Date terminationDate;
    private Long extCompanyId;
    private ContractTerminationType type;

    public ContractTerminationGroupKey(final Date terminationDate, final Long extCompanyId, final ContractTerminationType type) {
        this.terminationDate = terminationDate;
        this.extCompanyId = extCompanyId;
        this.type = type;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(final Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(final Long extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public ContractTerminationType getType() {
        return type;
    }

    public void setType(final ContractTerminationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContractTerminationGroupKey that = (ContractTerminationGroupKey) o;
        return Objects.equals(terminationDate, that.terminationDate) &&
                Objects.equals(extCompanyId, that.extCompanyId) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminationDate, extCompanyId, type);
    }
}
