package com.codersteam.alwin.testdata;

import com.codersteam.alwin.common.issue.IssueTypeName;
import com.codersteam.alwin.core.api.model.issue.IssueTypeDto;
import com.codersteam.alwin.core.api.model.issue.IssueTypeWithOperatorTypesDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.jpa.issue.IssueType;
import com.codersteam.alwin.jpa.operator.OperatorType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_12;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_2;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.TEST_OPERATOR_TYPE_DTO_3;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypeAdmin;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollector;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollector1;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollector2;
import static com.codersteam.alwin.testdata.OperatorTypeTestData.operatorTypePhoneDebtCollectorManager;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@SuppressWarnings("WeakerAccess")
public class IssueTypeTestData {

    public static final Long NOT_EXISTING_ISSUE_TYPE_ID = -1L;

    public static final Integer DPD_START_1A = 1;
    public static final Integer DPD_START_1B = 1;
    public static final Integer DPD_START_2A = 1;
    public static final Integer DPD_START_2B = 1;

    public static final Integer DURATION_1A = 10;
    public static final Integer DURATION_1B = 14;
    public static final Integer DURATION_2A = 10;
    public static final Integer DURATION_2B = 14;
    public static final Integer DURATION_A = 10;
    public static final Integer DURATION_B = 14;

    public static final Long ISSUE_TYPE_ID_1 = 1L;
    public static final IssueTypeName NAME_1 = IssueTypeName.PHONE_DEBT_COLLECTION_1;
    private static final String LABEL_1 = "Windykacja telefoniczna sekcja 1";

    public static final Long ID_2 = 2L;
    private static final IssueTypeName NAME_2 = IssueTypeName.PHONE_DEBT_COLLECTION_2;
    private static final String LABEL_2 = "Windykacja telefoniczna sekcja 2";

    private static final Long ID_3 = 3L;
    private static final IssueTypeName NAME_3 = IssueTypeName.DIRECT_DEBT_COLLECTION;
    private static final String LABEL_3 = "Windykacja bezpośrednia";

    private static final Long ID_4 = 4L;
    private static final IssueTypeName NAME_4 = IssueTypeName.LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM;
    private static final String LABEL_4 = "Windykacja prawna - pozew o wydanie przedmiotu";

    private static final Long ID_5 = 5L;
    private static final IssueTypeName NAME_5 = IssueTypeName.SUBJECT_TRANSPORT;
    private static final String LABEL_5 = "Transport przedmiotu";

    private static final Long ID_6 = 6L;
    private static final IssueTypeName NAME_6 = IssueTypeName.REALIZATION_OF_COLLATERAL;
    private static final String LABEL_6 = "Realizacja zabezpieczenia";

    private static final Long ID_7 = 7L;
    private static final IssueTypeName NAME_7 = IssueTypeName.LAW_DEBT_COLLECTION_MOTION_TO_PAY;
    private static final String LABEL_7 = "Windykacja prawna - pozew o zapłatę";

    private static final Long ID_8 = 8L;
    private static final IssueTypeName NAME_8 = IssueTypeName.RESTRUCTURING;
    private static final String LABEL_8 = "Restrukturyzacja";

    public static final IssueType ISSUE_TYPE_1 = issueType1();
    public static final IssueType ISSUE_TYPE_2 = issueType2();
    public static final IssueType ISSUE_TYPE_3 = issueType3();

    public List<IssueType> all() {
        return asList(ISSUE_TYPE_1, ISSUE_TYPE_2, issueType3(), issueType4(), issueType5(), issueType6(), issueType7(), issueType8());
    }

    public static final List<IssueType> ALL_ISSUE_TYPES = asList(issueType1(), issueType2(), issueType3(), issueType4(), issueType5(), issueType6(),
            issueType7(), issueType8());

    public static final List<IssueType> PHONE_DEBT_MANAGER_ISSUE_TYPES = singletonList(issueType1());

    public static IssueType issueType1() {
        return issueType(ISSUE_TYPE_ID_1, NAME_1, LABEL_1, new HashSet<>(asList(operatorTypeAdmin(), operatorTypePhoneDebtCollectorManager(),
                operatorTypePhoneDebtCollector(), operatorTypePhoneDebtCollector1())));
    }

    public static IssueType issueTypeWithEmptyOperatorTypes() {
        return issueType(ISSUE_TYPE_ID_1, NAME_1, LABEL_1, new HashSet<>());
    }

    public static IssueType issueType2() {
        return issueType(ID_2, NAME_2, LABEL_2, new HashSet<>(asList(operatorTypeAdmin(), operatorTypePhoneDebtCollector2())));
    }

    public static IssueType issueType3() {
        return issueType(ID_3, NAME_3, LABEL_3, new HashSet<>(singletonList(operatorTypeAdmin())));
    }

    public static IssueType issueType4() {
        return issueType(ID_4, NAME_4, LABEL_4, new HashSet<>(singletonList(operatorTypeAdmin())));
    }

    public static IssueType issueType5() {
        return issueType(ID_5, NAME_5, LABEL_5, new HashSet<>(singletonList(operatorTypeAdmin())));
    }

    public static IssueType issueType6() {
        return issueType(ID_6, NAME_6, LABEL_6, new HashSet<>(singletonList(operatorTypeAdmin())));
    }

    public static IssueType issueType7() {
        return issueType(ID_7, NAME_7, LABEL_7, new HashSet<>(singletonList(operatorTypeAdmin())));
    }

    public static IssueType issueType8() {
        return issueType(ID_8, NAME_8, LABEL_8, new HashSet<>(singletonList(operatorTypeAdmin())));
    }

    public static IssueTypeWithOperatorTypesDto issueTypeWithOperatorTypesDto1() {
        return issueTypeWithOperatorTypesDto(ISSUE_TYPE_ID_1, NAME_1.name(), LABEL_1, new HashSet<>(asList(TEST_OPERATOR_TYPE_DTO, TEST_OPERATOR_TYPE_DTO_3,
                TEST_OPERATOR_TYPE_DTO_2, TEST_OPERATOR_TYPE_DTO_12)));
    }

    public static IssueTypeDto issueTypeDto1() {
        return issueTypeDto(ISSUE_TYPE_ID_1, NAME_1.name(), LABEL_1);
    }

    public static IssueTypeDto issueTypeDtoPhoneDebtCollection1() {
        return issueTypeDto(ISSUE_TYPE_ID_1, NAME_1.name(), LABEL_1);
    }

    public static IssueTypeDto issueTypeDto2() {
        return issueTypeDto(ID_2, NAME_2.name(), LABEL_2);
    }

    public static IssueTypeDto issueTypeDto3() {
        return issueTypeDto(ID_3, NAME_3.name(), LABEL_3);
    }

    public static IssueTypeWithOperatorTypesDto issueTypeWithOperatorTypesDto3() {
        return issueTypeWithOperatorTypesDto(ID_3, NAME_3.name(), LABEL_3, new HashSet<>(singletonList(TEST_OPERATOR_TYPE_DTO)));
    }

    private static IssueType issueType(final Long id, final IssueTypeName name, final String label, final Set<OperatorType> operatorTypes) {
        final IssueType issueType = new IssueType();
        issueType.setLabel(label);
        issueType.setName(name);
        issueType.setId(id);
        issueType.setOperatorTypes(operatorTypes);
        return issueType;
    }

    private static IssueTypeDto issueTypeDto(final Long id, final String name, final String label) {
        final IssueTypeDto issueTypeDto = new IssueTypeDto();
        issueTypeDto.setLabel(label);
        issueTypeDto.setName(name);
        issueTypeDto.setId(id);
        return issueTypeDto;
    }

    private static IssueTypeWithOperatorTypesDto issueTypeWithOperatorTypesDto(final Long id, final String name, final String label,
                                                                               final Set<OperatorTypeDto> operatorTypes) {
        final IssueTypeWithOperatorTypesDto issueTypeDto = new IssueTypeWithOperatorTypesDto();
        issueTypeDto.setLabel(label);
        issueTypeDto.setName(name);
        issueTypeDto.setId(id);
        issueTypeDto.setOperatorTypes(operatorTypes);
        return issueTypeDto;
    }
}
