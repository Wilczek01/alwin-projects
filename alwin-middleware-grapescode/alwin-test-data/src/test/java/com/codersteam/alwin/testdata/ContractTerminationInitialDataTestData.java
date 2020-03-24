package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;
import com.codersteam.alwin.core.api.service.termination.ContractTerminationInitialData;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto1;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto2;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto3;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto4;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueSimpleInvoice1;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.dueSimpleInvoice2;
import static java.util.Arrays.asList;

/**
 * @author Tomasz Sliwinski
 */
public final class ContractTerminationInitialDataTestData {

    private ContractTerminationInitialDataTestData() {
    }

    public static ContractTerminationInitialData contractTerminationInitialData1() {
        return contractTerminationInitialData(dueSimpleInvoice1().getCompanyId(), dueSimpleInvoice1().getContractNo(),
                formalDebtCollectionInvoiceDto1().getInvoiceNumber(), parse("2018-03-12"),
                asList(formalDebtCollectionInvoiceDto1(), formalDebtCollectionInvoiceDto2()), DEMAND_FOR_PAYMENT_ID_1);
    }

    public static ContractTerminationInitialData contractTerminationInitialData2() {
        return contractTerminationInitialData(dueSimpleInvoice2().getCompanyId(), dueSimpleInvoice2().getContractNo(),
                formalDebtCollectionInvoiceDto4().getInvoiceNumber(), parse("2018-05-22"),
                asList(formalDebtCollectionInvoiceDto3(), formalDebtCollectionInvoiceDto4()), null);
    }

    private static ContractTerminationInitialData contractTerminationInitialData(final Long extCompanyId, final String contractNumber,
                                                                                 final String initialInvoiceNo, final Date dueDateInDemandForPayment,
                                                                                 final List<FormalDebtCollectionInvoiceDto> invoices, final Long precedingDemandForPaymentId) {
        final ContractTerminationInitialData contractTerminationInitialData = new ContractTerminationInitialData();
        contractTerminationInitialData.setExtCompanyId(extCompanyId);
        contractTerminationInitialData.setContractNumber(contractNumber);
        contractTerminationInitialData.setInitialInvoiceNo(initialInvoiceNo);
        contractTerminationInitialData.setDueDateInDemandForPayment(dueDateInDemandForPayment);
        contractTerminationInitialData.setInvoices(new HashSet<>(invoices));
        contractTerminationInitialData.setPrecedingDemandForPaymentId(precedingDemandForPaymentId);
        return contractTerminationInitialData;
    }
}