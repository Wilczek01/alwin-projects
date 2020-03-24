package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.LegalFormDto;

/**
 * @author Tomasz Sliwinski
 */
class AidaLegalFormTestData {

    private static final Long LEGAL_FORM_ID_1 = 1L;
    private static final String LEGAL_FORM_SYMBOL_1 = "Spółka z o.o.";

    private static final Long LEGAL_FORM_ID_3 = 3L;
    private static final String LEGAL_FORM_SYMBOL_3 = "Spółka cywilna";

    static LegalFormDto legalFormDto1() {
        return legalFormDto(LEGAL_FORM_ID_1, LEGAL_FORM_SYMBOL_1);
    }

    static LegalFormDto legalFormDto3() {
        return legalFormDto(LEGAL_FORM_ID_3, LEGAL_FORM_SYMBOL_3);
    }

    private static LegalFormDto legalFormDto(final Long id, final String symbol) {
        final LegalFormDto legalFormDto = new LegalFormDto();
        legalFormDto.setId(id);
        legalFormDto.setSymbol(symbol);
        return legalFormDto;
    }
}
