package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.ReasonType;
import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceDto;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.operator.Operator;
import com.codersteam.alwin.jpa.termination.FormalDebtCollectionContractOutOfService;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_2;
import static java.util.Arrays.asList;

public class FormalDebtCollectionContractOutOfServiceTestData {

    public static final Long ID_1 = 1L;
    public static final Long EXT_COMPANY_ID_1 = 10L;
    private static final Date START_DATE_1 = parse("2017-07-11");
    private static final Date END_DATE_1 = parse("2017-07-20");
    public static final String BLOCKING_OPERATOR_1;
    private static final String REASON_1 = "blocking reason 1";
    private static final String REMARK_1 = "comment example 1";
    public static final ReasonType REASON_TYPE_1 = ReasonType.CLIENT_COMPLAINTS;
    public static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_KEY_1 = DemandForPaymentTypeKey.FIRST;

    public static final String BLOCKING_OPERATOR_2;

    private static final Long ID_3 = 3L;
    private static final Long EXT_COMPANY_ID_3 = 10L;
    private static final Date START_DATE_3 = parse("2017-07-11");
    private static final Date END_DATE_3 = null;
    public static final String BLOCKING_OPERATOR_3;
    private static final String REASON_3 = "blocking reason 3";
    private static final String REMARK_3 = "comment example 3";
    public static final ReasonType REASON_TYPE_3 = ReasonType.MISSING_DATA;
    public static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_KEY_3 = DemandForPaymentTypeKey.FIRST;

    private static final Long ID_4 = 4L;
    private static final Long EXT_COMPANY_ID_4 = 10L;
    private static final Date START_DATE_4 = parse("2018-07-11");
    private static final Date END_DATE_4 = parse("2018-07-20");
    public static final String BLOCKING_OPERATOR_4;
    private static final String REASON_4 = "blocking reason 4";
    private static final String REMARK_4 = "comment example 4";
    public static final ReasonType REASON_TYPE_4 = ReasonType.CORRECTION;
    public static final DemandForPaymentTypeKey DEMAND_FOR_PAYMENT_TYPE_KEY_4 = DemandForPaymentTypeKey.SECOND;

    static {
        final User user = testOperator1().getUser();
        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
        BLOCKING_OPERATOR_1 = blockingOperator;
        BLOCKING_OPERATOR_2 = blockingOperator;
        BLOCKING_OPERATOR_3 = blockingOperator;
        BLOCKING_OPERATOR_4 = blockingOperator;
    }


    public static FormalDebtCollectionContractOutOfService contractOutOfService1() {
        return contractOutOfService(ID_1, EXT_COMPANY_ID_1, START_DATE_1, END_DATE_1, REASON_1, REMARK_1, CONTRACT_NO_1, testOperator1(), REASON_TYPE_1,
                DEMAND_FOR_PAYMENT_TYPE_KEY_1);
    }

    public static FormalDebtCollectionContractOutOfServiceDto contractOutOfServiceDto1() {
        return contractOutOfServiceDto(ID_1, EXT_COMPANY_ID_1, START_DATE_1, END_DATE_1, REASON_1, REMARK_1, CONTRACT_NO_1, testOperator1(), REASON_TYPE_1,
                DEMAND_FOR_PAYMENT_TYPE_KEY_1);
    }

    public static FormalDebtCollectionContractOutOfService contractOutOfService(final String contractNo) {
        return contractOutOfService(ID_1, EXT_COMPANY_ID_1, START_DATE_1, END_DATE_1, REASON_1, REMARK_1, contractNo, testOperator1(), REASON_TYPE_1,
                DEMAND_FOR_PAYMENT_TYPE_KEY_1);
    }

    public static FormalDebtCollectionContractOutOfService contractOutOfService3() {
        return contractOutOfService(ID_3, EXT_COMPANY_ID_3, START_DATE_3, END_DATE_3, REASON_3, REMARK_3, CONTRACT_NO_2, testOperator1(), REASON_TYPE_3,
                DEMAND_FOR_PAYMENT_TYPE_KEY_3);
    }

    public static FormalDebtCollectionContractOutOfService contractOutOfService4() {
        return contractOutOfService(ID_4, EXT_COMPANY_ID_4, START_DATE_4, END_DATE_4, REASON_4, REMARK_4, CONTRACT_NO_1, testOperator1(), REASON_TYPE_4,
                DEMAND_FOR_PAYMENT_TYPE_KEY_4);
    }

    public static List<FormalDebtCollectionContractOutOfService> activeContractsOutOfServiceForExtCompanyId1() {
        return asList(contractOutOfService1(), contractOutOfService3());
    }

    public static List<FormalDebtCollectionContractOutOfService> allContractsOutOfServiceForExtCompanyId1() {
        return asList(contractOutOfService1(), contractOutOfService3(), contractOutOfService4());
    }

    private static FormalDebtCollectionContractOutOfService contractOutOfService(final Long id, final Long extCompanyId, final Date startDate, final Date endDate,
                                                                                 final String reason, final String remark, final String contractNo, final Operator blockingOperator, final ReasonType reasonType, final DemandForPaymentTypeKey demandForPaymentType) {
        final FormalDebtCollectionContractOutOfService contractOutOfService = new FormalDebtCollectionContractOutOfService();
        contractOutOfService.setId(id);
        contractOutOfService.setStartDate(startDate);
        contractOutOfService.setEndDate(endDate);
        contractOutOfService.setContractNo(contractNo);
        contractOutOfService.setExtCompanyId(extCompanyId);
        contractOutOfService.setReason(reason);
        contractOutOfService.setRemark(remark);
        contractOutOfService.setBlockingOperator(blockingOperator);
        contractOutOfService.setReasonType(reasonType);
        contractOutOfService.setDemandForPaymentType(demandForPaymentType);
        return contractOutOfService;
    }

    private static FormalDebtCollectionContractOutOfServiceDto contractOutOfServiceDto(final Long id, final Long extCompanyId, final Date startDate, final Date endDate,
                                                                                       final String reason, final String remark, final String contractNo, final Operator blockingOperator, final ReasonType reasonType, final DemandForPaymentTypeKey demandForPaymentType) {
        final FormalDebtCollectionContractOutOfServiceDto contractOutOfServiceDto = new FormalDebtCollectionContractOutOfServiceDto();
        contractOutOfServiceDto.setId(id);
        contractOutOfServiceDto.setStartDate(startDate);
        contractOutOfServiceDto.setEndDate(endDate);
        contractOutOfServiceDto.setContractNo(contractNo);
        contractOutOfServiceDto.setExtCompanyId(extCompanyId);
        contractOutOfServiceDto.setReason(reason);
        contractOutOfServiceDto.setRemark(remark);
        contractOutOfServiceDto.setBlockingOperator(blockingOperator.getUser().getFirstName() + " " + blockingOperator.getUser().getLastName());
        contractOutOfServiceDto.setReasonType(reasonType);
        contractOutOfServiceDto.setDemandForPaymentType(demandForPaymentType);
        return contractOutOfServiceDto;
    }
}
