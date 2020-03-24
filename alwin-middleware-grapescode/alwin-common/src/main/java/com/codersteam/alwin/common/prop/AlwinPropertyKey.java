package com.codersteam.alwin.common.prop;

/**
 * Klucze dla wartości wyniesionych do pliku alwin.properties
 *
 * @author Michal Horowic
 */
public enum AlwinPropertyKey {
    DMS_ENDPOINT("dms.endpoint"),
    DMS_LOGIN("dms.login"),
    DMS_PASSWORD("dms.password"),
    DMS_DOCUMENT_URL("dms.document.url"),
    EFAKTURA_ENDPOINT("efaktura.endpoint"),
    EFAKTURA_TERMINATION_ENDPOINT("efaktura.termination.endpoint"),
    ATTACHMENTS_DIR("attachments.dir"),
    ATTACHMENTS_URL("attachments.url");

    private final String key;

    AlwinPropertyKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
