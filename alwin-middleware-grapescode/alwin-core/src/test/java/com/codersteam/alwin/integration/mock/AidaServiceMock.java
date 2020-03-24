package com.codersteam.alwin.integration.mock;

import com.codersteam.aida.core.api.service.*;
import com.codersteam.alwin.core.api.service.AidaService;

import javax.ejb.Stateless;

@Stateless
public class AidaServiceMock implements AidaService {

    private final InvoiceService invoiceService;
    private final ContractService contractService;
    private final CompanyService aidaCompanyService;
    private final PersonService aidaPersonService;
    private final SettlementService settlementService;
    private final InvolvementService involvementService;
    private final SegmentService segmentService;
    private final CurrencyExchangeRateService currencyExchangeRateService;
    private final ExcessPaymentService excessPaymentService;
    private final ContractSubjectService contractSubjectService;

    public AidaServiceMock() {
        invoiceService = new InvoiceServiceMock();
        contractService = new ContractServiceMock();
        aidaCompanyService = new CompanyServiceMock();
        aidaPersonService = new PersonServiceMock();
        settlementService = new SettlementServiceMock();
        involvementService = new InvolvementServiceMock();
        segmentService = new SegmentServiceMock();
        currencyExchangeRateService = new CurrencyExchangeRateServiceMock();
        excessPaymentService = new ExcessPaymentServiceMock();
        contractSubjectService = new ContractSubjectServiceMock();
    }

    @Override
    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    @Override
    public ContractService getContractService() {
        return contractService;
    }

    @Override
    public ContractSubjectService getContractSubjectService() {
        return contractSubjectService;
    }

    @Override
    public CompanyService getCompanyService() {
        return aidaCompanyService;
    }

    @Override
    public PersonService getPersonService() {
        return aidaPersonService;
    }

    @Override
    public SettlementService getSettlementService() {
        return settlementService;
    }

    @Override
    public ExcessPaymentService getExcessPaymentService() {
        return excessPaymentService;
    }

    @Override
    public InvolvementService getInvolvementService() {
        return involvementService;
    }

    @Override
    public SegmentService getSegmentService() {
        return segmentService;
    }

    @Override
    public CurrencyExchangeRateService getCurrencyExchangeRateService() {
        return currencyExchangeRateService;
    }
}
