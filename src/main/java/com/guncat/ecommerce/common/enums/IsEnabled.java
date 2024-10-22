package com.guncat.ecommerce.common.enums;

import lombok.Getter;

@Getter
public enum IsEnabled {

    ENABLED, DISABLED;

    public boolean toBoolean() {
        return this == ENABLED;
    }

    public static IsEnabled fromBoolean(Boolean value) {
        return value != null && value ? ENABLED : DISABLED;
    }

}
