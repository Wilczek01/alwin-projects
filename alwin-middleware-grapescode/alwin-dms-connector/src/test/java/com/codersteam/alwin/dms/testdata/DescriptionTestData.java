package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentDescription;

class DescriptionTestData extends DescriptionsDtoTestData {

    static DocumentDescription description1() {
        return description(KEY_1, VALUE_1);
    }

    static DocumentDescription description2() {
        return description(KEY_2, VALUE_2);
    }

    static DocumentDescription description3() {
        return description(KEY_3, VALUE_3);
    }

    static DocumentDescription description4() {
        return description(KEY_4, VALUE_4);
    }

    private static DocumentDescription description(final String key, final String value) {
        final DocumentDescription description = new DocumentDescription();
        description.setKey(key);
        description.setValue(value);
        return description;
    }
}