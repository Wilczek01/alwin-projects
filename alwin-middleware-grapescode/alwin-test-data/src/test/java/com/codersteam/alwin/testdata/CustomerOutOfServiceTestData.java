package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.customer.CustomerOutOfServiceDto;
import com.codersteam.alwin.jpa.User;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.customer.CustomerOutOfService;
import com.codersteam.alwin.jpa.operator.Operator;

import java.util.Date;
import java.util.List;

import static com.codersteam.alwin.testdata.CustomerTestData.testCustomer1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static java.util.Arrays.asList;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public class CustomerOutOfServiceTestData {

    public static final Long NOT_EXISTING_ID = -1L;

    public static final Long ID_1 = 1L;
    public static final Date EXCLUSION_START_DATE_1 = TestDateUtils.parse("2017-07-11 00:00:00.000000");
    public static final Date EXCLUSION_END_DATE_1 = TestDateUtils.parse("2017-07-20 00:00:00.000000");
    public static final String BLOCKING_OPERATOR_1;
    public static final String EXCLUSION_BLOCKING_REASON_1 = "blocking reason 1";
    public static final String EXCLUSION_BLOCKING_REMARK_1 = "blocking remark 1";

    public static final Long ID_2 = 2L;
    public static final Date EXCLUSION_START_DATE_2 = TestDateUtils.parse("2017-07-15 00:00:00.000000");
    public static final Date EXCLUSION_END_DATE_2 = TestDateUtils.parse("2017-07-23 00:00:00.000000");
    public static final String BLOCKING_OPERATOR_2;
    public static final String EXCLUSION_BLOCKING_REASON_2 = "blocking reason 2";
    public static final String EXCLUSION_BLOCKING_REMARK_2 = "blocking remark 2";

    static {
        final User user = testOperator1().getUser();
        final String blockingOperator = String.format("%s %s", user.getFirstName(), user.getLastName());
        BLOCKING_OPERATOR_1 = blockingOperator;
        BLOCKING_OPERATOR_2 = blockingOperator;
    }

    public static CustomerOutOfService customerOutOfService1() {
        return newEntity(ID_1, testCustomer1(), EXCLUSION_START_DATE_1, EXCLUSION_END_DATE_1, testOperator1(), EXCLUSION_BLOCKING_REASON_1,
                EXCLUSION_BLOCKING_REMARK_1);
    }

    public static CustomerOutOfService customerOutOfService2() {
        return newEntity(ID_2, testCustomer1(), EXCLUSION_START_DATE_2, EXCLUSION_END_DATE_2, testOperator1(), EXCLUSION_BLOCKING_REASON_2,
                EXCLUSION_BLOCKING_REMARK_2);
    }

    public static CustomerOutOfServiceDto customerOutOfServiceDto1() {
        return newDto(ID_1, EXCLUSION_START_DATE_1, EXCLUSION_END_DATE_1, BLOCKING_OPERATOR_1, EXCLUSION_BLOCKING_REASON_1, EXCLUSION_BLOCKING_REMARK_1);
    }

    public static CustomerOutOfServiceDto customerOutOfServiceDto2() {
        return newDto(ID_2, EXCLUSION_START_DATE_2, EXCLUSION_END_DATE_2, BLOCKING_OPERATOR_2, EXCLUSION_BLOCKING_REASON_2, EXCLUSION_BLOCKING_REMARK_2);
    }

    private static CustomerOutOfService newEntity(final Long id, final Customer customer, final Date startDate, final Date endDate,
                                                  final Operator blockingOperator, final String reason, final String remark) {
        final CustomerOutOfService customerOutOfService = new CustomerOutOfService();
        customerOutOfService.setId(id);
        customerOutOfService.setCustomer(customer);
        customerOutOfService.setStartDate(startDate);
        customerOutOfService.setEndDate(endDate);
        customerOutOfService.setBlockingOperator(blockingOperator);
        customerOutOfService.setReason(reason);
        customerOutOfService.setRemark(remark);
        return customerOutOfService;
    }

    private static CustomerOutOfServiceDto newDto(final Long id, final Date startDate, final Date endDate, final String blockingOperator, final
    String reason, final String remark) {
        final CustomerOutOfServiceDto customerOutOfService = new CustomerOutOfServiceDto();
        customerOutOfService.setId(id);
        customerOutOfService.setStartDate(startDate);
        customerOutOfService.setEndDate(endDate);
        customerOutOfService.setBlockingOperator(blockingOperator);
        customerOutOfService.setReason(reason);
        customerOutOfService.setRemark(remark);
        return customerOutOfService;
    }

    public static List<CustomerOutOfServiceDto> customersOutOfServiceForExtCompanyIdDto1() {
        return asList(customerOutOfServiceDto1(), customerOutOfServiceDto2());
    }

    public static List<CustomerOutOfService> customersOutOfServiceForExtCompanyId1() {
        return asList(customerOutOfService1(), customerOutOfService2());
    }
}
