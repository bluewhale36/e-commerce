package com.guncat.ecommerce.common.enums;

import lombok.Getter;

/**
 * 대상 활성화 여부의 Enum Class.
 *
 * @see com.guncat.ecommerce.common.converter.IsEnabledConverter
 */
@Getter
public enum IsEnabled {

    ENABLED, DISABLED;

    /**
     * {@link IsEnabled} 객체를 {@code boolean} 으로 변환.
     *
     * @return {@link IsEnabled#ENABLED} 일 경우 {@code true} 반환.<br/>
     * {@link IsEnabled#DISABLED} 일 경우 {@code false} 반환.
     */
    public boolean toBoolean() {
        return this == ENABLED;
    }

    /**
     * {@link Boolean} 객체를 {@link IsEnabled} 객체로 반환.
     * @param value {@link Boolean}
     * @return 매개변수가 {@link Boolean#TRUE} 일 경우 {@link IsEnabled#ENABLED} 반환.<br/>
     * 매개변수가 {@code null} 이거나, {@link Boolean#FALSE} 일 경우 {@link IsEnabled#DISABLED} 반환.
     */
    public static IsEnabled fromBoolean(Boolean value) {
        return value != null && value ? ENABLED : DISABLED;
    }

}
