package com.guncat.ecommerce.common.enums;

import lombok.Getter;

@Getter
public enum IsLocked {

    LOCKED, UNLOCKED;

    public boolean toBoolean() {
        return this == LOCKED;
    }

    public static IsLocked fromBoolean(Boolean value) {
        return value != null && value ? LOCKED : UNLOCKED;
    }

}
