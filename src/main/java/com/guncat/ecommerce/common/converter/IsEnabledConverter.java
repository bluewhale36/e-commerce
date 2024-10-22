package com.guncat.ecommerce.common.converter;

import com.guncat.ecommerce.common.enums.IsEnabled;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@link com.guncat.ecommerce.common.enums.IsEnabled} Enum 객체와 JPA Record 를 변환하는 Converter.
 */
@Converter(autoApply = true)
public class IsEnabledConverter implements AttributeConverter<IsEnabled, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(IsEnabled isEnabled) {
        return isEnabled != null && isEnabled.toBoolean();
    }

    @Override
    public IsEnabled convertToEntityAttribute(Boolean aBoolean) {
        return IsEnabled.fromBoolean(aBoolean);
    }
}
