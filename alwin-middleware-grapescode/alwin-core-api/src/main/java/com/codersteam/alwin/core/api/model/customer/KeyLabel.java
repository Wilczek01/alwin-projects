package com.codersteam.alwin.core.api.model.customer;

import com.codersteam.alwin.common.ReasonType;

/**
 * @author Piotr Naroznik
 */
public class KeyLabel {
    private final String key;
    private final String label;

    public KeyLabel(ReasonType type) {
        this.key = type.name();
        this.label = type.getLabel();
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }
}
