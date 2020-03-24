package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.contract.ContractDto;
import com.codersteam.alwin.core.api.model.customer.CustomerDto;
import com.codersteam.alwin.jpa.customer.Customer;
import com.codersteam.alwin.jpa.issue.Contract;

import static com.codersteam.alwin.testdata.CustomerTestData.*;

@SuppressWarnings("WeakerAccess")
public class ContractTestData {

    private static final long ID_1 = 1L;
    private static final String EXT_CONTRACT_ID_1 = "TEST 12/34/56";

    private static final long ID_2 = 2L;
    private static final String EXT_CONTRACT_ID_2 = "TEST CONTRACT 2";

    private static final long ID_3 = 3L;
    private static final String EXT_CONTRACT_ID_3 = "TEST CONTRACT 3";

    private static final long ID_4 = 4L;
    private static final String EXT_CONTRACT_ID_4 = "TEST CONTRACT 4";

    private static final long ID_5 = 5L;
    private static final String EXT_CONTRACT_ID_5 = "TEST CONTRACT 5";

    private static final long ID_6 = 6L;
    private static final String EXT_CONTRACT_ID_6 = "TEST CONTRACT 6";

    private static final long ID_7 = 7L;
    private static final String EXT_CONTRACT_ID_7 = "TEST CONTRACT 7";

    private static final long ID_8 = 8L;
    private static final String EXT_CONTRACT_ID_8 = "TEST CONTRACT 8";

    private static final long ID_9 = 9L;
    private static final String EXT_CONTRACT_ID_9 = "TEST CONTRACT 9";

    private static final long ID_10 = 10L;
    private static final String EXT_CONTRACT_ID_10 = "TEST CONTRACT 10";

    private static final long ID_11 = 11L;
    private static final String EXT_CONTRACT_ID_11 = "TEST CONTRACT 11";

    private static final long ID_12 = 12L;
    private static final String EXT_CONTRACT_ID_12 = "TEST CONTRACT 12";

    private static final long ID_13 = 13L;
    private static final String EXT_CONTRACT_ID_13 = "TEST CONTRACT 13";

    private static final long ID_16 = 16L;
    private static final String EXT_CONTRACT_ID_16 = "TEST CONTRACT 16";

    private static final long ID_17 = 17L;
    private static final String EXT_CONTRACT_ID_17 = "TEST CONTRACT 17";

    private static final long ID_18 = 18L;
    private static final String EXT_CONTRACT_ID_18 = "TEST CONTRACT 18";

    public static Contract contract1() {
        return contract(ID_1, EXT_CONTRACT_ID_1, testCustomer1());
    }

    public static Contract contract2() {
        return contract(ID_2, EXT_CONTRACT_ID_2, testCustomer7());
    }

    public static Contract contract3() {
        return contract(ID_3, EXT_CONTRACT_ID_3, testCustomer8());
    }

    public static Contract contract4() {
        return contract(ID_4, EXT_CONTRACT_ID_4, testCustomer9());
    }

    public static Contract contract5() {
        return contract(ID_5, EXT_CONTRACT_ID_5, testCustomer10());
    }

    public static Contract contract6() {
        return contract(ID_6, EXT_CONTRACT_ID_6, testCustomer11());
    }

    public static Contract contract7() {
        return contract(ID_7, EXT_CONTRACT_ID_7, testCustomer12());
    }

    public static Contract contract8() {
        return contract(ID_8, EXT_CONTRACT_ID_8, testCustomer13());
    }

    public static Contract contract9() {
        return contract(ID_9, EXT_CONTRACT_ID_9, testCustomer15());
    }

    public static Contract contract10() {
        return contract(ID_10, EXT_CONTRACT_ID_10, testCustomer17());
    }

    public static Contract contract11() {
        return contract(ID_11, EXT_CONTRACT_ID_11, testCustomer18());
    }

    public static Contract contract12() {
        return contract(ID_12, EXT_CONTRACT_ID_12, testCustomer19());
    }

    public static Contract contract13() {
        return contract(ID_13, EXT_CONTRACT_ID_13, testCustomer20());
    }

    public static Contract contract16() {
        return contract(ID_16, EXT_CONTRACT_ID_16, testCustomer23());
    }

    public static Contract contract17() {
        return contract(ID_17, EXT_CONTRACT_ID_17, testCustomer5());
    }

    public static Contract contract18() {
        return contract(ID_18, EXT_CONTRACT_ID_18, testCustomer24());
    }

    public static ContractDto contractDto1() {
        return contractDto(ID_1, EXT_CONTRACT_ID_1, testCustomerDto1());
    }

    public static ContractDto contractDto2() {
        return contractDto(ID_2, EXT_CONTRACT_ID_2, testCustomerDto7());
    }

    public static ContractDto contractDto3() {
        return contractDto(ID_3, EXT_CONTRACT_ID_3, testCustomerDto8());
    }

    public static ContractDto contractDto4() {
        return contractDto(ID_4, EXT_CONTRACT_ID_4, testCustomerDto9());
    }

    public static ContractDto contractDto6() {
        return contractDto(ID_6, EXT_CONTRACT_ID_6, testCustomerDto11());
    }

    public static ContractDto contractDto7() {
        return contractDto(ID_7, EXT_CONTRACT_ID_7, testCustomerDto12());
    }

    public static ContractDto contractDto13() {
        return contractDto(ID_13, EXT_CONTRACT_ID_13, testCustomerDto20());
    }

    public static ContractDto contractDto17() {
        return contractDto(ID_17, EXT_CONTRACT_ID_17, testCustomerDto5());
    }

    public static ContractDto contractDto18() {
        return contractDto(ID_18, EXT_CONTRACT_ID_18, testCustomerDto24());
    }

    private static Contract contract(final Long id, final String extContractId, final Customer customer) {
        final Contract contract = new Contract();
        contract.setId(id);
        contract.setExtContractId(extContractId);
        contract.setCustomer(customer);
        return contract;
    }

    private static ContractDto contractDto(final Long id, final String extContractId, final CustomerDto customer) {
        final ContractDto contract = new ContractDto();
        contract.setId(id);
        contract.setExtContractId(extContractId);
        contract.setCustomer(customer);
        return contract;
    }
}
