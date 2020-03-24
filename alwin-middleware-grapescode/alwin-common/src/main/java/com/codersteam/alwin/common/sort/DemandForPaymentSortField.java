package com.codersteam.alwin.common.sort;

/**
 * Nazwy kolumn dostępnych do sortowania dla wezwań do zapłaty
 *
 * @author Michal Horowic
 */
public enum DemandForPaymentSortField {

    ID("id"),
    STATE("state"),
    TYPE("key"),
    CONTRACT_NUMBER("contractNumber"),
    ISSUE_DATE("issueDate"),
    DUE_DATE("dueDate"),
    INITIAL_INVOICE_NO("initialInvoiceNo"),
    EXT_COMPANY_ID("extCompanyId"),
    COMPANY_NAME("companyName"),
    SEGMENT("segment"),
    CHARGE_INVOICE_NO("chargeInvoiceNo");

    private final String column;

    DemandForPaymentSortField(final String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
