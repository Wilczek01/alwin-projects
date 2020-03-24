package com.codersteam.alwin.dms.testdata;

import java.util.LinkedHashMap;
import java.util.Map;

public class DescriptionsDtoTestData {

    protected static final String KEY_1 = "KEY_11";
    protected static final String VALUE_1 = "VALUE_11";

    protected static final String KEY_2 = "KEY_21";
    protected static final String VALUE_2 = "VALUE_21";

    protected static final String KEY_3 = "KEY_31";
    protected static final String VALUE_3 = "VALUE_31";

    protected static final String KEY_4 = "KEY_41";
    protected static final String VALUE_4 = "VALUE_41";

    public static Map<String, String> descriptions1() {
        final Map<String, String> descriptions = new LinkedHashMap<>();
        descriptions.put(KEY_1, VALUE_1);
        descriptions.put(KEY_2, VALUE_2);
        descriptions.put(KEY_3, VALUE_3);
        descriptions.put(KEY_4, VALUE_4);
        return descriptions;
    }
}