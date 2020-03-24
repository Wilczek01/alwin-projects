package com.codersteam.alwin.dms.testdata;


import com.codersteam.alwin.dms.model.MetadataDto;

/**
 * @author Piotr Naroznik
 */
public class MetadataDtoTestData {
    public static final String KEY_1 = "KEY_1";
    public static final String LABEL_1 = "LABEL_1";
    public static final String VALUE_1 = "VALUE_1";

    public static MetadataDto metadataDto1() {
        return metadataDto(KEY_1, LABEL_1, VALUE_1);
    }

    public static final String KEY_2 = "KEY_2";
    public static final String LABEL_2 = "LABEL_2";
    public static final String VALUE_2 = "VALUE_2";

    public static MetadataDto metadataDto2() {
        return metadataDto(KEY_2, LABEL_2, VALUE_2);
    }

    public static final String KEY_3 = "KEY_3";
    public static final String LABEL_3 = "LABEL_3";
    public static final String VALUE_3 = "VALUE_3";

    public static MetadataDto metadataDto3() {
        return metadataDto(KEY_3, LABEL_3, VALUE_3);
    }

    public static final String KEY_4 = "KEY_4";
    public static final String LABEL_4 = "LABEL_4";
    public static final String VALUE_4 = "VALUE_4";

    public static MetadataDto metadataDto4() {
        return metadataDto(KEY_4, LABEL_4, VALUE_4);
    }

    public static MetadataDto metadataDto(String key, String label, String value) {
        final MetadataDto metadataDto = new MetadataDto();
        metadataDto.setKey(key);
        metadataDto.setLabel(label);
        metadataDto.setValue(value);
        return metadataDto;
    }
}