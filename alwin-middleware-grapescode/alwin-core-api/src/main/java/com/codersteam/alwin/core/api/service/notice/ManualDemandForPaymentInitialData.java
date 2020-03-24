package com.codersteam.alwin.core.api.service.notice;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;

/**
 * Zestaw danych do rozpoczęcia manualnego procesu wystawienia wezwania określonego typu dla umowy
 *
 * @author Tomasz Sliwinski
 */
public class ManualDemandForPaymentInitialData {

    /**
     * Typ wezwania
     */
    private final DemandForPaymentTypeKey typeKey;

    /**
     * Identyfikator firmy w systemie AIDA
     */
    private final Long extCompanyId;

    /**
     * Numer umowy
     */
    private final String contractNo;

    public ManualDemandForPaymentInitialData(final DemandForPaymentTypeKey typeKey, final Long extCompanyId, final String contractNo) {
        this.typeKey = typeKey;
        this.extCompanyId = extCompanyId;
        this.contractNo = contractNo;
    }

    public DemandForPaymentTypeKey getTypeKey() {
        return typeKey;
    }

    public Long getExtCompanyId() {
        return extCompanyId;
    }

    public String getContractNo() {
        return contractNo;
    }

    @Override
    public String toString() {
        return "ManualDemandForPaymentInitialData{" +
                "type=" + typeKey +
                ", extCompanyId=" + extCompanyId +
                ", contractNo='" + contractNo + '\'' +
                '}';
    }
}
