package com.codersteam.alwin.core.api.service.notice;

/**
 * Kod rezultatu tworzenia wezwania do zapłaty w procesie manualnym
 *
 * @author Tomasz Sliwinski
 */
public enum ManualPrepareDemandForPaymentResultCode {

    /**
     * Utworzono nowe wezwanie
     */
    SUCCESSFUL,

    /**
     * Wystąpił błąd systemu w trakcie tworzenia wezwania
     */
    FAILED,

    /**
     * Brak dłużnych dokumentów dla umowy
     */
    NO_DUE_INVOICES_FOR_CONTRACT,

    /**
     * Umowa jest już na etapie wypowiedzeń
     */
    CONTRACT_ALREADY_IN_TERMINATION_STAGE,

    /**
     * Nie znaleziono umowy
     */
    CONTRACT_NOT_FOUND,
}
