package com.guncat.ecommerce.common.converter;

import com.guncat.ecommerce.common.enums.IsEnabled;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link com.guncat.ecommerce.common.enums.IsEnabled} Enum 객체와 JPA Record 를 변환하는 Converter.
 *
 * @see IsEnabled
 */
@Converter(autoApply = true)
public class IsEnabledConverter implements AttributeConverter<IsEnabled, Boolean> {

    /**
     * 매개변수의 객체를 DB Column 으로 Convert.
     *
     * @param isEnabled {@link IsEnabled} 객체.
     * @return 매개변수가 {@link IsEnabled#ENABLED} 일 경우 {@link Boolean#TRUE} 반환.<br/>
     * 매개변수가 {@code null} 이거나, {@link IsEnabled#DISABLED} 일 경우 {@link Boolean#FALSE} 반환.
     */
    @Override
    public Boolean convertToDatabaseColumn(IsEnabled isEnabled) {
        return isEnabled != null && isEnabled.toBoolean();
    }

    /**
     * 매개변수의 DB Column 값을 {@link IsEnabled} 객체로 Convert.
     *
     * @param aBoolean {@link Boolean} 객체.
     * @return {@link IsEnabled#fromBoolean(Boolean)}
     */
    @Override
    public IsEnabled convertToEntityAttribute(Boolean aBoolean) {
        return IsEnabled.fromBoolean(aBoolean);
    }
}
