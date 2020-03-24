package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.model.InvoiceSettlementDto;
import com.codersteam.aida.core.api.service.SettlementService;

import java.util.List;

import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_1;
import static com.codersteam.alwin.testdata.aida.InvoiceSettlementTestData.invoiceSettlementDtos;
import static java.util.Collections.emptyList;

public class SettlementServiceMock implements SettlementService {

    @Override
    public List<InvoiceSettlementDto> findInvoiceSettlementsByInvoiceNumber(String invoiceNo) {
        if (INVOICE_NUMBER_1.equals(invoiceNo)) {
            return invoiceSettlementDtos();
        }
        return emptyList();
    }
}
