package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentState;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentDto;
import com.codersteam.alwin.core.api.model.demand.DemandForPaymentTypeConfigurationDto;
import com.codersteam.alwin.core.api.model.demand.FormalDebtCollectionInvoiceDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.jpa.notice.DemandForPayment;
import com.codersteam.alwin.jpa.notice.DemandForPaymentTypeConfiguration;
import com.codersteam.alwin.jpa.notice.DemandForPaymentWithCompanyName;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionInvoice;
import com.codersteam.alwin.testdata.aida.AidaInvoiceTestData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.common.demand.DemandForPaymentState.CANCELED;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.ISSUED;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.NEW;
import static com.codersteam.alwin.common.demand.DemandForPaymentState.WAITING;
import static com.codersteam.alwin.testdata.AlwinPropertiesTestData.DMS_DOCUMENT_URL;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_NAME_1;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_NAME_2;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_NAME_3;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_NAME_4;
import static com.codersteam.alwin.testdata.CompanyTestData.COMPANY_NAME_5;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_1;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_2;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_3;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_4;
import static com.codersteam.alwin.testdata.CompanyTestData.EXT_COMPANY_ID_5;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentA;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentADto;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentB;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationFirstSegmentBDto;
import static com.codersteam.alwin.testdata.DemandForPaymentTypeConfigurationTestData.demandForPaymentTypeConfigurationSecondSegmentA;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice10;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice100;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice11;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice15;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice16;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice17;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice18;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoice25;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto11;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto15;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto16;
import static com.codersteam.alwin.testdata.FormalDebtCollectionInvoiceTestData.formalDebtCollectionInvoiceDto17;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_10;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_15;
import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_25;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_10;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_15;
import static com.codersteam.alwin.testdata.InvoiceTestData.INVOICE_NUMBER_25;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public final class DemandForPaymentTestData {

    public static final int DEMANDS_IN_NEW_STATE_COUNT = demandsForPaymentInNewState().size();

    public static final BigDecimal CURRENT_EUR_EXCHANGE_RATE = new BigDecimal("4.22");

    public static final Long NON_EXISTING_DEMAND_FOR_PAYMENT_ID_1 = -1L;
    public static final Long DEMAND_FOR_PAYMENT_ID_1001 = 1001L;
    public static final Long DEMAND_FOR_PAYMENT_ID_1003 = 1003L;
    public static final Long DEMAND_FOR_PAYMENT_ID_1005 = 1005L;

    public static final Long DEMAND_FOR_PAYMENT_ID_1 = 1L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_1 = parse("2017-07-11 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_1 = parse("2017-08-03 00:00:00.000000");
    public static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_1 = INVOICE_NUMBER_15;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_1 = EXT_COMPANY_ID_1;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_1 = COMPANY_NAME_1;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_1 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final DemandForPaymentTypeConfigurationDto DEMAND_FOR_PAYMENT_TYPE_DTO_1 = demandForPaymentTypeConfigurationFirstSegmentADto();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_1 = "CHARGE/INVOICE/10/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_1 = NEW;
    public static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_1 = "attachment1Link.pdf";
    public static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1 = CONTRACT_NUMBER_INVOICE_15;

    public static final Long DEMAND_FOR_PAYMENT_ID_2 = 2L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_2 = parse("2017-08-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_2 = parse("2017-09-13 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_2 = INVOICE_NUMBER_10;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_2 = EXT_COMPANY_ID_1;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_2 = COMPANY_NAME_1;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_2 = demandForPaymentTypeConfigurationSecondSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_2 = "CHARGE/INVOICE/11/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_2 = NEW;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_2 = "attachment2Link.pdf";
    public static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_2 = CONTRACT_NUMBER_INVOICE_10;

    public static final Long DEMAND_FOR_PAYMENT_ID_3 = 3L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_3 = parse("2017-10-11 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_3 = parse("2017-11-03 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_3 = INVOICE_NUMBER_25;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_3 = EXT_COMPANY_ID_2;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_3 = COMPANY_NAME_2;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_3 = demandForPaymentTypeConfigurationFirstSegmentB();
    private static final DemandForPaymentTypeConfigurationDto DEMAND_FOR_PAYMENT_TYPE_DTO_3 = demandForPaymentTypeConfigurationFirstSegmentBDto();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_3 = "CHARGE/INVOICE/12/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_3 = ISSUED;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_3 = "attachment3Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_3 = CONTRACT_NUMBER_INVOICE_25;

    public static final Long DEMAND_FOR_PAYMENT_ID_4 = 4L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_4 = parse("2017-07-21 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_4 = parse("2017-08-03 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_4 = INVOICE_NUMBER_15;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_4 = EXT_COMPANY_ID_3;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_4 = COMPANY_NAME_3;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_4 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final DemandForPaymentTypeConfigurationDto DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DTO_4 = demandForPaymentTypeConfigurationFirstSegmentADto();
    public static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_4 = "CHARGE/INVOICE/10/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_4 = WAITING;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_4 = "attachment4Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_4 = CONTRACT_NUMBER_INVOICE_15;

    public static final Long DEMAND_FOR_PAYMENT_ID_5 = 5L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_5 = parse("2017-08-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_5 = parse("2017-09-13 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_5 = INVOICE_NUMBER_10;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_5 = EXT_COMPANY_ID_3;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_5 = COMPANY_NAME_3;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_5 = demandForPaymentTypeConfigurationSecondSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_5 = "CHARGE/INVOICE/11/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_5 = WAITING;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_5 = "attachment5Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_5 = CONTRACT_NUMBER_INVOICE_10;

    public static final Long DEMAND_FOR_PAYMENT_ID_6 = 6L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_6 = parse("2017-08-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_6 = parse("2017-08-04 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_6 = AidaInvoiceTestData.INVOICE_NUMBER_1;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_6 = EXT_COMPANY_ID_1;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_6 = COMPANY_NAME_1;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_6 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_6 = null;
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_6 = NEW;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_6 = null;
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_6 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_1;

    public static final Long DEMAND_FOR_PAYMENT_ID_7 = 7L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_7 = parse("2017-09-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_7 = parse("2017-09-04 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_7 = AidaInvoiceTestData.INVOICE_NUMBER_2;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_7 = EXT_COMPANY_ID_2;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_7 = COMPANY_NAME_2;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_7 = demandForPaymentTypeConfigurationSecondSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_7 = "CHARGE/INVOICE/07/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_7 = ISSUED;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_7 = "attachment7Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_7 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_2;

    public static final Long DEMAND_FOR_PAYMENT_ID_8 = 8L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_8 = parse("2017-03-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_8 = parse("2017-03-13 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_8 = AidaInvoiceTestData.INVOICE_NUMBER_3;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_8 = EXT_COMPANY_ID_3;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_8 = COMPANY_NAME_3;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_8 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_8 = "CHARGE/INVOICE/08/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_8 = ISSUED;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_8 = "attachment8Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_8 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_3;

    public static final Long DEMAND_FOR_PAYMENT_ID_9 = 9L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_9 = parse("2017-05-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_9 = parse("2017-05-13 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_9 = AidaInvoiceTestData.INVOICE_NUMBER_4;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_9 = EXT_COMPANY_ID_4;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_9 = COMPANY_NAME_4;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_9 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_9 = null;
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_9 = NEW;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_9 = null;
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_9 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_4;

    public static final Long DEMAND_FOR_PAYMENT_ID_10 = 10L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_10 = parse("2017-06-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_10 = parse("2017-06-13 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_10 = AidaInvoiceTestData.INVOICE_NUMBER_5;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_10 = EXT_COMPANY_ID_5;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_10 = COMPANY_NAME_5;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_10 = demandForPaymentTypeConfigurationSecondSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_10 = null;
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_10 = NEW;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_10 = null;
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_10 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_5;

    public static final Long DEMAND_FOR_PAYMENT_ID_11 = 11L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_11 = parse("2017-10-11 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_11 = parse("2017-11-03 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_11 = INVOICE_NUMBER_25;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_11 = EXT_COMPANY_ID_2;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_11 = COMPANY_NAME_2;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_11 = demandForPaymentTypeConfigurationFirstSegmentB();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_11 = "CHARGE/INVOICE/12/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_11 = CANCELED;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_11 = "attachment3Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_11 = CONTRACT_NUMBER_INVOICE_25;
    public static final ReasonType DEMAND_FOR_PAYMENT_REASON_TYPE_11 = ReasonType.CLIENT_COMPLAINTS;

    public static final Long DEMAND_FOR_PAYMENT_ID_12 = 12L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_12 = parse("2017-02-01 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_12 = parse("2017-02-13 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_12 = AidaInvoiceTestData.INVOICE_NUMBER_3;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_12 = EXT_COMPANY_ID_3;
    private static final String DEMAND_FOR_PAYMENT_COMPANY_NAME_12 = COMPANY_NAME_3;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_12 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final String DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_12 = "CHARGE/INVOICE/08/2017";
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_12 = ISSUED;
    private static final String DEMAND_FOR_PAYMENT_ATTACHMENT_REF_12 = "attachment8Link.pdf";
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_12 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_3;

    public static final Long DEMAND_FOR_PAYMENT_ID_100 = 100L;
    private static final Date DEMAND_FOR_PAYMENT_ISSUE_DATE_100 = parse("2018-10-12 00:00:00.000000");
    private static final Date DEMAND_FOR_PAYMENT_DUE_DATE_100 = parse("2018-10-15 00:00:00.000000");
    private static final String DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_100 = AidaInvoiceTestData.INVOICE_NUMBER_1;
    private static final Long DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_100 = EXT_COMPANY_ID_1;
    private static final DemandForPaymentTypeConfiguration DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_100 = demandForPaymentTypeConfigurationFirstSegmentA();
    private static final DemandForPaymentState DEMAND_FOR_PAYMENT_STATE_100 = NEW;
    private static final String DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_100 = AidaInvoiceTestData.INVOICE_CONTRACT_NO_1;

    private DemandForPaymentTestData() {
    }

    public static List<DemandForPaymentWithCompanyName> demandsForPaymentInNewState() {
        return asList(demandForPaymentWithCompanyName1(), demandForPaymentWithCompanyName2());
    }

    public static List<DemandForPaymentWithCompanyName> demandsForPaymentInNewStateWithCreatedManually() {
        return asList(demandForPaymentWithCompanyName1(), demandForPaymentWithCompanyName2(), demandForPaymentWithCompanyName6(),
                demandForPaymentWithCompanyName9(), demandForPaymentWithCompanyName10());
    }

    public static List<DemandForPaymentWithCompanyName> demandsForPaymentInIssuedState() {
        return asList(demandForPaymentWithCompanyName3(), demandForPaymentWithCompanyName7(), demandForPaymentWithCompanyName8(), demandForPaymentWithCompanyName12());
    }

    public static List<DemandForPaymentWithCompanyName> demandsForPaymentFilteredByContractNo() {
        return asList(demandForPaymentWithCompanyName1(), demandForPaymentWithCompanyName4());
    }

    public static List<DemandForPaymentWithCompanyName> demandsForPaymentFilteredByType() {
        return asList(demandForPaymentWithCompanyName1(), demandForPaymentWithCompanyName3());
    }

    public static List<DemandForPaymentWithCompanyName> newManualDemandsForPayment() {
        return asList(demandForPaymentWithCompanyName6(), demandForPaymentWithCompanyName9());
    }

    public static List<DemandForPaymentDto> demandsForPaymentFilteredByContractNoDto() {
        return asList(demandForPaymentDto1(), demandForPaymentDto4());
    }

    public static List<DemandForPaymentWithCompanyName> allAutomaticallyCreatedDemandsForPaymentFirstPage() {
        return asList(demandForPaymentWithCompanyName1(), demandForPaymentWithCompanyName2(), demandForPaymentWithCompanyName3());
    }

    public static List<DemandForPaymentWithCompanyName> allDemandsForPaymentSecondPage() {
        return asList(demandForPaymentWithCompanyName4(), demandForPaymentWithCompanyName5(), demandForPaymentWithCompanyName11());
    }

    public static Page<DemandForPaymentDto> firstPageOfDemandsForPaymentsFilteredByContractNo() {
        return new Page<>(demandsForPaymentFilteredByContractNoDto(), 2);
    }

    public static DemandForPayment demandForPaymentWithoutIds1() {
        final DemandForPayment demandForPayment = demandForPayment1();
        demandForPayment.setId(null);
        demandForPayment.getInvoices().forEach(invoice -> invoice.setId(null));
        return demandForPayment;
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName1() {
        return new DemandForPaymentWithCompanyName(demandForPayment1(), DEMAND_FOR_PAYMENT_COMPANY_NAME_1);
    }

    public static DemandForPayment demandForPayment1() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ISSUE_DATE_1, DEMAND_FOR_PAYMENT_DUE_DATE_1,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_1, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_1,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_1, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_1, DEMAND_FOR_PAYMENT_STATE_1,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_1, prepareInvoices(formalDebtCollectionInvoice11(), formalDebtCollectionInvoice15()),
                DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1, false, false, null);
    }

    public static DemandForPaymentDto demandForPaymentDto1() {
        return demandForPaymentDto(DEMAND_FOR_PAYMENT_ID_1, DEMAND_FOR_PAYMENT_ISSUE_DATE_1, DEMAND_FOR_PAYMENT_DUE_DATE_1,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_1, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_1, DEMAND_FOR_PAYMENT_COMPANY_NAME_1, DEMAND_FOR_PAYMENT_TYPE_DTO_1,
                DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_1, DEMAND_FOR_PAYMENT_STATE_1, DEMAND_FOR_PAYMENT_ATTACHMENT_REF_1,
                prepareInvoices(formalDebtCollectionInvoiceDto11(), formalDebtCollectionInvoiceDto15()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_1,
                null, null, null, null);
    }

    public static DemandForPaymentDto demandForPaymentDto3() {
        return demandForPaymentDto(DEMAND_FOR_PAYMENT_ID_3, DEMAND_FOR_PAYMENT_ISSUE_DATE_3, DEMAND_FOR_PAYMENT_DUE_DATE_3,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_3, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_3, DEMAND_FOR_PAYMENT_COMPANY_NAME_3, DEMAND_FOR_PAYMENT_TYPE_DTO_3,
                DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_3, DEMAND_FOR_PAYMENT_STATE_3, DEMAND_FOR_PAYMENT_ATTACHMENT_REF_3,
                prepareInvoices(formalDebtCollectionInvoiceDto11(), formalDebtCollectionInvoiceDto15()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_3,
                null, null, null, null);
    }

    public static DemandForPayment demandForPayment100() {
        final DemandForPayment demandForPayment = demandForPaymentWithCompanyName1();
        demandForPayment.setId(DEMAND_FOR_PAYMENT_ID_100);
        return demandForPayment;
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName2() {
        return new DemandForPaymentWithCompanyName(demandForPayment2(), DEMAND_FOR_PAYMENT_COMPANY_NAME_2);
    }

    public static DemandForPayment demandForPayment2() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_2, DEMAND_FOR_PAYMENT_ISSUE_DATE_2, DEMAND_FOR_PAYMENT_DUE_DATE_2,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_2, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_2,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_2, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_2, DEMAND_FOR_PAYMENT_STATE_2,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_2, prepareInvoices(formalDebtCollectionInvoice10()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_2, false, false,
                null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName3() {
        return new DemandForPaymentWithCompanyName(demandForPayment3(), DEMAND_FOR_PAYMENT_COMPANY_NAME_3);
    }

    public static DemandForPayment demandForPayment3() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_3, DEMAND_FOR_PAYMENT_ISSUE_DATE_3, DEMAND_FOR_PAYMENT_DUE_DATE_3,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_3, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_3,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_3, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_3, DEMAND_FOR_PAYMENT_STATE_3,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_3, prepareInvoices(formalDebtCollectionInvoice25()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_3, false, false,
                null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName4() {
        return new DemandForPaymentWithCompanyName(demandForPayment4(),
                DEMAND_FOR_PAYMENT_COMPANY_NAME_4);
    }

    public static DemandForPayment demandForPayment4() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_4, DEMAND_FOR_PAYMENT_ISSUE_DATE_4, DEMAND_FOR_PAYMENT_DUE_DATE_4,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_4, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_4, DEMAND_FOR_PAYMENT_TYPE_4,
                DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_4, DEMAND_FOR_PAYMENT_STATE_4, DEMAND_FOR_PAYMENT_ATTACHMENT_REF_4,
                prepareInvoices(formalDebtCollectionInvoice16(), formalDebtCollectionInvoice17()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_4, false, false,
                null);
    }

    public static DemandForPaymentDto demandForPaymentDto4() {
        return demandForPaymentDto(DEMAND_FOR_PAYMENT_ID_4, DEMAND_FOR_PAYMENT_ISSUE_DATE_4, DEMAND_FOR_PAYMENT_DUE_DATE_4,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_4, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_4, DEMAND_FOR_PAYMENT_COMPANY_NAME_4,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_DTO_4, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_4, DEMAND_FOR_PAYMENT_STATE_4,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_4, prepareInvoices(formalDebtCollectionInvoiceDto16(), formalDebtCollectionInvoiceDto17()),
                DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_4, null, null, null, null);
    }

    private static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName5() {
        return new DemandForPaymentWithCompanyName(demandForPayment5(), DEMAND_FOR_PAYMENT_COMPANY_NAME_5);
    }

    private static DemandForPayment demandForPayment5() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_5, DEMAND_FOR_PAYMENT_ISSUE_DATE_5, DEMAND_FOR_PAYMENT_DUE_DATE_5,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_5, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_5,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_5, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_5, DEMAND_FOR_PAYMENT_STATE_5,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_5, prepareInvoices(formalDebtCollectionInvoice18()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_5, false,
                false, null);
    }

    private static DemandForPayment demandForPayment6() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_6, DEMAND_FOR_PAYMENT_ISSUE_DATE_6, DEMAND_FOR_PAYMENT_DUE_DATE_6,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_6, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_6,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_6, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_6, DEMAND_FOR_PAYMENT_STATE_6,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_6, new HashSet<>(), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_6, true, false, null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName6() {
        return new DemandForPaymentWithCompanyName(demandForPayment6(), DEMAND_FOR_PAYMENT_COMPANY_NAME_6);
    }

    public static DemandForPayment demandForPayment7() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_7, DEMAND_FOR_PAYMENT_ISSUE_DATE_7, DEMAND_FOR_PAYMENT_DUE_DATE_7,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_7, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_7,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_7, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_7, DEMAND_FOR_PAYMENT_STATE_7,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_7, new HashSet<>(), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_7, true, false, null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName7() {
        return new DemandForPaymentWithCompanyName(demandForPayment7(), DEMAND_FOR_PAYMENT_COMPANY_NAME_7);
    }

    private static DemandForPayment demandForPayment8() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_8, DEMAND_FOR_PAYMENT_ISSUE_DATE_8, DEMAND_FOR_PAYMENT_DUE_DATE_8,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_8, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_8,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_8, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_8, DEMAND_FOR_PAYMENT_STATE_8,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_8, new HashSet<>(), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_8, true, false, null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName8() {
        return new DemandForPaymentWithCompanyName(demandForPayment8(), DEMAND_FOR_PAYMENT_COMPANY_NAME_8);
    }

    private static DemandForPayment demandForPayment9() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_9, DEMAND_FOR_PAYMENT_ISSUE_DATE_9, DEMAND_FOR_PAYMENT_DUE_DATE_9,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_9, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_9,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_9, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_9, DEMAND_FOR_PAYMENT_STATE_9,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_9, new HashSet<>(), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_9, true, false, null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName9() {
        return new DemandForPaymentWithCompanyName(demandForPayment9(), DEMAND_FOR_PAYMENT_COMPANY_NAME_9);
    }

    private static DemandForPayment demandForPayment10() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_10, DEMAND_FOR_PAYMENT_ISSUE_DATE_10, DEMAND_FOR_PAYMENT_DUE_DATE_10,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_10, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_10,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_10, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_10, DEMAND_FOR_PAYMENT_STATE_10,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_10, new HashSet<>(), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_10, true, false, null);
    }

    public static DemandForPayment demandForPayment11() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_11, DEMAND_FOR_PAYMENT_ISSUE_DATE_11, DEMAND_FOR_PAYMENT_DUE_DATE_11,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_11, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_11,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_11, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_11, DEMAND_FOR_PAYMENT_STATE_11,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_11, prepareInvoices(formalDebtCollectionInvoice25()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_11, false, false,
                DEMAND_FOR_PAYMENT_REASON_TYPE_11);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName11() {
        return new DemandForPaymentWithCompanyName(demandForPayment11(), DEMAND_FOR_PAYMENT_COMPANY_NAME_11);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName10() {
        return new DemandForPaymentWithCompanyName(demandForPayment10(), DEMAND_FOR_PAYMENT_COMPANY_NAME_10);
    }

    private static DemandForPayment demandForPayment12() {
        return demandForPayment(DEMAND_FOR_PAYMENT_ID_12, DEMAND_FOR_PAYMENT_ISSUE_DATE_12, DEMAND_FOR_PAYMENT_DUE_DATE_12,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_12, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_12,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_12, DEMAND_FOR_PAYMENT_CHARGE_INVOICE_NO_12, DEMAND_FOR_PAYMENT_STATE_12,
                DEMAND_FOR_PAYMENT_ATTACHMENT_REF_12, new HashSet<>(), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_12, false, true, null);
    }

    public static DemandForPaymentWithCompanyName demandForPaymentWithCompanyName12() {
        return new DemandForPaymentWithCompanyName(demandForPayment12(), DEMAND_FOR_PAYMENT_COMPANY_NAME_12);
    }

    private static Set<FormalDebtCollectionInvoice> prepareInvoices(final FormalDebtCollectionInvoice... invoices) {
        return new HashSet<>(asList(invoices));
    }

    private static Set<FormalDebtCollectionInvoiceDto> prepareInvoices(final FormalDebtCollectionInvoiceDto... invoices) {
        return new HashSet<>(asList(invoices));
    }

    private static DemandForPaymentWithCompanyName newlyCreatedDemandForPayment() {
        return new DemandForPaymentWithCompanyName(demandForPayment(DEMAND_FOR_PAYMENT_ID_100, DEMAND_FOR_PAYMENT_ISSUE_DATE_100, DEMAND_FOR_PAYMENT_DUE_DATE_100,
                DEMAND_FOR_PAYMENT_INITIAL_INVOICE_NO_100, DEMAND_FOR_PAYMENT_EXT_COMPANY_ID_100,
                DEMAND_FOR_PAYMENT_TYPE_CONFIGURATION_100, null, DEMAND_FOR_PAYMENT_STATE_100, null,
                prepareInvoices(formalDebtCollectionInvoice100()), DEMAND_FOR_PAYMENT_CONTRACT_NUMBER_100, false, false, null), "nie znam tej firmy");
    }

    public static List<DemandForPayment> expectedNewlyCreatedDemandsForPayment() {
        return singletonList(newlyCreatedDemandForPayment());
    }

    private static DemandForPayment demandForPayment(final Long id, final Date issueDate, final Date dueDate, final String initialInvoiceNo,
                                                     final Long extCompanyId, final DemandForPaymentTypeConfiguration type,
                                                     final String chargeInvoiceNo, final DemandForPaymentState state, final String attachmentRef,
                                                     final Set<FormalDebtCollectionInvoice> invoices, final String contractNumber, final boolean createdManually,
                                                     final boolean aborted, final ReasonType reasonType) {
        final DemandForPayment demandForPayment = new DemandForPayment();
        demandForPayment.setId(id);
        demandForPayment.setIssueDate(issueDate);
        demandForPayment.setDueDate(dueDate);
        demandForPayment.setInitialInvoiceNo(initialInvoiceNo);
        demandForPayment.setExtCompanyId(extCompanyId);
        demandForPayment.setContractNumber(contractNumber);
        demandForPayment.setType(type);
        demandForPayment.setChargeInvoiceNo(chargeInvoiceNo);
        demandForPayment.setState(state);
        demandForPayment.setAttachmentRef(attachmentRef);
        demandForPayment.setInvoices(invoices);
        demandForPayment.setCreatedManually(createdManually);
        demandForPayment.setReasonType(reasonType);
        demandForPayment.setAborted(aborted);
        return demandForPayment;
    }

    private static DemandForPaymentDto demandForPaymentDto(final Long id, final Date issueDate, final Date dueDate, final String initialInvoiceNo,
                                                           final Long extCompanyId, final String companyName, final DemandForPaymentTypeConfigurationDto type, final String chargeInvoiceNo,
                                                           final DemandForPaymentState state, final String attachmentRef,
                                                           final Set<FormalDebtCollectionInvoiceDto> invoices, final String contractNumber, final ReasonType reasonType, final Date stateChangeDate, final OperatorDto stateChangeOperator, final String stateChangeReason) {
        final DemandForPaymentDto demandForPayment = new DemandForPaymentDto();
        demandForPayment.setId(id);
        demandForPayment.setIssueDate(issueDate);
        demandForPayment.setDueDate(dueDate);
        demandForPayment.setInitialInvoiceNo(initialInvoiceNo);
        demandForPayment.setExtCompanyId(extCompanyId);
        demandForPayment.setCompanyName(companyName);
        demandForPayment.setContractNumber(contractNumber);
        demandForPayment.setType(type);
        demandForPayment.setChargeInvoiceNo(chargeInvoiceNo);
        demandForPayment.setState(state);
        demandForPayment.setAttachmentRef(String.format("%s%s", DMS_DOCUMENT_URL, attachmentRef));
        demandForPayment.setInvoices(invoices);
        demandForPayment.setReasonType(reasonType);
        demandForPayment.setReasonTypeLabel(getReasonTypeLabel(reasonType));
        demandForPayment.setStateChangeDate(stateChangeDate);
        demandForPayment.setStateChangeOperator(stateChangeOperator);
        demandForPayment.setStateChangeReason(stateChangeReason);
        return demandForPayment;
    }

    private static String getReasonTypeLabel(final ReasonType reasonType) {
        return reasonType == null ? null : reasonType.getLabel();
    }

    public static DemandForPayment prepareDemandForPayment(final DemandForPaymentTypeConfiguration type, final DemandForPaymentState state,
                                                           final String contractNumber, final Date dueDate) {
        final DemandForPayment demandForPayment = demandForPaymentWithoutIds1();
        demandForPayment.setType(type);
        demandForPayment.setState(state);
        demandForPayment.setContractNumber(contractNumber);
        demandForPayment.setDueDate(dueDate);
        return demandForPayment;
    }

    public static DemandForPaymentWithCompanyName prepareDemandForPaymentWithCompanyName(final DemandForPaymentTypeConfiguration type, final DemandForPaymentState state,
                                                                                         final String contractNumber, final Date dueDate) {
        final DemandForPayment demandForPayment = demandForPaymentWithoutIds1();
        demandForPayment.setType(type);
        demandForPayment.setState(state);
        demandForPayment.setContractNumber(contractNumber);
        demandForPayment.setDueDate(dueDate);
        return new DemandForPaymentWithCompanyName(demandForPayment, COMPANY_NAME_1);
    }
}
