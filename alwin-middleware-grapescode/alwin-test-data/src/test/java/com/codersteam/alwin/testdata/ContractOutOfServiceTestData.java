package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceDto;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.customer.ContractOutOfService;
import com.codersteam.alwin.jpa.operator.Operator;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.InvoiceTestData.CONTRACT_NUMBER_INVOICE_10;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.TestDateUtils.parse;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_1;
import static com.codersteam.alwin.testdata.aida.AidaContractTestData.CONTRACT_NO_2;
import static com.codersteam.alwin.testdata.aida.AidaInvoiceTestData.INVOICE_CONTRACT_NO_2;
import static java.util.Arrays.asList;

public class ContractOutOfServiceTestData {

    public static final Long ID_1 = 1L;
    public static final Long EXT_COMPANY_ID_1 = 10L;
    private static final Date START_DATE_1 = parse("2017-07-11");
    private static final Date END_DATE_1 = parse("2017-07-20");
    public static final String BLOCKING_OPERATOR_1;
    private static final String REASON_1 = "blocking reason 1";
    private static final String REMARK_1 = "comment example 1";

    private static final Long ID_2 = 2L;
    private static final Long EXT_COMPANY_ID_2 = 2L;
    private static final Date START_DATE_2 = parse("2017-07-10");
    private static final Date END_DATE_2 = parse("2017-07-15");
    public static final String BLOCKING_OPERATOR_2;
    private static final String REASON_2 = "blocking reason 2";
    private static final String REMARK_2 = "comment example 2";

    private static final Long ID_3 = 3L;
    private static final Long EXT_COMPANY_ID_3 = 10L;
    private static final Date START_DATE_3 = parse("2017-07-11");
    private static final Date END_DATE_3 = null;
    public static final String BLOCKING_OPERATOR_3;
    private static final String REASON_3 = "blocking reason 3";
    private static final String REMARK_3 = "comment example 3";

    private static final Long ID_4 = 4L;
    private static final Long EXT_COMPANY_ID_4 = 10L;
    private static final Date START_DATE_4 = parse("2018-07-11");
    private static final Date END_DATE_4 = parse("2018-07-20");
    public static final String BLOCKING_OPERATOR_4;
    private static final String REASON_4 = "blocking reason 4";
    private static final String REMARK_4 = "comment example 4";

    static {
        final User user = testOperator1().getUser();
        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
        BLOCKING_OPERATOR_1 = blockingOperator;
        BLOCKING_OPERATOR_2 = blockingOperator;
        BLOCKING_OPERATOR_3 = blockingOperator;
        BLOCKING_OPERATOR_4 = blockingOperator;
    }


    public static ContractOutOfService contractOutOfService1() {
        return contractOutOfService(ID_1, EXT_COMPANY_ID_1, START_DATE_1, END_DATE_1, REASON_1, REMARK_1, CONTRACT_NO_1, testOperator1());
    }

    public static ContractOutOfService contractOutOfService(final String contractNo) {
        return contractOutOfService(ID_1, EXT_COMPANY_ID_1, START_DATE_1, END_DATE_1, REASON_1, REMARK_1, contractNo, testOperator1());
    }

    public static ContractOutOfServiceDto contractOutOfServiceDto1() {
        return contractOutOfServiceDto(ID_1, EXT_COMPANY_ID_1, START_DATE_1, END_DATE_1, REASON_1, REMARK_1, CONTRACT_NO_1, BLOCKING_OPERATOR_1);
    }

    public static ContractOutOfServiceDto contractOutOfServiceDto2() {
        return contractOutOfServiceDto(ID_2, EXT_COMPANY_ID_2, START_DATE_2, END_DATE_2, REASON_2, REMARK_2, CONTRACT_NO_1, BLOCKING_OPERATOR_1);
    }

    public static ContractOutOfService contractOutOfService3() {
        return contractOutOfService(ID_3, EXT_COMPANY_ID_3, START_DATE_3, END_DATE_3, REASON_3, REMARK_3, CONTRACT_NO_2, testOperator1());
    }

    public static ContractOutOfServiceDto contractOutOfServiceDto3() {
        return contractOutOfServiceDto(ID_3, EXT_COMPANY_ID_3, START_DATE_3, END_DATE_3, REASON_3, REMARK_3, CONTRACT_NO_2, BLOCKING_OPERATOR_3);
    }

    public static ContractOutOfServiceDto contractOutOfServiceDtoForInvoice2() {
        return contractOutOfServiceDto(ID_3, EXT_COMPANY_ID_3, START_DATE_3, END_DATE_3, REASON_3, REMARK_3, INVOICE_CONTRACT_NO_2, BLOCKING_OPERATOR_3);
    }

    public static ContractOutOfServiceDto contractOutOfServiceDtoForInvoice10() {
        return contractOutOfServiceDto(ID_3, EXT_COMPANY_ID_3, START_DATE_3, END_DATE_3, REASON_3, REMARK_3, CONTRACT_NUMBER_INVOICE_10, BLOCKING_OPERATOR_3);
    }

    public static ContractOutOfService contractOutOfService4() {
        return contractOutOfService(ID_4, EXT_COMPANY_ID_4, START_DATE_4, END_DATE_4, REASON_4, REMARK_4, CONTRACT_NO_1, testOperator1());
    }

    public static List<ContractOutOfService> activeContractsOutOfServiceForExtCompanyId1() {
        return asList(contractOutOfService1(), contractOutOfService3());
    }

    public static List<ContractOutOfService> allContractsOutOfServiceForExtCompanyId1() {
        return asList(contractOutOfService1(), contractOutOfService3(), contractOutOfService4());
    }

    public static List<ContractOutOfServiceDto> contractsOutOfServiceForExtCompanyId1Dto() {
        return asList(contractOutOfServiceDto1(), contractOutOfServiceDto3());
    }

    private static ContractOutOfService contractOutOfService(final Long id, final Long extCompanyId, final Date startDate, final Date endDate,
                                                             final String reason, final String remark, final String contractNo, final Operator blockingOperator) {
        final ContractOutOfService contractOutOfService = new ContractOutOfService();
        contractOutOfService.setId(id);
        contractOutOfService.setStartDate(startDate);
        contractOutOfService.setEndDate(endDate);
        contractOutOfService.setContractNo(contractNo);
        contractOutOfService.setExtCompanyId(extCompanyId);
        contractOutOfService.setReason(reason);
        contractOutOfService.setRemark(remark);
        contractOutOfService.setBlockingOperator(blockingOperator);
        return contractOutOfService;
    }

    private static ContractOutOfServiceDto contractOutOfServiceDto(final Long id, final Long extCompanyId, final Date startDate, final Date endDate,
                                                                   final String reason, final String remark, final String contractNo, final String blockingOperator) {
        final ContractOutOfServiceDto contractOutOfService = new ContractOutOfServiceDto();
        contractOutOfService.setId(id);
        contractOutOfService.setStartDate(startDate);
        contractOutOfService.setEndDate(endDate);
        contractOutOfService.setContractNo(contractNo);
        contractOutOfService.setExtCompanyId(extCompanyId);
        contractOutOfService.setReason(reason);
        contractOutOfService.setRemark(remark);
        contractOutOfService.setBlockingOperator(blockingOperator);
        return contractOutOfService;
    }
}
