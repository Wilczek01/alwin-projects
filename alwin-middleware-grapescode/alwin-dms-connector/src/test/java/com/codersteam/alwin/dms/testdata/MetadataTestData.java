package com.codersteam.alwin.dms.testdata;

import pl.aliorleasing.dms.DocumentMetadataDTO;

/**
 * @author Piotr Naroznik
 */
class MetadataTestData extends MetadataDtoTestData {

    static DocumentMetadataDTO metadata1() {
        return metadata(KEY_1, LABEL_1, VALUE_1);
    }

    static DocumentMetadataDTO metadata2() {
        return metadata(KEY_2, LABEL_2, VALUE_2);
    }

    static DocumentMetadataDTO metadata3() {
        return metadata(KEY_3, LABEL_3, VALUE_3);
    }

    static DocumentMetadataDTO metadata4() {
        return metadata(KEY_4, LABEL_4, VALUE_4);
    }

    private static DocumentMetadataDTO metadata(final String key, final String label, final String value) {
        final DocumentMetadataDTO metadata = new DocumentMetadataDTO();
        metadata.setKey(key);
        metadata.setLabel(label);
        metadata.setValue(value);
        return metadata;
    }
}