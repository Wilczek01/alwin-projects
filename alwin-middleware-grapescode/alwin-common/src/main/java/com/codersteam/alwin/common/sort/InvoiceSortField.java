package com.codersteam.alwin.common.sort;

/**
 * Nazwy kolumn dostępnych do sortowania dla faktur zlecenia
 *
 * @author Michal Horowic
 */
public enum InvoiceSortField {
    ID("id"),
    DUE_DATE("realDueDate"),
    ISSUE_DATE("issueDate"),
    LAST_PAYMENT_DATE("lastPaymentDate"),
    CONTRACT_NUMBER("contractNumber"),
    NUMBER("number"),
    CURRENCY("currency"),
    CURRENT_BALANCE("currentBalance"),
    PAID("paid"),
    EXCLUDED("excluded"),
    GROSS_AMOUNT("grossAmount");

    private final String column;

    InvoiceSortField(final String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
