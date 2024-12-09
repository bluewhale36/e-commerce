package com.guncat.ecommerce.common.enums;

import lombok.Getter;

/**
 * 사용자 계정 잠김 여부의 Enum Class.
 *
 * @see com.guncat.ecommerce.common.converter.IsLockedConverter
 */
@Getter
public enum IsLocked {

    LOCKED("잠김"),
    UNLOCKED("잠기지 않음");

    private final String description;

    IsLocked(String description) {
        this.description = description;
    }

    /**
     * {@link IsLocked} 객체를 {@code boolean} 으로 변환.
     *
     * @return {@link IsLocked#LOCKED} 일 경우 {@code true} 반환.<br/>
     * {@link IsLocked#UNLOCKED} 일 경우 {@code false} 반환.
     */
    public boolean toBoolean() {
        return this == LOCKED;
    }

    /**
     * {@link Boolean} 객체를 {@link IsDefault} 객체로 반환.
     * @param value {@link Boolean}
     * @return 매개변수가 {@link Boolean#TRUE} 일 경우 {@link IsLocked#LOCKED} 반환.<br/>
     * 매개변수가 {@code null} 이거나, {@link Boolean#FALSE} 일 경우 {@link IsLocked#UNLOCKED} 반환.
     */
    public static IsLocked fromBoolean(Boolean value) {
        return value != null && value ? LOCKED : UNLOCKED;
    }

}
