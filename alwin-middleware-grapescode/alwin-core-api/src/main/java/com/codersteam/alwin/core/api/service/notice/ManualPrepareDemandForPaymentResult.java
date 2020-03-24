package com.codersteam.alwin.core.api.service.notice;

import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResultCode.CONTRACT_ALREADY_IN_TERMINATION_STAGE;
import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResultCode.CONTRACT_NOT_FOUND;
import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResultCode.FAILED;
import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResultCode.NO_DUE_INVOICES_FOR_CONTRACT;
import static com.codersteam.alwin.core.api.service.notice.ManualPrepareDemandForPaymentResultCode.SUCCESSFUL;

/**
 * Rezultat przygotowania wezwania do zapłaty w procesie manualnym
 *
 * @author Tomasz Sliwinski
 */
public class ManualPrepareDemandForPaymentResult {

    /**
     * Numer umowy
     */
    private String contractNumber;

    /**
     * Kod rezultatu
     */
    private final ManualPrepareDemandForPaymentResultCode resultCode;

    /**
     * Dodatkowa informacja odnośnie rezultatu tworzenia wezwania do zapłaty
     */
    private String message;

    public ManualPrepareDemandForPaymentResult(final String contractNumber, final ManualPrepareDemandForPaymentResultCode resultCode) {
        this.contractNumber = contractNumber;
        this.resultCode = resultCode;
    }

    public ManualPrepareDemandForPaymentResult(final ManualPrepareDemandForPaymentResultCode resultCode, final String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public static ManualPrepareDemandForPaymentResult successful(final String contractNumber) {
        return new ManualPrepareDemandForPaymentResult(contractNumber, SUCCESSFUL);
    }

    public static ManualPrepareDemandForPaymentResult noDueInvoicesForContract(final String contractNumber) {
        return new ManualPrepareDemandForPaymentResult(contractNumber, NO_DUE_INVOICES_FOR_CONTRACT);
    }

    public static ManualPrepareDemandForPaymentResult contractAlreadyInTerminationStage(final String contractNumber) {
        return new ManualPrepareDemandForPaymentResult(contractNumber, CONTRACT_ALREADY_IN_TERMINATION_STAGE);
    }

    public static ManualPrepareDemandForPaymentResult failed(final String contractNumber) {
        return new ManualPrepareDemandForPaymentResult(contractNumber, FAILED);
    }

    public static ManualPrepareDemandForPaymentResult noContractFound(final String contractNumber) {
        return new ManualPrepareDemandForPaymentResult(contractNumber, CONTRACT_NOT_FOUND);
    }

    public ManualPrepareDemandForPaymentResultCode getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    @Override
    public String toString() {
        return "ManualPrepareDemandForPaymentResult{" +
                "contractNumber='" + contractNumber + '\'' +
                ", resultCode=" + resultCode +
                ", message='" + message + '\'' +
                '}';
    }
}
