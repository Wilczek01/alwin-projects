package com.codersteam.alwin.common.search.param;

/**
 * @author Michal Horowic
 */
public enum ContractTerminationCriteriaParams {

    ID("id"),
    STATE("state"),
    CONTRACT_NO("contractNumber"),
    REMARK("remark"),
    GENERATED_BY("generatedBy"),
    RESUMPTION_COST("resumptionCost"),
    DUE_DATE_IN_DEMAND_FOR_PAYMENT("dueDateInDemandForPayment"),
    AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN("amountInDemandForPaymentPLN"),
    AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR("amountInDemandForPaymentEUR"),
    TOTAL_PAYMENT_SINCE_DEMAND_FOR_PAYMENT_PLN("totalPaymentsSinceDemandForPaymentPLN"),
    TOTAL_PAYMENT_SINCE_DEMAND_FOR_PAYMENT_EUR("totalPaymentsSinceDemandForPaymentEUR"),
    INITIAL_INVOICE_NO("invoiceNumberInitiatingDemandForPayment"),
    EXT_COMPANY_ID("extCompanyId"),
    COMPANY_NAME("companyName"),
    TERMINATION_DATE("terminationDate"),
    TYPE("type"),
    STATES("states"),
    CONTRACT_TERMINATION_IDS("contractTerminationIds");

    public final String name;

    ContractTerminationCriteriaParams(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
