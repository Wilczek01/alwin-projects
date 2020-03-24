package com.codersteam.alwin.core.api.model.contract;

import com.codersteam.aida.core.api.model.AidaContractDto;
import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceDto;

import java.util.List;

/**
 * Umowa klienta rozszerzona o blokady
 *
 * @author Michal Horowic
 */
public class ContractWithExclusions extends AidaContractDto {

    private List<ContractOutOfServiceDto> exclusions;
    private boolean excluded;

    public List<ContractOutOfServiceDto> getExclusions() {
        return exclusions;
    }

    public void setExclusions(final List<ContractOutOfServiceDto> exclusions) {
        this.exclusions = exclusions;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(final boolean excluded) {
        this.excluded = excluded;
    }
}
