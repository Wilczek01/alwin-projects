package com.codersteam.alwin.testdata.aida;


import com.codersteam.aida.core.api.model.CalculatorDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@SuppressWarnings("WeakerAccess")
public final class AidaCalculatorTestData {

    private static final long ID = 123456789L;
    private static final long CALCULATION_TYPE = 1L;
    private static final long FINANCIAL_MODEL = 8888L;

    public static final Long CALCULATOR_ID_1 = 1L;
    public static final Long CALCULATOR_CALC_TYPE_1 = 1L;
    public static final String CALCULATOR_NAME_1 = "Bazowy";

    public static final Long CALCULATOR_ID_2 = 2L;
    public static final Long CALCULATOR_CALC_TYPE_2 = 1L;
    public static final String CALCULATOR_NAME_2 = "Ubezpieczenie dodatkowe";

    public static final Long CALCULATOR_ID_3 = 3L;
    public static final Long CALCULATOR_CALC_TYPE_3 = 1L;
    public static final String CALCULATOR_NAME_3 = "Ubezpieczenie Komunikacyjne";

    public static final Long CALCULATOR_ID_4 = 4L;
    public static final Long CALCULATOR_CALC_TYPE_4 = 3L;
    public static final String CALCULATOR_NAME_4 = "GAP";

    public static final Long CALCULATOR_ID_5 = 5L;
    public static final Long CALCULATOR_CALC_TYPE_5 = 1L;
    public static final String CALCULATOR_NAME_5 = "Dodatek";

    public static final List<CalculatorDto> TEST_EXT_CALCULATORS = aidaCalculator();

    private AidaCalculatorTestData() {
    }

    private static List<CalculatorDto> aidaCalculator() {
        final List<CalculatorDto> aidaCalculators = new ArrayList<>();

        final CalculatorDto calculatorDto = new CalculatorDto();
        calculatorDto.setId(ID);
        calculatorDto.setCalculationType(CALCULATION_TYPE);
        calculatorDto.setFinanceModel(FINANCIAL_MODEL);

        aidaCalculators.add(calculatorDto);
        return aidaCalculators;
    }
}
