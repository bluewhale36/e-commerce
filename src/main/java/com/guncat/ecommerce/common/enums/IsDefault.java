package com.guncat.ecommerce.common.enums;

import lombok.Getter;

@Getter
public enum IsDefault {

    DEFAULT, NOT_DEFAULT;

    public boolean toBoolean() {
        return this == DEFAULT;
    }

    public static IsDefault fromBoolean(Boolean value) {
        return value != null && value ? DEFAULT : NOT_DEFAULT;
    }
}
