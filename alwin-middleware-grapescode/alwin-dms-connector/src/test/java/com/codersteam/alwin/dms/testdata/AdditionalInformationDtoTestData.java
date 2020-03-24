package com.codersteam.alwin.dms.testdata;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdditionalInformationDtoTestData {

    public static final String KEY_1 = "KEY_1";
    public static final String VALUE_1 = "VALUE_1";

    public static final String KEY_2 = "KEY_2";
    public static final String VALUE_2 = "VALUE_2";

    public static final String KEY_3 = "KEY_3";
    public static final String VALUE_3 = "VALUE_3";

    public static final String KEY_4 = "KEY_4";
    public static final String VALUE_4 = "VALUE_4";

    public static Map<String, String> additionalInformations1() {
        final Map<String, String> additionalInformations = new LinkedHashMap<>();
        additionalInformations.put(KEY_1, VALUE_1);
        additionalInformations.put(KEY_2, VALUE_2);
        additionalInformations.put(KEY_3, VALUE_3);
        additionalInformations.put(KEY_4, VALUE_4);
        return additionalInformations;
    }
}