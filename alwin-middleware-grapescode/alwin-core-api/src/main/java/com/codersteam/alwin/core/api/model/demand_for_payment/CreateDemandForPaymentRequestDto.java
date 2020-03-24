package com.codersteam.alwin.core.api.model.demand_for_payment;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;

import java.util.List;

/**
 * Obiekt żądania utworzenia wezwań do zapłaty
 *
 * @author Tomasz Sliwinski
 */
public class CreateDemandForPaymentRequestDto {

    /**
     * Klucz typu wezwania do utworzenia
     */
    private DemandForPaymentTypeKey typeKey;

    /**
     * Numery umów, dla których należy utworzyć wezwania
     */
    private List<String> contractNumbers;

    public DemandForPaymentTypeKey getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(final DemandForPaymentTypeKey typeKey) {
        this.typeKey = typeKey;
    }

    public List<String> getContractNumbers() {
        return contractNumbers;
    }

    public void setContractNumbers(final List<String> contractNumbers) {
        this.contractNumbers = contractNumbers;
    }
}
