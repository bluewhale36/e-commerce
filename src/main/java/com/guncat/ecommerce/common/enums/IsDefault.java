package com.guncat.ecommerce.common.enums;

import lombok.Getter;

/**
 * 기본 설정 여부의 Enum Class.
 *
 * @see com.guncat.ecommerce.common.converter.IsDefaultConverter
 */
@Getter
public enum IsDefault {

    DEFAULT, NOT_DEFAULT;

    /**
     * {@link IsDefault} 객체를 {@code boolean} 으로 변환.
     *
     * @return {@link IsDefault#DEFAULT} 일 경우 {@code true} 반환.<br/>
     * {@link IsDefault#NOT_DEFAULT} 일 경우 {@code false} 반환.
     */
    public boolean toBoolean() {
        return this == DEFAULT;
    }

    /**
     * {@link Boolean} 객체를 {@link IsDefault} 객체로 반환.
     * @param value {@link Boolean}
     * @return 매개변수가 {@link Boolean#TRUE} 일 경우 {@link IsDefault#DEFAULT} 반환.<br/>
     * 매개변수가 {@code null} 이거나, {@link Boolean#FALSE} 일 경우 {@link IsDefault#NOT_DEFAULT} 반환.
     */
    public static IsDefault fromBoolean(Boolean value) {
        return value != null && value ? DEFAULT : NOT_DEFAULT;
    }
}
