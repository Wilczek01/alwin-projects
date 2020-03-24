package com.codersteam.alwin.core.service.impl;

import com.codersteam.aida.core.api.service.*;
import com.codersteam.alwin.core.api.service.AidaService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
@SuppressWarnings("EjbEnvironmentInspection")
public class AidaServiceImpl implements AidaService {

    @EJB(lookup = "java:global/aida/aida-core/InvoiceService!com.codersteam.aida.core.api.service.InvoiceService")
    private InvoiceService invoiceService;

    @EJB(lookup = "java:global/aida/aida-core/ContractService!com.codersteam.aida.core.api.service.ContractService")
    private ContractService contractService;

    @EJB(lookup = "java:global/aida/aida-core/CompanyService!com.codersteam.aida.core.api.service.CompanyService")
    private CompanyService companyService;

    @EJB(lookup = "java:global/aida/aida-core/PersonService!com.codersteam.aida.core.api.service.PersonService")
    private PersonService personService;

    @EJB(lookup = "java:global/aida/aida-core/SettlementService!com.codersteam.aida.core.api.service.SettlementService")
    private SettlementService settlementService;

    @EJB(lookup = "java:global/aida/aida-core/InvolvementService!com.codersteam.aida.core.api.service.InvolvementService")
    private InvolvementService involvementService;

    @EJB(lookup = "java:global/aida/aida-core/SegmentService!com.codersteam.aida.core.api.service.SegmentService")
    private SegmentService segmentService;

    @EJB(lookup = "java:global/aida/aida-core/CurrencyExchangeRateService!com.codersteam.aida.core.api.service.CurrencyExchangeRateService")
    private CurrencyExchangeRateService currencyExchangeRateService;

    @EJB(lookup = "java:global/aida/aida-core/ExcessPaymentService!com.codersteam.aida.core.api.service.ExcessPaymentService")
    private ExcessPaymentService excessPaymentService;

    @EJB(lookup = "java:global/aida/aida-core/ContractSubjectService!com.codersteam.aida.core.api.service.ContractSubjectService")
    private ContractSubjectService contractSubjectService;

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
        return companyService;
    }

    @Override
    public PersonService getPersonService() {
        return personService;
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
