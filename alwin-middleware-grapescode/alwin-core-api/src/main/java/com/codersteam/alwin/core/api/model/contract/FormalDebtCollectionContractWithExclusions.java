package com.codersteam.alwin.core.api.model.contract;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceDto;

import java.util.List;

/**
 * Umowa klienta rozszerzona o blokady w windykacji formalnej
 *
 * @author Piotr Naroznik
 */
public class FormalDebtCollectionContractWithExclusions extends AidaContractDto {

    private List<FormalDebtCollectionContractOutOfServiceDto> exclusions;
    private boolean excluded;

    public List<FormalDebtCollectionContractOutOfServiceDto> getExclusions() {
        return exclusions;
    }

    public void setExclusions(final List<FormalDebtCollectionContractOutOfServiceDto> exclusions) {
        this.exclusions = exclusions;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(final boolean excluded) {
        this.excluded = excluded;
    }
}
