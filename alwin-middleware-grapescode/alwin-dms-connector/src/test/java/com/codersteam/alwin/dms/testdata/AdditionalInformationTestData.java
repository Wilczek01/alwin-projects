package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentAdditionalInformation;

public class AdditionalInformationTestData extends AdditionalInformationDtoTestData {

    public static DocumentAdditionalInformation additionalInformation1() {
        return additionalInformation(KEY_1, VALUE_1);
    }

    public static DocumentAdditionalInformation additionalInformation2() {
        return additionalInformation(KEY_2, VALUE_2);
    }

    public static DocumentAdditionalInformation additionalInformation3() {
        return additionalInformation(KEY_3, VALUE_3);
    }

    public static DocumentAdditionalInformation additionalInformation4() {
        return additionalInformation(KEY_4, VALUE_4);
    }

    public static DocumentAdditionalInformation additionalInformation(final String key, final String value) {
        final DocumentAdditionalInformation documentAdditionalInformation = new DocumentAdditionalInformation();
        documentAdditionalInformation.setKey(key);
        documentAdditionalInformation.setValue(value);
        return documentAdditionalInformation;
    }
}