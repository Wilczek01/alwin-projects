package com.codersteam.alwin.testdata.aida;

import com.codersteam.aida.core.api.model.CustomerInvolvementDto;

import java.math.BigDecimal;
import java.util.List;

import static com.codersteam.alwin.testdata.TestBigDecimalUtils.valueOf;
import static com.codersteam.alwin.testdata.aida.AidaCompanyTestData.COMPANY_ID_10;
import static java.util.Arrays.asList;

/**
 * @author Tomasz Sliwinski
 */
@SuppressWarnings("WeakerAccess")
public class AidaCustomerInvolvementTestData {

    public static final Long CUSTOMER_INVOLVEMENT_COMPANY_ID_1 = COMPANY_ID_10;
    public static final String CUSTOMER_INVOLVEMENT_CONTRACT_NO_1 = "contract1";
    public static final BigDecimal CUSTOMER_INVOLVEMENT_BALANCE_1 = valueOf("1021.23");
    public static final BigDecimal CUSTOMER_INVOLVEMENT_RPB_1 = valueOf("10.00");

    public static final Long CUSTOMER_INVOLVEMENT_COMPANY_ID_2 = COMPANY_ID_10;
    public static final String CUSTOMER_INVOLVEMENT_CONTRACT_NO_2 = "contract2";
    public static final BigDecimal CUSTOMER_INVOLVEMENT_BALANCE_2 = valueOf("101.11");
    public static final BigDecimal CUSTOMER_INVOLVEMENT_RPB_2 = valueOf("13.46");

    public static final Long CUSTOMER_INVOLVEMENT_COMPANY_ID_3 = COMPANY_ID_10;
    public static final String CUSTOMER_INVOLVEMENT_CONTRACT_NO_3 = "contract3";
    public static final BigDecimal CUSTOMER_INVOLVEMENT_BALANCE_3 = valueOf("23101.53");
    public static final BigDecimal CUSTOMER_INVOLVEMENT_RPB_3 = valueOf("133.97");

    public static final Long CUSTOMER_INVOLVEMENT_COMPANY_ID_4 = COMPANY_ID_10;
    public static final String CUSTOMER_INVOLVEMENT_CONTRACT_NO_4 = "contract4";
    public static final BigDecimal CUSTOMER_INVOLVEMENT_BALANCE_4 = valueOf("46.94");
    public static final BigDecimal CUSTOMER_INVOLVEMENT_RPB_4 = null;

    public static final Long CUSTOMER_INVOLVEMENT_COMPANY_ID_5 = COMPANY_ID_10;
    public static final String CUSTOMER_INVOLVEMENT_CONTRACT_NO_5 = "contract5";
    public static final BigDecimal CUSTOMER_INVOLVEMENT_BALANCE_5 = valueOf("346.94");
    public static final BigDecimal CUSTOMER_INVOLVEMENT_RPB_5 = valueOf("66.67");

    public static CustomerInvolvementDto customerInvolvementDto1() {
        return customerInvolvementDto(CUSTOMER_INVOLVEMENT_COMPANY_ID_1, CUSTOMER_INVOLVEMENT_CONTRACT_NO_1, CUSTOMER_INVOLVEMENT_BALANCE_1,
                CUSTOMER_INVOLVEMENT_RPB_1);
    }

    public static CustomerInvolvementDto customerInvolvementDto2() {
        return customerInvolvementDto(CUSTOMER_INVOLVEMENT_COMPANY_ID_2, CUSTOMER_INVOLVEMENT_CONTRACT_NO_2, CUSTOMER_INVOLVEMENT_BALANCE_2,
                CUSTOMER_INVOLVEMENT_RPB_2);
    }

    public static CustomerInvolvementDto customerInvolvementDto3() {
        return customerInvolvementDto(CUSTOMER_INVOLVEMENT_COMPANY_ID_3, CUSTOMER_INVOLVEMENT_CONTRACT_NO_3, CUSTOMER_INVOLVEMENT_BALANCE_3,
                CUSTOMER_INVOLVEMENT_RPB_3);
    }

    public static CustomerInvolvementDto customerInvolvementDto4() {
        return customerInvolvementDto(CUSTOMER_INVOLVEMENT_COMPANY_ID_4, CUSTOMER_INVOLVEMENT_CONTRACT_NO_4, CUSTOMER_INVOLVEMENT_BALANCE_4,
                CUSTOMER_INVOLVEMENT_RPB_4);
    }

    public static CustomerInvolvementDto customerInvolvementDto5() {
        return customerInvolvementDto(CUSTOMER_INVOLVEMENT_COMPANY_ID_5, CUSTOMER_INVOLVEMENT_CONTRACT_NO_5, CUSTOMER_INVOLVEMENT_BALANCE_5,
                CUSTOMER_INVOLVEMENT_RPB_5);
    }

    public static List<CustomerInvolvementDto> allCompany10CustomerInvolvements() {
        return asList(customerInvolvementDto1(), customerInvolvementDto2(), customerInvolvementDto3(), customerInvolvementDto4(), customerInvolvementDto5());
    }

    private static CustomerInvolvementDto customerInvolvementDto(final Long extCompanyId, final String contractNo, final BigDecimal balance,
                                                                 final BigDecimal rpb) {
        final CustomerInvolvementDto customerInvolvementDto = new CustomerInvolvementDto();
        customerInvolvementDto.setCompanyId(extCompanyId);
        customerInvolvementDto.setContractNo(contractNo);
        customerInvolvementDto.setBalance(balance);
        customerInvolvementDto.setRpb(rpb);
        return customerInvolvementDto;
    }
}
