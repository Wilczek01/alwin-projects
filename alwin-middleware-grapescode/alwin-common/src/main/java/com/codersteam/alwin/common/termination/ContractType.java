package com.codersteam.alwin.common.termination;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Dariusz Rackowski
 */
public enum ContractType {
    OPERATIONAL_LEASING("Leasing operacyjny"),
    FINANCIAL_LEASING("Leasing finasowy"),
    LOAN("Pozyczka");

    private final String name;

    ContractType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ContractType byName(final String financingType) {
        if (StringUtils.isBlank(financingType)) {
            return null;
        }
        for (final ContractType value : values()) {
            if (financingType.equalsIgnoreCase(value.name)) {
                return value;
            }
        }
        return null;
    }
}
