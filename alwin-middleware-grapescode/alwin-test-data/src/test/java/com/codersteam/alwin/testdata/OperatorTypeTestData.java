package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.jpa.operator.OperatorType;
import com.codersteam.alwin.jpa.type.OperatorNameType;

import java.util.List;

import static com.codersteam.alwin.jpa.type.OperatorNameType.ACCOUNT_MANAGER;
import static com.codersteam.alwin.jpa.type.OperatorNameType.ADMIN;
import static com.codersteam.alwin.jpa.type.OperatorNameType.ANALYST;
import static com.codersteam.alwin.jpa.type.OperatorNameType.DEPARTMENT_MANAGER;
import static com.codersteam.alwin.jpa.type.OperatorNameType.DIRECT_DEBT_COLLECTION_MANAGER;
import static com.codersteam.alwin.jpa.type.OperatorNameType.FIELD_DEBT_COLLECTOR;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_1;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_2;
import static com.codersteam.alwin.jpa.type.OperatorNameType.PHONE_DEBT_COLLECTOR_MANAGER;
import static com.codersteam.alwin.jpa.type.OperatorNameType.RENUNCIATION_COORDINATOR;
import static com.codersteam.alwin.jpa.type.OperatorNameType.RESTRUCTURING_SPECIALIST;
import static com.codersteam.alwin.jpa.type.OperatorNameType.SECURITY_SPECIALIST;
import static java.util.Arrays.asList;

public class OperatorTypeTestData {

    private static final OperatorNameType OPERATOR_NAME_TYPE_1 = ADMIN;
    private static final String OPERATOR_NAME_LABEL_1 = "Administrator systemu";
    private static final long OPERATOR_TYPE_ID_1 = 1L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_2 = PHONE_DEBT_COLLECTOR;
    private static final String OPERATOR_NAME_LABEL_2 = "Windykator telefoniczny";
    private static final long OPERATOR_TYPE_ID_2 = 2L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_3 = PHONE_DEBT_COLLECTOR_MANAGER;
    private static final String OPERATOR_NAME_LABEL_3 = "Menedżer windykacji telefonicznej";
    private static final long OPERATOR_TYPE_ID_3 = 3L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_4 = DIRECT_DEBT_COLLECTION_MANAGER;
    private static final String OPERATOR_NAME_LABEL_4 = "Menedżer windykacji bezpośredniej";
    private static final long OPERATOR_TYPE_ID_4 = 4L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_5 = FIELD_DEBT_COLLECTOR;
    private static final String OPERATOR_NAME_LABEL_5 = "Windykator terenowy";
    private static final long OPERATOR_TYPE_ID_5 = 5L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_6 = RESTRUCTURING_SPECIALIST;
    private static final String OPERATOR_NAME_LABEL_6 = "Specjalista ds. restrukturyzacji";
    private static final long OPERATOR_TYPE_ID_6 = 6L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_7 = RENUNCIATION_COORDINATOR;
    private static final String OPERATOR_NAME_LABEL_7 = "Koordynator wypowiedzeń";
    private static final long OPERATOR_TYPE_ID_7 = 7L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_8 = SECURITY_SPECIALIST;
    private static final String OPERATOR_NAME_LABEL_8 = "Specjalista ds. realizacji zabezpieczeń";
    private static final long OPERATOR_TYPE_ID_8 = 8L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_9 = ANALYST;
    private static final String OPERATOR_NAME_LABEL_9 = "Analityk";
    private static final long OPERATOR_TYPE_ID_9 = 9L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_10 = DEPARTMENT_MANAGER;
    private static final String OPERATOR_NAME_LABEL_10 = "Menedżer departamentu";
    private static final long OPERATOR_TYPE_ID_10 = 10L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_11 = ACCOUNT_MANAGER;
    private static final String OPERATOR_NAME_LABEL_11 = "Opiekun klienta";
    private static final long OPERATOR_TYPE_ID_11 = 11L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_12 = PHONE_DEBT_COLLECTOR_1;
    private static final String OPERATOR_NAME_LABEL_12 = "Windykator telefoniczny sekcja 1";
    private static final long OPERATOR_TYPE_ID_12 = 12L;

    private static final OperatorNameType OPERATOR_NAME_TYPE_13 = PHONE_DEBT_COLLECTOR_2;
    private static final String OPERATOR_NAME_LABEL_13 = "Windykator telefoniczny sekcja 2";
    private static final long OPERATOR_TYPE_ID_13 = 13L;

    public static final OperatorTypeDto TEST_OPERATOR_TYPE_DTO = operatorTypeDto(OPERATOR_TYPE_ID_1, OPERATOR_NAME_TYPE_1, OPERATOR_NAME_LABEL_1);
    public static final OperatorTypeDto TEST_OPERATOR_TYPE_DTO_2 = operatorTypeDto(OPERATOR_TYPE_ID_2, OPERATOR_NAME_TYPE_2, OPERATOR_NAME_LABEL_2);
    public static final OperatorTypeDto TEST_OPERATOR_TYPE_DTO_3 = operatorTypeDto(OPERATOR_TYPE_ID_3, OPERATOR_NAME_TYPE_3, OPERATOR_NAME_LABEL_3);
    public static final OperatorTypeDto TEST_OPERATOR_TYPE_DTO_12 = operatorTypeDto(OPERATOR_TYPE_ID_12, OPERATOR_NAME_TYPE_12, OPERATOR_NAME_LABEL_12);
    public static final OperatorTypeDto TEST_OPERATOR_TYPE_DTO_13 = operatorTypeDto(OPERATOR_TYPE_ID_13, OPERATOR_NAME_TYPE_13, OPERATOR_NAME_LABEL_13);

    public static List<OperatorType> allOperatorTypes() {
        return asList(operatorTypeAdmin(), operatorTypePhoneDebtCollector(), operatorTypePhoneDebtCollectorManager(), operatorTypeDirectDebtCollectionManager(),
                operatorTypeFieldDebtCollector(), operatorTypeRestructuringSpecialist(), operatorTypeRenunciationCoordinator(),
                operatorTypeSecuritySpecialist(), operatorAnalyst(), operatorDepartmentManager(), operatorAccountManager(), operatorTypePhoneDebtCollector1(),
                operatorTypePhoneDebtCollector2());
    }

    public static OperatorType operatorTypeAdmin() {
        return operatorType(OPERATOR_TYPE_ID_1, OPERATOR_NAME_TYPE_1, OPERATOR_NAME_LABEL_1);
    }

    public static OperatorType operatorTypePhoneDebtCollector() {
        return operatorType(OPERATOR_TYPE_ID_2, OPERATOR_NAME_TYPE_2, operatorTypePhoneDebtCollectorManager(), OPERATOR_NAME_LABEL_2);
    }

    public static OperatorType operatorTypePhoneDebtCollector1() {
        return operatorType(OPERATOR_TYPE_ID_12, OPERATOR_NAME_TYPE_12, operatorTypePhoneDebtCollectorManager(), OPERATOR_NAME_LABEL_12);
    }

    public static OperatorType operatorTypePhoneDebtCollector2() {
        return operatorType(OPERATOR_TYPE_ID_13, OPERATOR_NAME_TYPE_13, operatorTypePhoneDebtCollectorManager(), OPERATOR_NAME_LABEL_13);
    }

    public static OperatorType operatorTypePhoneDebtCollectorManager() {
        return operatorType(OPERATOR_TYPE_ID_3, OPERATOR_NAME_TYPE_3, OPERATOR_NAME_LABEL_3);
    }

    private static OperatorType operatorTypeDirectDebtCollectionManager() {
        return operatorType(OPERATOR_TYPE_ID_4, OPERATOR_NAME_TYPE_4, OPERATOR_NAME_LABEL_4);
    }

    public static OperatorType operatorTypeFieldDebtCollector() {
        return operatorType(OPERATOR_TYPE_ID_5, OPERATOR_NAME_TYPE_5, operatorTypeDirectDebtCollectionManager(), OPERATOR_NAME_LABEL_5);
    }

    private static OperatorType operatorTypeRestructuringSpecialist() {
        return operatorType(OPERATOR_TYPE_ID_6, OPERATOR_NAME_TYPE_6, OPERATOR_NAME_LABEL_6);
    }

    private static OperatorType operatorTypeRenunciationCoordinator() {
        return operatorType(OPERATOR_TYPE_ID_7, OPERATOR_NAME_TYPE_7, OPERATOR_NAME_LABEL_7);
    }

    private static OperatorType operatorTypeSecuritySpecialist() {
        return operatorType(OPERATOR_TYPE_ID_8, OPERATOR_NAME_TYPE_8, OPERATOR_NAME_LABEL_8);
    }

    private static OperatorType operatorAnalyst() {
        return operatorType(OPERATOR_TYPE_ID_9, OPERATOR_NAME_TYPE_9, OPERATOR_NAME_LABEL_9);
    }

    private static OperatorType operatorDepartmentManager() {
        return operatorType(OPERATOR_TYPE_ID_10, OPERATOR_NAME_TYPE_10, OPERATOR_NAME_LABEL_10);
    }

    public static OperatorType operatorAccountManager() {
        return operatorType(OPERATOR_TYPE_ID_11, OPERATOR_NAME_TYPE_11, OPERATOR_NAME_LABEL_11);
    }

    private static OperatorType operatorType(final Long id, final OperatorNameType typeName, final String label) {
        return operatorType(id, typeName, null, label);
    }

    private static OperatorType operatorType(final Long id, final OperatorNameType typeName, final OperatorType parentOperatorType, final String label) {
        final OperatorType operatorType = new OperatorType();
        operatorType.setId(id);
        operatorType.setTypeName(typeName);
        operatorType.setTypeLabel(label);
        operatorType.setParentOperatorType(parentOperatorType);
        return operatorType;
    }

    private static OperatorTypeDto operatorTypeDto(final Long operatorTypeId, final OperatorNameType operatorNameType, final String label) {
        return new OperatorTypeDto(operatorTypeId, operatorNameType.name(), label);
    }
}


