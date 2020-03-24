package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.termination.ContractTerminationState;
import com.codersteam.alwin.common.termination.ContractTerminationType;
import com.codersteam.alwin.common.termination.ContractType;
import com.codersteam.alwin.core.api.model.customer.AddressDto;
import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractDto;
import com.codersteam.alwin.core.api.model.termination.TerminationContractSubjectDto;
import com.codersteam.alwin.core.api.model.termination.TerminationDto;
import com.codersteam.alwin.jpa.customer.Address;
import com.codersteam.alwin.jpa.termination.ContractTermination;
import com.codersteam.alwin.jpa.termination.ContractTerminationAttachment;
import com.codersteam.alwin.jpa.termination.ContractTerminationSubject;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import com.codersteam.alwin.testdata.aida.AidaInvoiceTestData;

import java.math.BigDecimal;
import java.util.*;

import static com.codersteam.alwin.testdata.DemandForPaymentTestData.DEMAND_FOR_PAYMENT_ID_1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public class ContractTerminationTestData {

    public static final String[] DATE_FIELDS_LIST = {"terminationDate", "activationDate", "dueDateInDemandForPayment", "formalDebtCollectionInvoices.realDueDate",
            "formalDebtCollectionInvoices.issueDate"};

    public static final String[] SKIP_FIELDS_LIST = {"distinct"};

    public static final Long NON_EXISTING_CONTRACT_TERMINATION_ID_1 = -1L;

    public static final Long ID_100 = 100L;
    public static final String CONTRACT_NUMBER_100 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_1;
    public static final Long COMPANY_ID_100 = CompanyTestData.EXT_COMPANY_ID_1;
    public static final Date TERMINATION_DATE_100 = TestDateUtils.parse("2018-10-02");
    public static final Date ACTIVATION_DATE_100 = null;
    public static final ContractTerminationType TYPE_100 = ContractTerminationType.CONDITIONAL;
    public static final ContractTerminationState STATE_100 = ContractTerminationState.NEW;
    public static final ContractType CONTRACT_TYPE_100 = ContractType.OPERATIONAL_LEASING;
    public static final String GENERATED_BY_100 = null;
    public static final String OPERATOR_PHONE_NUMBER_100 = null;
    public static final String OPERATOR_EMAIL_100 = null;
    public static final String COMPANY_NAME_100 = CompanyTestData.COMPANY_NAME_1;
    public static final Address COMPANY_ADDRESS_100 = AddressTestData.address2();
    public static final Long COMPANY_ADDRESS_ID_100 = COMPANY_ADDRESS_100.getId();
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_100 = AddressTestData.address1();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_100 = COMPANY_CORRESPONDENCE_ADDRESS_100.getId();
    public static final BigDecimal RESUMPTION_COST_100 = new BigDecimal("0.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_100 = parse("2018-03-12 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_100 = new BigDecimal("-9500.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_100 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_100 = new BigDecimal("-9500.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_100 = new BigDecimal("0.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_100 = FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice1().getInvoiceNumber();
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_100 = new BigDecimal("1230.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_100 = new BigDecimal("500.00");
    public static final String REMARK_100 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_100 = singletonList(ContractTerminationSubjectTestData.newContractTerminationSubject100());
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_100 = emptyList();
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_100 = emptyList();
    public static final String DISTINCT_100 = "99992018-10-02CONDITIONAL";

    public static final Long ID_101 = 101L;
    public static final String CONTRACT_NUMBER_101 = "CONTRACT/101";
    public static final Long COMPANY_ID_101 = 1000L;
    public static final Date TERMINATION_DATE_101 = TestDateUtils.parse("2016-03-13");
    public static final Date ACTIVATION_DATE_101 = null;
    public static final ContractTerminationType TYPE_101 = ContractTerminationType.CONDITIONAL;
    public static final ContractTerminationState STATE_101 = ContractTerminationState.ISSUED;
    public static final ContractType CONTRACT_TYPE_101 = ContractType.OPERATIONAL_LEASING;
    public static final String GENERATED_BY_101 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_101 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_101 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_101 = "Company1000";
    public static final Address COMPANY_ADDRESS_101 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_101 = 4L;
    public static final AddressDto COMPANY_ADDRESS_DTO_101 = AddressTestData.addressDto4();
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_101 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_101 = 5L;
    public static final AddressDto COMPANY_CORRESPONDENCE_ADDRESS_DTO_101 = AddressTestData.addressDto5();
    public static final BigDecimal RESUMPTION_COST_101 = new BigDecimal("0.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_101 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_101 = new BigDecimal("0.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_101 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_101 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_101 = new BigDecimal("0.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_101 = FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice1().getInvoiceNumber();
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_101 = new BigDecimal("0.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_101 = new BigDecimal("0.00");
    public static final String REMARK_101 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_101 = asList(ContractTerminationSubjectTestData.contractTerminationSubject101(),
            ContractTerminationSubjectTestData.contractTerminationSubject102(), ContractTerminationSubjectTestData.contractTerminationSubject103());
    public static final List<TerminationContractSubjectDto> SUBJECTS_DTO_101 = asList(ContractTerminationSubjectTestData.terminationContractSubjectDto101(),
            ContractTerminationSubjectTestData.terminationContractSubjectDto102(), ContractTerminationSubjectTestData.terminationContractSubjectDto103());
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_101 = asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice1(), FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice2());
    public static final List<FormalDebtCollectionInvoiceDto> FORMAL_DEBT_COLLECTION_INVOICES_DTO_101 = asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto1(), FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto2());
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_101 = asList(
            ContractTerminationAttachmentTestData.contractTerminationAttachment101(),
            ContractTerminationAttachmentTestData.contractTerminationAttachment103()
    );
    public static final List<TerminationContractDto> TERMINATION_CONTRACTS_101 = null;
    public static final String DMS_DOCUMENT_URL = "http://dms.document.url/";

    public static final Set<String> CONTRACT_TERMINATION_ATTACHMENT_REFS1_101 = new HashSet<>(asList(
            DMS_DOCUMENT_URL + ContractTerminationAttachmentTestData.REFERENCE_101,
            DMS_DOCUMENT_URL + ContractTerminationAttachmentTestData.REFERENCE_103
    ));

    public static final Long ID_102 = 102L;
    public static final String CONTRACT_NUMBER_102 = "CONTRACT/102";
    public static final Long COMPANY_ID_102 = 1000L;
    public static final Date TERMINATION_DATE_102 = TestDateUtils.parse("2016-03-13");
    public static final Date ACTIVATION_DATE_102 = null;
    public static final ContractTerminationType TYPE_102 = ContractTerminationType.IMMEDIATE;
    public static final ContractTerminationState STATE_102 = ContractTerminationState.REJECTED;
    public static final ContractType CONTRACT_TYPE_102 = ContractType.FINANCIAL_LEASING;
    public static final String GENERATED_BY_102 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_102 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_102 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_102 = "Company1000";
    public static final Address COMPANY_ADDRESS_102 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_102 = 4L;
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_102 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_102 = 5L;
    public static final BigDecimal RESUMPTION_COST_102 = new BigDecimal("0.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_102 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_102 = new BigDecimal("0.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_102 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_102 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_102 = new BigDecimal("0.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_102 = "FV/2018/102";
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_102 = new BigDecimal("0.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_102 = new BigDecimal("0.00");
    public static final String REMARK_102 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_102 = singletonList(ContractTerminationSubjectTestData.contractTerminationSubject104());
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_102 = asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice3(), FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice4()
    );
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_102 = emptyList();

    public static final Long ID_103 = 103L;
    public static final String CONTRACT_NUMBER_103 = "CONTRACT/103";
    public static final Long COMPANY_ID_103 = 1000L;
    public static final Date TERMINATION_DATE_103 = TestDateUtils.parse("2016-03-13");
    public static final Date ACTIVATION_DATE_103 = null;
    public static final ContractTerminationType TYPE_103 = ContractTerminationType.CONDITIONAL;
    public static final ContractTerminationState STATE_103 = ContractTerminationState.ISSUED;
    public static final ContractType CONTRACT_TYPE_103 = ContractType.FINANCIAL_LEASING;
    public static final String GENERATED_BY_103 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_103 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_103 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_103 = "Company1000";
    public static final Address COMPANY_ADDRESS_103 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_103 = 4L;
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_103 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_103 = 5L;
    public static final BigDecimal RESUMPTION_COST_103 = new BigDecimal("0.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_103 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_103 = new BigDecimal("0.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_103 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_103 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_103 = new BigDecimal("0.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_103 = "FV/2018/103";
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_103 = new BigDecimal("0.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_103 = new BigDecimal("0.00");
    public static final String REMARK_103 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_103 = emptyList();
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_103 = emptyList();
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_103 = emptyList();

    public static final Long ID_104 = 104L;
    public static final String CONTRACT_NUMBER_104 = "CONTRACT/104";
    public static final Long COMPANY_ID_104 = 1001L;
    public static final Date TERMINATION_DATE_104 = TestDateUtils.parse("2016-03-13");
    public static final Date ACTIVATION_DATE_104 = null;
    public static final ContractTerminationType TYPE_104 = ContractTerminationType.CONDITIONAL;
    public static final ContractTerminationState STATE_104 = ContractTerminationState.NEW;
    public static final ContractType CONTRACT_TYPE_104 = ContractType.FINANCIAL_LEASING;
    public static final String GENERATED_BY_104 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_104 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_104 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_104 = "Company1001";
    public static final Address COMPANY_ADDRESS_104 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_104 = 4L;
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_104 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_104 = 5L;
    public static final BigDecimal RESUMPTION_COST_104 = new BigDecimal("100.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_104 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_104 = new BigDecimal("0.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_104 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_104 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_104 = new BigDecimal("0.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_104 = FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice3().getInvoiceNumber();
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_104 = new BigDecimal("100.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_104 = new BigDecimal("0.00");
    public static final String REMARK_104 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_104 = asList(
            ContractTerminationSubjectTestData.contractTerminationSubject106(), ContractTerminationSubjectTestData.contractTerminationSubject107());
    public static final List<TerminationContractSubjectDto> SUBJECTS_DTO_104 = asList(
            ContractTerminationSubjectTestData.terminationContractSubjectDto106(), ContractTerminationSubjectTestData.terminationContractSubjectDto107());
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_104 = asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice3(), FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice4());
    public static final List<FormalDebtCollectionInvoiceDto> FORMAL_DEBT_COLLECTION_INVOICES_DTO_104 = asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto3(), FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto4());
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_104 = emptyList();

    public static final Long ID_105 = 105L;
    public static final String CONTRACT_NUMBER_105 = "CONTRACT/105";
    public static final Long COMPANY_ID_105 = 1001L;
    public static final Date TERMINATION_DATE_105 = TestDateUtils.parse("2016-03-13");
    public static final Date ACTIVATION_DATE_105 = null;
    public static final ContractTerminationType TYPE_105 = ContractTerminationType.IMMEDIATE;
    public static final ContractTerminationState STATE_105 = ContractTerminationState.NEW;
    public static final ContractType CONTRACT_TYPE_105 = ContractType.FINANCIAL_LEASING;
    public static final String GENERATED_BY_105 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_105 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_105 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_105 = "Company1001";
    public static final Address COMPANY_ADDRESS_105 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_105 = 4L;
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_105 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_105 = 5L;
    public static final BigDecimal RESUMPTION_COST_105 = new BigDecimal("100.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_105 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_105 = new BigDecimal("0.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_105 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_105 = new BigDecimal("100.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_105 = new BigDecimal("100.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_105 = "FV/2018/105";
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_105 = new BigDecimal("0.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_105 = new BigDecimal("0.00");
    public static final String REMARK_105 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_105 = singletonList(ContractTerminationSubjectTestData.contractTerminationSubject108());
    public static final List<TerminationContractSubjectDto> SUBJECTS_DTO_105 =
            singletonList(ContractTerminationSubjectTestData.terminationContractSubjectDto108());
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_105 =
            singletonList(FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice1());
    public static final List<FormalDebtCollectionInvoiceDto> FORMAL_DEBT_COLLECTION_INVOICES_DTO_105 =
            singletonList(FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto1());
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_105 = emptyList();

    public static final Long ID_130 = 130L;
    public static final String CONTRACT_NUMBER_130 = "CONTRACT/130";
    public static final Long COMPANY_ID_130 = 1009L;
    public static final Date TERMINATION_DATE_130 = TestDateUtils.parse("2016-04-14");
    public static final Date ACTIVATION_DATE_130 = TestDateUtils.parse("2016-12-05");
    public static final ContractTerminationType TYPE_130 = ContractTerminationType.CONDITIONAL;
    public static final ContractTerminationState STATE_130 = ContractTerminationState.REJECTED;
    public static final ContractType CONTRACT_TYPE_130 = ContractType.FINANCIAL_LEASING;
    public static final String GENERATED_BY_130 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_130 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_130 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_130 = "Company1009";
    public static final Address COMPANY_ADDRESS_130 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_130 = 4L;
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_130 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_130 = 5L;
    public static final BigDecimal RESUMPTION_COST_130 = new BigDecimal("500.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_130 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_130 = new BigDecimal("500.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_130 = new BigDecimal("500.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_130 = new BigDecimal("100.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_130 = new BigDecimal("100.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_130 = "FV/2018/130";
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_130 = new BigDecimal("100.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_130 = new BigDecimal("40.00");
    public static final String REMARK_130 = null;
    public static final List<ContractTerminationSubject> SUBJECTS_130 = singletonList(
            ContractTerminationSubjectTestData.contractTerminationSubject129()
    );
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_130 = emptyList();
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_130 = emptyList();


    public static final Long ID_131 = 131L;

    public static final String CONTRACT_NUMBER_132 = "CONTRACT/132";
    public static final Long COMPANY_ID_132 = 1010L;
    public static final ContractTerminationType TYPE_132 = ContractTerminationType.IMMEDIATE;
    public static final String GENERATED_BY_132 = "Jan Kochanowski";
    public static final String COMPANY_NAME_132 = "Company1010";
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_132 = "FV/2018/132";

    public static final Long ID_133 = 133L;

    public static final Long ID_1104 = 1104L;
    public static final String CONTRACT_NUMBER_1104 = "CONTRACT/1104";
    public static final Long COMPANY_ID_1104 = 1000L;
    public static final Date TERMINATION_DATE_1104 = TestDateUtils.parse("2018-01-13");
    public static final Date ACTIVATION_DATE_1104 = null;
    public static final ContractTerminationType TYPE_1104 = ContractTerminationType.CONDITIONAL;
    public static final ContractTerminationState STATE_1104 = ContractTerminationState.NEW;
    public static final ContractType CONTRACT_TYPE_1104 = ContractType.OPERATIONAL_LEASING;
    public static final String GENERATED_BY_1104 = "Jan Kochanowski";
    public static final String OPERATOR_PHONE_NUMBER_1104 = "+22 123 45 67";
    public static final String OPERATOR_EMAIL_1104 = "jkochanowski@alwin.com";
    public static final String COMPANY_NAME_1104 = "Company1000";
    public static final Address COMPANY_ADDRESS_1104 = AddressTestData.address4();
    public static final Long COMPANY_ADDRESS_ID_1104 = 4L;
    public static final AddressDto COMPANY_ADDRESS_DTO_1104 = AddressTestData.addressDto4();
    public static final Address COMPANY_CORRESPONDENCE_ADDRESS_1104 = AddressTestData.address5();
    public static final Long COMPANY_CORRESPONDENCE_ADDRESS_ID_1104 = 5L;
    public static final AddressDto COMPANY_CORRESPONDENCE_ADDRESS_DTO_1104 = AddressTestData.addressDto5();
    public static final BigDecimal RESUMPTION_COST_1104 = new BigDecimal("0.00");
    public static final Date DUE_DATE_IN_DEMAND_FOR_PAYMENT_1104 = parse("2016-03-27 00:00:00.000000");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_1104 = new BigDecimal("0.00");
    public static final BigDecimal AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_1104 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_1104 = new BigDecimal("0.00");
    public static final BigDecimal TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_1104 = new BigDecimal("0.00");
    public static final String INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_1104 = FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice1().getInvoiceNumber();
    public static final BigDecimal INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_1104 = new BigDecimal("0.00");
    public static final BigDecimal INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_1104 = new BigDecimal("0.00");
    public static final List<ContractTerminationSubject> SUBJECTS_1104 = Arrays.asList(
            ContractTerminationSubjectTestData.contractTerminationSubject1106(), ContractTerminationSubjectTestData.contractTerminationSubject1107());
    public static final List<FormalDebtCollectionInvoice> FORMAL_DEBT_COLLECTION_INVOICES_1104 = Arrays.asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice3(),
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice4()
    );
    public static final String REMARK_1104 = null;
    public static final List<TerminationContractSubjectDto> SUBJECTS_DTO_1104 = Arrays.asList(
            ContractTerminationSubjectTestData.terminationContractSubjectDto1106(),
            ContractTerminationSubjectTestData.terminationContractSubjectDto1107());
    public static final List<FormalDebtCollectionInvoiceDto> FORMAL_DEBT_COLLECTION_INVOICES_DTO_1104 = Arrays.asList(
            FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto3(), FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto4());
    public static final List<TerminationContractDto> TERMINATION_CONTRACTS_1104 = Collections.singletonList(terminationContractDto1104());
    public static final List<ContractTerminationAttachment> CONTRACT_TERMINATION_ATTACHMENTS_1104 = Collections.emptyList();
    private static final Set<String> CONTRACT_TERMINATION_ATTACHMENT_REFS1_1104 = null;

    public static final List<ContractTermination> CONTRACT_TERMINATIONS_FOR_OFFSET_3_LIMIT_2 = Arrays.asList(
            contractTermination101(), contractTermination103());

    public static ContractTermination contractTermination101() {
        return contractTermination(ID_101, CONTRACT_NUMBER_101, TERMINATION_DATE_101, ACTIVATION_DATE_101, TYPE_101, STATE_101, GENERATED_BY_101, OPERATOR_PHONE_NUMBER_101,
                OPERATOR_EMAIL_101, COMPANY_ID_101, COMPANY_NAME_101, COMPANY_ADDRESS_101, COMPANY_ADDRESS_ID_101, COMPANY_CORRESPONDENCE_ADDRESS_101,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_101, RESUMPTION_COST_101, DUE_DATE_IN_DEMAND_FOR_PAYMENT_101, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_101,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_101,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_101, TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_101,
                INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_101, INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_101,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_101, SUBJECTS_101, FORMAL_DEBT_COLLECTION_INVOICES_101, REMARK_101,
                CONTRACT_TERMINATION_ATTACHMENTS_101, CONTRACT_TYPE_101, DEMAND_FOR_PAYMENT_ID_1, null, null);
    }

    public static TerminationContractDto terminationContractDto101() {
        return terminationContract(ID_101, CONTRACT_NUMBER_101, TYPE_101, STATE_101, REMARK_101, GENERATED_BY_101, OPERATOR_PHONE_NUMBER_101,
                OPERATOR_EMAIL_101, RESUMPTION_COST_101, DUE_DATE_IN_DEMAND_FOR_PAYMENT_101, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_101,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_101,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_101,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_101, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_101,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_101,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_101, SUBJECTS_DTO_101, FORMAL_DEBT_COLLECTION_INVOICES_DTO_101, TERMINATION_DATE_101,
                ACTIVATION_DATE_101
        );
    }

    public static ContractTermination contractTermination102() {
        return contractTermination(ID_102, CONTRACT_NUMBER_102, TERMINATION_DATE_102, ACTIVATION_DATE_102, TYPE_102, STATE_102, GENERATED_BY_102, OPERATOR_PHONE_NUMBER_102,
                OPERATOR_EMAIL_102, COMPANY_ID_102, COMPANY_NAME_102, COMPANY_ADDRESS_102, COMPANY_ADDRESS_ID_102, COMPANY_CORRESPONDENCE_ADDRESS_102,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_102, RESUMPTION_COST_102, DUE_DATE_IN_DEMAND_FOR_PAYMENT_102, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_102,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_102,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_102, TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_102,
                INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_102, INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_102,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_102, SUBJECTS_102, FORMAL_DEBT_COLLECTION_INVOICES_102, REMARK_102,
                CONTRACT_TERMINATION_ATTACHMENTS_102, CONTRACT_TYPE_102, null, null, null);
    }

    public static ContractTermination contractTermination104() {
        return contractTermination(ID_104, CONTRACT_NUMBER_104, TERMINATION_DATE_104, ACTIVATION_DATE_104, TYPE_104, STATE_104, GENERATED_BY_104, OPERATOR_PHONE_NUMBER_104,
                OPERATOR_EMAIL_104, COMPANY_ID_104, COMPANY_NAME_104, COMPANY_ADDRESS_104, COMPANY_ADDRESS_ID_104, COMPANY_CORRESPONDENCE_ADDRESS_104,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_104, RESUMPTION_COST_104, DUE_DATE_IN_DEMAND_FOR_PAYMENT_104, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_104,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_104,
                INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_104,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_104,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_104, SUBJECTS_104, FORMAL_DEBT_COLLECTION_INVOICES_104, REMARK_104,
                CONTRACT_TERMINATION_ATTACHMENTS_104, CONTRACT_TYPE_104, null, null, null);
    }

    public static ContractTermination contractTermination103() {
        return contractTermination(ID_103, CONTRACT_NUMBER_103, TERMINATION_DATE_103, ACTIVATION_DATE_103, TYPE_103, STATE_103, GENERATED_BY_103, OPERATOR_PHONE_NUMBER_103,
                OPERATOR_EMAIL_103, COMPANY_ID_103, COMPANY_NAME_103, COMPANY_ADDRESS_103, COMPANY_ADDRESS_ID_103, COMPANY_CORRESPONDENCE_ADDRESS_103,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_103, RESUMPTION_COST_103, DUE_DATE_IN_DEMAND_FOR_PAYMENT_103, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_103,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_103,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_103, TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_103,
                INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_103, INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_103,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_103, SUBJECTS_103, FORMAL_DEBT_COLLECTION_INVOICES_103, REMARK_103,
                CONTRACT_TERMINATION_ATTACHMENTS_103, CONTRACT_TYPE_103, null, null, null);
    }

    public static TerminationContractDto terminationContractDto104() {
        return terminationContract(ID_104, CONTRACT_NUMBER_104, TYPE_104, STATE_104, REMARK_104, GENERATED_BY_104, OPERATOR_PHONE_NUMBER_104,
                OPERATOR_EMAIL_104, RESUMPTION_COST_104, DUE_DATE_IN_DEMAND_FOR_PAYMENT_104, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_104,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_104, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_104,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_104,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_104, SUBJECTS_DTO_104, FORMAL_DEBT_COLLECTION_INVOICES_DTO_104, TERMINATION_DATE_104,
                ACTIVATION_DATE_104
        );
    }

    public static ContractTermination contractTermination105() {
        return contractTermination(ID_105, CONTRACT_NUMBER_105, TERMINATION_DATE_105, ACTIVATION_DATE_105, TYPE_105, STATE_105, GENERATED_BY_105, OPERATOR_PHONE_NUMBER_105,
                OPERATOR_EMAIL_105, COMPANY_ID_105, COMPANY_NAME_105, COMPANY_ADDRESS_105, COMPANY_ADDRESS_ID_105, COMPANY_CORRESPONDENCE_ADDRESS_105,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_105, RESUMPTION_COST_105, DUE_DATE_IN_DEMAND_FOR_PAYMENT_105, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_105,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_105,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_105,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_105, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_105,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_105,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_105, SUBJECTS_105, FORMAL_DEBT_COLLECTION_INVOICES_105, REMARK_105,
                CONTRACT_TERMINATION_ATTACHMENTS_105, CONTRACT_TYPE_105, null, null, null);
    }

    public static TerminationContractDto terminationContractDto105() {
        return terminationContract(ID_105, CONTRACT_NUMBER_105, TYPE_105, STATE_105, REMARK_105, GENERATED_BY_105, OPERATOR_PHONE_NUMBER_105,
                OPERATOR_EMAIL_105, RESUMPTION_COST_105, DUE_DATE_IN_DEMAND_FOR_PAYMENT_105, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_105,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_105,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_105,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_105, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_105,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_105,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_105, SUBJECTS_DTO_105, FORMAL_DEBT_COLLECTION_INVOICES_DTO_105, TERMINATION_DATE_105,
                ACTIVATION_DATE_105
        );
    }

    public static ContractTermination contractTermination130() {
        return contractTermination(ID_130, CONTRACT_NUMBER_130, TERMINATION_DATE_130, ACTIVATION_DATE_130, TYPE_130, STATE_130, GENERATED_BY_130, OPERATOR_PHONE_NUMBER_130,
                OPERATOR_EMAIL_130, COMPANY_ID_130, COMPANY_NAME_130, COMPANY_ADDRESS_130, COMPANY_ADDRESS_ID_130, COMPANY_CORRESPONDENCE_ADDRESS_130,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_130, RESUMPTION_COST_130, DUE_DATE_IN_DEMAND_FOR_PAYMENT_130, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_130,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_130,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_130,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_130, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_130,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_130,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_130, SUBJECTS_130, FORMAL_DEBT_COLLECTION_INVOICES_130, REMARK_130,
                CONTRACT_TERMINATION_ATTACHMENTS_130, CONTRACT_TYPE_130, null, null, null);
    }

    public static TerminationDto terminationDto101() {
        return terminationDto(COMPANY_ID_101, TERMINATION_DATE_101, COMPANY_NAME_101, COMPANY_ADDRESS_DTO_101,
                COMPANY_CORRESPONDENCE_ADDRESS_DTO_101, TERMINATION_CONTRACTS_101, CONTRACT_TERMINATION_ATTACHMENT_REFS1_101, TYPE_101);
    }

    public static ContractTermination newlyCreatedContractTermination() {
        return contractTermination(ID_100, CONTRACT_NUMBER_100, TERMINATION_DATE_100, ACTIVATION_DATE_100, TYPE_100, STATE_100, GENERATED_BY_100, OPERATOR_PHONE_NUMBER_100,
                OPERATOR_EMAIL_100, COMPANY_ID_100, COMPANY_NAME_100, COMPANY_ADDRESS_100, COMPANY_ADDRESS_ID_100, COMPANY_CORRESPONDENCE_ADDRESS_100,
                COMPANY_CORRESPONDENCE_ADDRESS_ID_100, RESUMPTION_COST_100, DUE_DATE_IN_DEMAND_FOR_PAYMENT_100, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_100,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_100,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_100,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_100, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_100,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_100,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_100, SUBJECTS_100, FORMAL_DEBT_COLLECTION_INVOICES_100, REMARK_100,
                CONTRACT_TERMINATION_ATTACHMENTS_100, CONTRACT_TYPE_100, DEMAND_FOR_PAYMENT_ID_1, null, DISTINCT_100);
    }

    public static List<ContractTermination> expectedNewlyCreatedContractTerminations() {
        return singletonList(newlyCreatedContractTermination());
    }

    private static TerminationDto terminationDto(final Long companyId, final Date terminationDate, final String companyName,
                                                 final AddressDto companyAddress, final AddressDto companyCorrespondenceAddress,
                                                 final List<TerminationContractDto> contracts, final Set<String> attachmentRefs,
                                                 final ContractTerminationType type) {
        final TerminationDto termination = new TerminationDto();
        termination.setExtCompanyId(companyId);
        termination.setTerminationDate(terminationDate);
        termination.setType(type);
        termination.setCompanyName(companyName);
        termination.setCompanyAddress(companyAddress);
        termination.setCompanyCorrespondenceAddress(companyCorrespondenceAddress);
        termination.setContracts(contracts);
        termination.setAttachmentRefs(attachmentRefs);
        return termination;
    }

    public static ContractTermination contractTermination1104() {
        return contractTermination(ID_1104, CONTRACT_NUMBER_1104, TERMINATION_DATE_1104, null, TYPE_1104, STATE_1104, GENERATED_BY_1104,
                OPERATOR_PHONE_NUMBER_1104, OPERATOR_EMAIL_1104, COMPANY_ID_1104, COMPANY_NAME_1104, COMPANY_ADDRESS_1104, COMPANY_ADDRESS_ID_1104,
                COMPANY_CORRESPONDENCE_ADDRESS_1104, COMPANY_CORRESPONDENCE_ADDRESS_ID_1104, RESUMPTION_COST_1104, DUE_DATE_IN_DEMAND_FOR_PAYMENT_1104,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_1104, AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_1104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_1104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_1104,
                INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_1104,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_1104,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_1104, SUBJECTS_1104, FORMAL_DEBT_COLLECTION_INVOICES_1104, REMARK_1104,
                CONTRACT_TERMINATION_ATTACHMENTS_1104, CONTRACT_TYPE_1104, null, null, null);
    }

    public static TerminationContractDto terminationContractDto1104() {
        return terminationContract(ID_1104, CONTRACT_NUMBER_1104, TYPE_1104, STATE_1104, REMARK_1104, GENERATED_BY_1104, OPERATOR_PHONE_NUMBER_1104,
                OPERATOR_EMAIL_1104, RESUMPTION_COST_1104, DUE_DATE_IN_DEMAND_FOR_PAYMENT_1104, AMOUNT_IN_DEMAND_FOR_PAYMENT_PLN_1104,
                AMOUNT_IN_DEMAND_FOR_PAYMENT_EUR_1104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_PLN_1104,
                TOTAL_PAYMENTS_SINCE_DEMAND_FOR_PAYMENT_EUR_1104, INVOICE_NUMBER_INITIATING_DEMAND_FOR_PAYMENT_1104,
                INVOICE_AMOUNT_INITIATING_DEMAND_FOR_PAYMENT_1104,
                INVOICE_BALANCE_INITIATING_DEMAND_FOR_PAYMENT_1104, SUBJECTS_DTO_1104, FORMAL_DEBT_COLLECTION_INVOICES_DTO_1104, TERMINATION_DATE_1104,
                ACTIVATION_DATE_1104);
    }

    public static TerminationDto terminationDtoForProcessTest() {
        return terminationDto(COMPANY_ID_1104, TERMINATION_DATE_1104, COMPANY_NAME_1104, COMPANY_ADDRESS_DTO_1104,
                COMPANY_CORRESPONDENCE_ADDRESS_DTO_1104, TERMINATION_CONTRACTS_1104, CONTRACT_TERMINATION_ATTACHMENT_REFS1_1104, TYPE_1104);
    }

    private static TerminationContractDto terminationContract(final Long contractTerminationId, final String contractNumber, final ContractTerminationType type,
                                                              final ContractTerminationState state, final String remark, final String generatedBy,
                                                              final String operatorPhoneNumber, final String operatorEmail, final BigDecimal resumptionCost,
                                                              final Date dueDateInDemandForPayment, final BigDecimal amountInDemandForPaymentPLN,
                                                              final BigDecimal amountInDemandForPaymentEUR,
                                                              final BigDecimal totalPaymentsSinceDemandForPaymentPLN,
                                                              final BigDecimal totalPaymentsSinceDemandForPaymentEUR,
                                                              final String invoiceNumberInitiatingDemandForPayment,
                                                              final BigDecimal invoiceAmountInitiatingDemandForPayment,
                                                              final BigDecimal invoiceBalanceInitiatingDemandForPayment,
                                                              final List<TerminationContractSubjectDto> subjects,
                                                              final List<FormalDebtCollectionInvoiceDto> formalDebtCollectionInvoices,
                                                              final Date terminationDate, final Date activationDate) {
        final TerminationContractDto tc = new TerminationContractDto();
        tc.setContractTerminationId(contractTerminationId);
        tc.setTerminationDate(terminationDate);
        tc.setActivationDate(activationDate);
        tc.setContractNumber(contractNumber);
        tc.setType(type);
        tc.setState(state);
        tc.setRemark(remark);
        tc.setGeneratedBy(generatedBy);
        tc.setOperatorPhoneNumber(operatorPhoneNumber);
        tc.setOperatorEmail(operatorEmail);
        tc.setResumptionCost(resumptionCost);
        tc.setDueDateInDemandForPayment(dueDateInDemandForPayment);
        tc.setAmountInDemandForPaymentPLN(amountInDemandForPaymentPLN);
        tc.setAmountInDemandForPaymentEUR(amountInDemandForPaymentEUR);
        tc.setTotalPaymentsSinceDemandForPaymentPLN(totalPaymentsSinceDemandForPaymentPLN);
        tc.setTotalPaymentsSinceDemandForPaymentEUR(totalPaymentsSinceDemandForPaymentEUR);
        tc.setInvoiceNumberInitiatingDemandForPayment(invoiceNumberInitiatingDemandForPayment);
        tc.setInvoiceAmountInitiatingDemandForPayment(invoiceAmountInitiatingDemandForPayment);
        tc.setInvoiceBalanceInitiatingDemandForPayment(invoiceBalanceInitiatingDemandForPayment);
        tc.setSubjects(subjects);
        tc.setFormalDebtCollectionInvoices(formalDebtCollectionInvoices);
        return tc;
    }

    private static ContractTermination contractTermination(final Long id, final String contractNumber, final Date terminationDate, final Date activationDate,
                                                           final ContractTerminationType type, final ContractTerminationState state, final String generatedBy,
                                                           final String operatorPhoneNumber, final String operatorEmail, final Long companyId,
                                                           final String companyName, final Address companyAddress, final Long companyAddressId,
                                                           final Address companyCorrespondenceAddress, final Long companyCorrespondenceAddressId,
                                                           final BigDecimal resumptionCost, final Date dueDateInDemandForPayment,
                                                           final BigDecimal amountInDemandForPaymentPLN, final BigDecimal amountInDemandForPaymentEUR,
                                                           final BigDecimal totalPaymentsSinceDemandForPaymentPLN,
                                                           final BigDecimal totalPaymentsSinceDemandForPaymentEUR,
                                                           final String invoiceNumberInitiatingDemandForPayment,
                                                           final BigDecimal invoiceAmountInitiatingDemandForPayment,
                                                           final BigDecimal invoiceBalanceInitiatingDemandForPayment,
                                                           final List<ContractTerminationSubject> subjects,
                                                           final List<FormalDebtCollectionInvoice> formalDebtCollectionInvoices, final String remark,
                                                           final List<ContractTerminationAttachment> contractTerminationAttachments,
                                                           final ContractType contractType, final Long precedingDemandForPaymentId,
                                                           final Long precedingContractTerminationId, final String distinct) {
        final ContractTermination contractTermination = new ContractTermination();
        contractTermination.setId(id);
        contractTermination.setContractNumber(contractNumber);
        contractTermination.setTerminationDate(terminationDate);
        contractTermination.setActivationDate(activationDate);
        contractTermination.setType(type);
        contractTermination.setState(state);
        contractTermination.setContractType(contractType);
        contractTermination.setGeneratedBy(generatedBy);
        contractTermination.setOperatorPhoneNumber(operatorPhoneNumber);
        contractTermination.setOperatorEmail(operatorEmail);
        contractTermination.setExtCompanyId(companyId);
        contractTermination.setCompanyName(companyName);
        contractTermination.setCompanyAddressId(companyAddressId);
        contractTermination.setCompanyAddress(companyAddress);
        contractTermination.setCompanyCorrespondenceAddress(companyCorrespondenceAddress);
        contractTermination.setCompanyCorrespondenceAddressId(companyCorrespondenceAddressId);
        contractTermination.setResumptionCost(resumptionCost);
        contractTermination.setDueDateInDemandForPayment(dueDateInDemandForPayment);
        contractTermination.setAmountInDemandForPaymentPLN(amountInDemandForPaymentPLN);
        contractTermination.setAmountInDemandForPaymentEUR(amountInDemandForPaymentEUR);
        contractTermination.setTotalPaymentsSinceDemandForPaymentPLN(totalPaymentsSinceDemandForPaymentPLN);
        contractTermination.setTotalPaymentsSinceDemandForPaymentEUR(totalPaymentsSinceDemandForPaymentEUR);
        contractTermination.setInvoiceNumberInitiatingDemandForPayment(invoiceNumberInitiatingDemandForPayment);
        contractTermination.setInvoiceAmountInitiatingDemandForPayment(invoiceAmountInitiatingDemandForPayment);
        contractTermination.setInvoiceBalanceInitiatingDemandForPayment(invoiceBalanceInitiatingDemandForPayment);
        contractTermination.setSubjects(subjects);
        contractTermination.setFormalDebtCollectionInvoices(formalDebtCollectionInvoices);
        contractTermination.setRemark(remark);
        contractTermination.setContractTerminationAttachments(contractTerminationAttachments);
        contractTermination.setPrecedingDemandForPaymentId(precedingDemandForPaymentId);
        contractTermination.setPrecedingContractTerminationId(precedingContractTerminationId);
        contractTermination.setDistinct(distinct);
        return contractTermination;
    }
}
