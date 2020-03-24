package com.codersteam.alwin.core.api.model.contract;

import com.codersteam.alwin.core.api.model.currency.Currency;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasa obiektu przechowującego zestawienie należności dla umowy
 *
 * @author Tomasz Sliwinski
 */
public class ContractFinancialSummaryDto {

    /**
     * Numer umowy
     */
    private String contractNo;

    /**
     * DPD (ilość dni zaległości) dla umowy.
     * Wyznaczone jako maksymalna wartość DPD faktur dla tej umowy, które to faktury pozostają nieopłacone (Saldo < 0).
     * W przypadku, gdy dla umowy stwierdzono jedynie takie faktury nieopłacone, których termin zapłaty jeszcze nie upłynął, DPD powinno przyjąć wartość ujemną.
     * W przypadku, gdy dla umowy nie stwierdzono faktur nieopłaconych, zwracany jest null.
     */
    private Long dpd;

    /**
     * Czy dana umowa jest wyłączona z obsługi
     */
    private Boolean excluded;

    private Map<Currency, FinancialSummaryDto> currencyToSummary = new HashMap<>();

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getDpd() {
        return dpd;
    }

    public void setDpd(final Long dpd) {
        this.dpd = dpd;
    }

    public Map<Currency, FinancialSummaryDto> getCurrencyToSummary() {
        return currencyToSummary;
    }

    public void setCurrencyToSummary(final Map<Currency, FinancialSummaryDto> currencyToSummary) {
        this.currencyToSummary = currencyToSummary;
    }

    public Boolean getExcluded() {
        return excluded;
    }

    public void setExcluded(final Boolean excluded) {
        this.excluded = excluded;
    }
}
