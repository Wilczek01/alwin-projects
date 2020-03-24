package com.codersteam.alwin.core.api.model.customer;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;

/**
 * Blokowanie umowy w windykacji formalnej
 *
 * @author Piotr Naroznik
 */

public class FormalDebtCollectionContractOutOfServiceInputDto extends ContractOutOfServiceInputDto {

    /**
     * Wybrana przyczyna wykluczenia
     */
    private ReasonType reasonType;

    /**
     * Typ wezwania do zapłaty, dla której zostaje wykluczony klient
     */
    private DemandForPaymentTypeKey demandForPaymentType;

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(final ReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public DemandForPaymentTypeKey getDemandForPaymentType() {
        return demandForPaymentType;
    }

    public void setDemandForPaymentType(final DemandForPaymentTypeKey demandForPaymentType) {
        this.demandForPaymentType = demandForPaymentType;
    }
}
