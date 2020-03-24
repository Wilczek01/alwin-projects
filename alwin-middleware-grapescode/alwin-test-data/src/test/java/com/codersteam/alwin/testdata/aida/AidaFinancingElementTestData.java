package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.FinancingElementDto;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public final class AidaFinancingElementTestData {

    private static final Long FINANCING_ELEMENT_ID_1 = 1L;
    private static final String FINANCING_ELEMENT_KIND_1 = "Raty";

    private static final Long FINANCING_ELEMENT_ID_2 = 2L;
    private static final String FINANCING_ELEMENT_KIND_2 = "Oplata Wstepna";

    private static final String REPURCHASE_FINANCING_ELEMENT_KIND = "Wykup";

    public static final FinancingElementDto FINANCING_ELEMENT_DTO_1 = financingElementDto(FINANCING_ELEMENT_ID_1, FINANCING_ELEMENT_KIND_1);
    public static final FinancingElementDto FINANCING_ELEMENT_DTO_2 = financingElementDto(FINANCING_ELEMENT_ID_2, FINANCING_ELEMENT_KIND_2);
    public static final FinancingElementDto REPURCHASE_FINANCING_ELEMENT_DTO = financingElementDto(FINANCING_ELEMENT_ID_2, REPURCHASE_FINANCING_ELEMENT_KIND);

    private AidaFinancingElementTestData() {
    }

    private static FinancingElementDto financingElementDto(final Long id, final String paymentKind) {
        final FinancingElementDto financingElement = new FinancingElementDto();
        financingElement.setId(id);
        financingElement.setPaymentKind(paymentKind);
        return financingElement;
    }
}
