package com.codersteam.alwin.common.sort;

/**
 * Nazwy kolumn dostępnych do sortowania dla wezwań do zapłaty
 *
 * @author Michal Horowic
 */
public enum ContractTerminationSortField {

    ID("id"),
    EXT_COMPANY_ID("extCompanyId"),
    TYPE("type"),
    COMPANY_NAME("companyName"),
    TERMINATION_DATE("terminationDate");

    private final String column;

    ContractTerminationSortField(final String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
