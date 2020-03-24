package com.codersteam.alwin.testdata;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeDto;
import com.codersteam.alwin.jpa.PostalCode;
import com.codersteam.alwin.jpa.operator.Operator;

import java.util.List;

import static com.codersteam.alwin.testdata.OperatorTestData.testOperator1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator3;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperator4;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto1;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto2;
import static com.codersteam.alwin.testdata.OperatorTestData.testOperatorDto3;
import static java.util.Arrays.asList;

/**
 * @author Michal Horowic
 */
public class PostalCodeTestData {

    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final Long ID_3 = 3L;
    public static final Long ID_4 = 4L;
    public static final Long ID_5 = 5L;
    public static final Long ID_100 = 100L;

    public static final String MASK_1 = "12-345";
    public static final String MASK_2 = "12-34x";
    public static final String MASK_3 = "12-3xx";
    public static final String MASK_4 = "23-xxx";
    public static final String MASK_5 = "2x-xxx";
    public static final String NOT_EXISTING_MASK = "01-001";

    public static final Operator OPERATOR_1 = testOperator1();
    public static final Operator OPERATOR_2 = testOperator2();
    public static final Operator OPERATOR_3 = testOperator3();
    public static final Operator OPERATOR_4 = testOperator4();
    public static final Operator OPERATOR_5 = testOperator1();

    public static final OperatorDto OPERATOR_1_DTO = testOperatorDto1();
    public static final OperatorDto OPERATOR_2_DTO = testOperatorDto2();
    public static final OperatorDto OPERATOR_3_DTO = testOperatorDto3();

    public static PostalCode testPostalCode1() {
        return postalCode(ID_1, MASK_1, OPERATOR_1);
    }

    public static PostalCode testPostalCode2() {
        return postalCode(ID_2, MASK_2, OPERATOR_2);
    }

    public static PostalCode testPostalCode3() {
        return postalCode(ID_3, MASK_3, OPERATOR_3);
    }

    public static PostalCode testPostalCode4() {
        return postalCode(ID_4, MASK_4, OPERATOR_4);
    }

    public static PostalCode testPostalCode5() {
        return postalCode(ID_5, MASK_5, OPERATOR_5);
    }

    public static PostalCodeDto testPostalCode1Dto() {
        return postalCodeDto(ID_1, MASK_1, OPERATOR_1_DTO);
    }

    public static PostalCodeDto testPostalCode2Dto() {
        return postalCodeDto(ID_2, MASK_2, OPERATOR_2_DTO);
    }

    public static PostalCodeDto testPostalCode3Dto() {
        return postalCodeDto(ID_3, MASK_3, OPERATOR_3_DTO);
    }

    public static List<PostalCode> testPostalCodesList() {
        return asList(testPostalCode1(), testPostalCode2(), testPostalCode3());
    }

    public static List<PostalCodeDto> testPostalCodesListDto() {
        return asList(testPostalCode1Dto(), testPostalCode2Dto(), testPostalCode3Dto());
    }

    public static Page<PostalCodeDto> testPostalCodesPage() {
        return new Page<>(testPostalCodesListDto(), 3);
    }


    private static PostalCodeDto postalCodeDto(final Long id, final String mask, final OperatorDto operator) {
        final PostalCodeDto postalCode = new PostalCodeDto();
        postalCode.setId(id);
        postalCode.setMask(mask);
        postalCode.setOperator(operator);
        return postalCode;
    }

    private static PostalCode postalCode(final Long id, final String mask, final Operator operator) {
        final PostalCode postalCode = new PostalCode();
        postalCode.setId(id);
        postalCode.setMask(mask);
        postalCode.setOperator(operator);
        return postalCode;
    }
}
